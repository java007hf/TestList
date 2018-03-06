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
        observable.subscribe(observer);
    }
}
