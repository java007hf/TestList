package ju.xposed.com.jumodle.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by p_kaelpu on 2017/2/28.
 */
public abstract class MVPBaseActivity<V,P extends MVPBasePresenter<V>> extends AppCompatActivity {
    protected P mPresenter ; // Presenter 对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView((V)this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.resume();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPresenter != null){
            mPresenter.stop();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
