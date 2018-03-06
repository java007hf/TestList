package com.code.library.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by yue on 2015/12/11.
 */
public class AppUtil {
    /**
     * 判断应用是否已安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm
                .getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            //print all package
            LogUtils.i("p.packageName===" + p.packageName);
            if (packageName != null && packageName.equals(p.packageName.toString())) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }

    public static void openCLD(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;

        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    public static Intent getApkFileIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    public static void openInstagram(Activity activity, String packageName) {
        Uri uri = Uri.parse("http://instagram.com/_u/xxx");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage(packageName);

        try {
            activity.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")));
        }
    }
}
