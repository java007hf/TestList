package ju.xposed.com.jumodle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by benylwang on 2018/5/17.
 */

public class TestService extends Service {
    private static final String TAG = "TestService";

    @Override
    public void onCreate() {
        Log.d(TAG, "===onCreate===");
        Toast.makeText(this, "test service toast!!! onCreate", Toast.LENGTH_LONG).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "===onStartCommand===");
//        Toast.makeText(this, "test service toast!!!", Toast.LENGTH_LONG).show();
        Log.d(TAG, "===onStartCommand===toast");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "===onBind===");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "===onDestroy===");
        super.onDestroy();
    }
}
