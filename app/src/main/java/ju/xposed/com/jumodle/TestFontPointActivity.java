package ju.xposed.com.jumodle;

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

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by benylwang on 2017/12/13.
 */

public class TestFontPointActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyView mMyView;
    private static Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "==onCreate==");
        super.onCreate(savedInstanceState);
        mMyView = new MyView(this);
        setContentView(mMyView);
//        test();
    }

    @Override
    protected void onResume() {
        super.onResume();

        int pix[] = text2pix("123456789012345678901234567890");
        mMyView.setPix(pix);
        mMyView.invalidate();
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

    private static final int MAX_HEIGHT = 8;
    private static final int TEXT_SHOW_HEIGHT = 12;
    private static int[] text2pix(String text) {
        Paint paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(11);

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w_pix = rect.width();
        int h_pix = MAX_HEIGHT*2;

        Log.d(TAG, "widget = " + w_pix + " , height = " + h_pix);

        mBitmap = Bitmap.createBitmap(w_pix, h_pix, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(mBitmap);

        canvas.drawText(text, 0, TEXT_SHOW_HEIGHT, paint);

        int pix[] = new int[h_pix * w_pix];
        mBitmap.getPixels(pix, 0, w_pix, 0, 0, w_pix, h_pix);

        int arraysize = w_pix*2;
        int array[] = new int[arraysize];
        for(int i=0;i<arraysize;i++) {
            int temp = 0;
            for(int j=MAX_HEIGHT-1;j>=0;j--) {
                int index = -1;
                if (i < w_pix) {
                    index = j*w_pix + i;
                } else {
                    index = (j+MAX_HEIGHT)*w_pix + (i-w_pix);
                }


                Log.d(TAG, "i = " + i + " , j= " + j + " index= " + index);
                Log.d(TAG, "pix = " + pix[index]);
                int bit = pix[index]==0xff000000 ? 1:0;

                Log.d(TAG, "******bit = " + bit);
                temp = (temp<<1)+bit;
                Log.d(TAG, "******temp = " + temp);
            }

            array[i] = temp;
        }

        return array;
    }

    class MyView extends View {
        private int pix[];

        public MyView(Context context) {
            super(context);
        }

        public void setPix(int pix[]) {
            this.pix = pix;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawColor(Color.RED);

            int lenght = pix.length/2;

            for(int i=0;i<2;i++) {
                for(int j=0;j<lenght;j++) {
                    int pixvalue = pix[i*lenght+j];
                    Log.d(TAG, "pixvalue = " + pixvalue);
                    for(int x=0;x<8;x++) {
                        int mash = 1<<x;
                        int b = pixvalue & mash;
                        Log.d(TAG, "j = " + j + " , x = " + x + " , b = " + b);
                        if (b!=0) {
                            if (i==0) {
                                canvas.drawPoint(j, 300+x, paint);
                            } else {
                                canvas.drawPoint(j, 500+x, paint);
                            }
                        }
                    }
                }
            }
        }
    }

}
