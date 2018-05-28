package ju.xposed.com.jumodle;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by benylwang on 2018/5/28.
 */

//test by https://juejin.im/entry/593109e72f301e005830cd76
public class TestFutureTask {
    private ProgressDialog mProgressDialog = null;
    private ExecutorService mExecutor = null;
    private Context mContext;

    public void startDownLoad(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mContext = context;
        new DownloadTask().execute();
    }

    public void startExecutors() {
        mExecutor = Executors.newFixedThreadPool(5);
        mExecutor = Executors.newCachedThreadPool();
        mExecutor = Executors.newScheduledThreadPool(1);
        mExecutor = Executors.newSingleThreadExecutor();

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.i("myRunnable", "run");
            }
        };
        mExecutor.execute(myRunnable);
    }

    public void stopExecutors() {
        mExecutor.shutdown();
    }

    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                while (true) {
                    int downloadPercent = 100;//doDownload();
                    publishProgress(downloadPercent);
                    if (downloadPercent >= 100) {
                        break;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setMessage("当前下载进度：" + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.dismiss();
            if (result) {
                Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
