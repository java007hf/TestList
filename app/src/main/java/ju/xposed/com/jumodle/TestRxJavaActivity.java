package ju.xposed.com.jumodle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sensetime.senseme.com.effects.avrecorder.FFmpegJNI;

public class TestRxJavaActivity extends AppCompatActivity {
    private static final String TAG = "TestRxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "==onCreate==");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        test();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void test() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");

//                FFmpegJNI.ffmpeg_run()
                subscriber.onCompleted();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "==onCompleted==");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "==onError==");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "==onNext=="+s);
            }
        };

        Log.d(TAG, "==observable.subscribe(observer);==");
        observable
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程;
                .subscribe(observer);

        Observable.just("images/logo.png") // 输入类型 String
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String filePath) { // 参数类型 String
                        return "path:"+filePath; // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String bitmap) { // 参数类型 Bitmap
                        Log.d(TAG, "==call=="+bitmap);
                    }
                });

    }
}
