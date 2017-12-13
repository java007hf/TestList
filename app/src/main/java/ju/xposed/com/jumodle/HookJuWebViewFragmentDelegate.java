package ju.xposed.com.jumodle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by benylwang on 2017/10/25.
 */

public class HookJuWebViewFragmentDelegate implements IXposedHookLoadPackage {
    private static final String TAG = "HookJuWebViewFragmentDelegate";
    private ClassLoader classLoader;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        Log.e(TAG, "handleLoadPackage loadPackageParam = " + loadPackageParam.packageName);

        if (loadPackageParam.packageName.equals("com.taobao.ju.android")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (classLoader != null) return;

                    Context context = (Context) param.args[0];
                    classLoader = context.getClassLoader();
                    hookMethod();
                }
            });
        }
    }

    private void hookMethod() throws ClassNotFoundException {
        Log.e(TAG, "HOOK shouldPrintInfo ====== ");
        Class cls_TaoLog = classLoader.loadClass("android.taobao.windvane.util.TaoLog");
        XposedHelpers.findAndHookMethod(cls_TaoLog, "i", String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "Log " + param.args[0] + " : " + param.args[1]);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        Log.e(TAG, "HOOK JuWebViewFragmentDelegate ====== ");
        Class cls_JuWebViewFragmentDelegate = classLoader.loadClass("com.taobao.ju.android.common.web.JuWebViewFragmentDelegate");
        Log.e(TAG, "HOOK JuWebViewFragmentDelegate m ====== ");

        try {
            XposedHelpers.findAndHookMethod(cls_JuWebViewFragmentDelegate, "load", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    String url = (String) param.args[0];
                    Log.d(TAG, "cls_JuWebViewFragmentDelegate loadUrl " + url);
                }
            });
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        } catch (NoClassDefFoundError error) {
            error.printStackTrace();
            Log.d(TAG, error.toString());
        }

        try {
            Log.e(TAG, "HOOK JuWebViewController ====== ");
            Class cls_JuWebViewController = classLoader.loadClass("com.taobao.ju.android.h5.ui.JuWebViewController");
            XposedHelpers.findAndHookMethod(cls_JuWebViewController, "loadUrl", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    String url = (String) param.args[0];
                    Log.d(TAG, "cls_JuWebViewController loadUrl " + url);
                }
            });
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }

        try {
            Log.e(TAG, "HOOK WVUCWebView ====== ");
            Class cls_WVUCWebView = classLoader.loadClass("android.taobao.windvane.extra.uc.WVUCWebView");
            XposedHelpers.findAndHookMethod(cls_WVUCWebView, "loadUrl", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    String url = (String) param.args[0];
                    Log.d(TAG, "WVUCWebView loadUrl " + url);
                }
            });
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }

        try {
            Log.e(TAG, "HOOK WVUCWebViewClient ====== ");

            Class cls_WebView = classLoader.loadClass("com.uc.webview.export.WebView");
            Class cls_WVUCWebViewClient = classLoader.loadClass("android.taobao.windvane.extra.uc.WVUCWebViewClient");
            XposedHelpers.findAndHookMethod(cls_WVUCWebViewClient, "shouldOverrideUrlLoading", cls_WebView, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    String url = (String) param.args[1];
                    Log.d(TAG, "WVUCWebViewClient shouldOverrideUrlLoading " + url);
                }
            });
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
    }
}
