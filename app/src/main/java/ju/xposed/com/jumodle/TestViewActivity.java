package ju.xposed.com.jumodle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

/**
 * Created by benylwang on 2018/1/25.
 */

public class TestViewActivity extends Activity {
    private TestView mTestView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestView = new TestView(this);
        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        mTestView.setLayoutParams(layoutParams);
        setContentView(mTestView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTestView.invalidate();
    }
}
