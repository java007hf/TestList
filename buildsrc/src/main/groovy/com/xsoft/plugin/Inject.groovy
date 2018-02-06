package com.xsoft.plugin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import org.apache.commons.io.FileUtils

/**
 * Created by AItsuki on 2016/4/7.
 * 注入代码分为两种情况，一种是目录，需要遍历里面的class进行注入
 * 另外一种是jar包，需要先解压jar包，注入代码之后重新打包成jar
 */
public class Inject {

    private static ClassPool pool = ClassPool.getDefault()
    private static String classesDir

    /**
     * 添加classPath到ClassPool
     * @param libPath
     */
    public static void appendClassPath(String libPath) {
        pool.appendClassPath(libPath)
    }

    private static boolean isRFile(String filePath) {
        boolean isRFile = false;
        if (filePath.endsWith(".class")
                && (filePath.contains('R$') || filePath.contains('R.class'))) {
            isRFile = true
        }

        return isRFile
    }

    /**
     * 遍历该目录下的所有class，对所有class进行代码注入。
     * 其中以下class是不需要注入代码的：
     * --- 1. R文件相关
     * --- 2. 配置文件相关（BuildConfig）
     * --- 3. Application
     * @param path 目录的路径
     */
    public static void injectDir(String path) {
        pool.appendClassPath(path)
        File dir = new File(path)
        if(dir.isDirectory()) {
            dir.eachFileRecurse { File file ->

                String filePath = file.absolutePath

                PreDexTransform.project.logger.error "injectDir:=====$filePath"

                if (isRFile(filePath)) {
                    PreDexTransform.project.logger.error "isRFile true:=====$filePath"
                    String className = filePathToClassName(filePath)
                    injectClass(className, path)
                }
            }
        }
    }

    private static void getClassesDir(String filePath) {
        if (classesDir==null || classesDir.isEmpty()) {
            String buildDir = PreDexTransform.project.getBuildDir()
            String classesDirTmp = buildDir + java.io.File.separator + "intermediates" + java.io.File.separator+ "classes"+ java.io.File.separator
            int start = classesDirTmp.length()

            String str = filePath.substring(start)
            String buildType
            if (str.startsWith("debug")) {
                buildType = "debug" + java.io.File.separator
            } else {
                buildType = "release" + java.io.File.separator
            }

            classesDir = classesDirTmp + buildType
            PreDexTransform.project.logger.error "getClassesDir classesDir:=====$classesDir"
        }
    }

    private static String filePathToClassName(String filePath) {
        getClassesDir(filePath)
        String packageAndClass = filePath.substring(classesDir.length())

        PreDexTransform.project.logger.error "filePathToClassName packageAndClass:=====$packageAndClass"
        String className = packageAndClass.replace('\\', '.').replace('/','.')
        className = moveClass(className)
        PreDexTransform.project.logger.error "filePathToClassName className:=====$className"

        return className
    }

    private static String moveClass(String file) {
        int end = file.length() - 6 // .class = 6
        return file.substring(0, end)
    }

    /**
     * 这里需要将jar包先解压，注入代码后再重新生成jar包
     * @path jar包的绝对路径
     */
    public static void injectJar(String path) {
        if (path.endsWith(".jar")) {
            File jarFile = new File(path)


            // jar包解压后的保存路径
            String jarZipDir = jarFile.getParent() +"/"+jarFile.getName().replace('.jar','')

            // 解压jar包, 返回jar包中所有class的完整类名的集合（带.class后缀）
            List classNameList = JarZipUtil.unzipJar(path, jarZipDir)

            // 删除原来的jar包
            jarFile.delete()

            // 注入代码
            pool.appendClassPath(jarZipDir)
            for(String className : classNameList) {

                PreDexTransform.project.logger.error "injectJar:=====$className"

                if (isRFile(className)) {
                    className = moveClass(className)
                    injectClass(className, jarZipDir)
                }
            }

            // 从新打包jar
            JarZipUtil.zipJar(jarZipDir, path)

            // 删除目录
            FileUtils.deleteDirectory(new File(jarZipDir))
        }
    }

    private static void injectClass(String className, String path) {
        PreDexTransform.project.logger.error "injectClass:=====$className"
        CtClass c = pool.getCtClass(className)
        boolean isFrozen = c.isFrozen()
        if (isFrozen) {
            c.defrost()
        }

        CtField[] fields = c.getFields();
        for(CtField field : fields) {
            String name = field.getFieldInfo().getName();
            Object obj = field.getConstantValue();

            if (obj == null) continue
            PreDexTransform.project.logger.error "injectClass:==obj===$obj"
            int value = Integer.parseInt(obj+"")
            String hexStr = Integer.toHexString(value)

            if (!hexStr.startsWith("7f") || hexStr.length()!=8) continue
            String subStr = "0x6f" + hexStr.substring(2)
            String addStr = "public static final int " + name + " = " + subStr + ";"

            PreDexTransform.project.logger.error "injectClass:==addStr===$addStr"

            CtField copyField = CtField.make(addStr, c)
            c.removeField(field);
            c.addField(copyField);
        }
        c.writeFile(path)
    }

}
