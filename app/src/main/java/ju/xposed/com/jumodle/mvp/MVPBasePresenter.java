package ju.xposed.com.jumodle.mvp;

import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by p_kaelpu on 2017/2/28.
 */
public abstract class MVPBasePresenter<V> {
    protected Reference<V> mViewRef;

    // 绑定要控制的View
    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
        Log.d("MVPBasePresenter", this + " attachView mViewRef = " + mViewRef);
    }
    public abstract void create();
    public void resume(){}
    public void stop(){}
    public abstract void destroy();

    // 获得绑定的View
    protected V getView(){
        return mViewRef.get();
    }

    // 检查是否绑定了View
    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    // 解除View的绑定
    public void detachView(){
        if(null != mViewRef){
            mViewRef.clear();
            mViewRef = null;
        }
        
        Log.d("MVPBasePresenter", this + " detachView!!! mViewRef = " + mViewRef);
    }
}
