package ju.xposed.com.jumodle;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by benylwang on 2018/1/25.
 */

public class TestView extends View implements View.OnTouchListener {
    private static final String TAG = "TestView";

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "===onTouch===" + event.getAction() , new RuntimeException());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "===onTouchEvent===" + event.getAction(), new RuntimeException());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "===onDraw===", new RuntimeException());
        canvas.drawARGB(255, 255, 0, 0);
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "===draw===", new RuntimeException());
        super.draw(canvas);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.d(TAG, "===onInterceptTouchEvent===", new RuntimeException());
//        return super.onInterceptTouchEvent(ev);
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(400, 400);
//    }
}
