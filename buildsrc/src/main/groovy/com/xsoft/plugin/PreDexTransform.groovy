package com.xsoft.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class PreDexTransform extends Transform {

    public static Project project

    public PreDexTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "preDex"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    private void printInfo() {
        String getRootProject = project.getRootProject()
        String getRootDir = project.getRootDir()
        String getBuildDir = project.getBuildDir()
        String getBuildFile = project.getBuildFile()
        String getName = project.getName()
        String getDisplayName = project.getDisplayName()
        String getDescription = project.getDescription()
        String getVersion = project.getVersion()
        String getStatus = project.getStatus()
        String getPath = project.getPath()
        String getProjectDir = project.getProjectDir()

        project.logger.error "=====getRootProject =$getRootProject"
        project.logger.error "=====getRootDir =$getRootDir"
        project.logger.error "=====getBuildDir =$getBuildDir"
        project.logger.error "=====getBuildFile =$getBuildFile"
        project.logger.error "=====getName =$getName"
        project.logger.error "=====getDisplayName =$getDisplayName"
        project.logger.error "=====getDescription =$getDescription"
        project.logger.error "=====getVersion =$getVersion"
        project.logger.error "=====getStatus =$getStatus"
        project.logger.error "=====getPath =$getPath"
        project.logger.error "=====getProjectDir =$getProjectDir"
        project.logger.error "=====rootProject =$project.rootProject.name"
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        printInfo();

        // 遍历transfrom的inputs
        // inputs有两种类型，一种是目录，一种是jar，需要分别遍历。
        inputs.each {TransformInput input ->
            input.directoryInputs.each {DirectoryInput directoryInput->

                // 获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                project.logger.error "=====inputs =$directoryInput"
                project.logger.error "=====outputs =$dest"

                //TODO 这里可以对input的文件做处理，比如代码注入！
                Inject.injectDir(directoryInput.file.absolutePath)

                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each {JarInput jarInput->


                //TODO 这里可以对input的文件做处理，比如代码注入！
                String jarPath = jarInput.file.absolutePath;
                String projectName = project.rootProject.name;
                if(jarPath.endsWith("classes.jar") && jarPath.contains("exploded-aar"+"\\"+projectName)) {
//                    Inject.injectJar(jarPath)
                }

                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if(jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0,jarName.length()-4)
                }
                def dest = outputProvider.getContentLocation(jarName+md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                project.logger.error "#####inputs =$jarInput"
                project.logger.error "#####outputs =$dest"

                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }
}