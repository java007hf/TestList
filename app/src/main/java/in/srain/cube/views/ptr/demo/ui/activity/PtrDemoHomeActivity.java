package in.srain.cube.views.ptr.demo.ui.activity;

import android.os.Bundle;
import in.srain.cube.mints.base.MintsBaseActivity;
import ju.xposed.com.jumodle.R;
import in.srain.cube.views.ptr.demo.ui.PtrDemoHomeFragment;

public class PtrDemoHomeActivity extends MintsBaseActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ptr);
        pushFragmentToBackStack(PtrDemoHomeFragment.class, null);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}