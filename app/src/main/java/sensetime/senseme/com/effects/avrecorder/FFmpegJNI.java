package sensetime.senseme.com.effects.avrecorder;


import android.util.Log;

public class FFmpegJNI {
    public static final String TAG = "FFmpegJNI";

    static {
        try {
            String ffmpeg = "ffmpeg-7neon";
            Log.d(TAG, "loadLibrary " + ffmpeg);
            System.loadLibrary(ffmpeg);

            String jni = "ffmpeg-jni";
            Log.d(TAG, "loadLibrary " + jni);
            System.loadLibrary(jni);

            classInitNative();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static native void classInitNative();
    public static native int ffmpeg_run(String command);
    public static native int ffmpeg_progress();
    public static native void ffmpeg_clear();
}
