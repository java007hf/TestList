package ju.xposed.com.jumodle;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by benylwang on 2018/2/9.
 */

public class TestViewGroup extends LinearLayout {
    private static final String TAG = "TestViewGroup";

    public TestViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "===onTouchEvent==" + event.getAction() , new RuntimeException());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "===dispatchTouchEvent==" + ev.getAction() , new RuntimeException());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "===onInterceptTouchEvent==" + ev.getAction() , new RuntimeException());
        if (ev.getAction() == 2) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
