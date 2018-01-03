package ju.xposed.com.jumodle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.xposed.utils.GetWebUtils;

/**
 * Created by benylwang on 2018/1/3.
 */

public class TestHttpActivity extends Activity {
    private static final String TAG = "TestHttpActivity";
    private static final String baseURL = "http://10.245.12.92:8080/users/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));

        new Thread() {
            @Override
            public void run() {
                test();
                super.run();
            }
        }.start();
    }

    private void test() {
        String getresult = GetWebUtils.geturl(baseURL);
        Log.d(TAG, "getresult = " + getresult);


        String param = "?id="+100+"&name="+"wyl"+"&age="+30;
        String postresult = GetWebUtils.posturl(baseURL+param);
        Log.d(TAG, "postresult = " + postresult);

        String getresult1 = GetWebUtils.geturl(baseURL);
        Log.d(TAG, "getresult1 = " + getresult1);

        String param1 = ""+100+"?name="+"benylwang"+"&age="+31;
        String putresult = GetWebUtils.puturl(baseURL+param1);
        Log.d(TAG, "postresult = " + putresult);

        String getresult2 = GetWebUtils.geturl(baseURL);
        Log.d(TAG, "getresult1 = " + getresult2);
    }
}
