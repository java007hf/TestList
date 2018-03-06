package ju.xposed.com.jumodle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by benylwang on 2017/12/13.
 */

public class TestRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "TestRecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private List<DataInfo> mDatas;
    private RecyclerViewAdapter mAdapter;
    private Random random = new Random(12313123l);

    class DataInfo {
        String text;
        int height;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        getSupportActionBar().setIcon(ju.xposed.com.jumodle.R.drawable.ic_launcher);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        // 设置组件复用回收池
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter = new RecyclerViewAdapter(this, mDatas));
        mAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(TestRecyclerViewActivity.this, "onItemClick : " + position, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(TestRecyclerViewActivity.this, "onItemLongClick : " + position, Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "remove item", Toast.LENGTH_SHORT)
                        .show();

                mDatas.remove(0);

                mAdapter.notifyItemRemoved(0);
//                mAdapter.notifyDataSetChanged();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "add item", Toast.LENGTH_SHORT)
                        .show();
                DataInfo dataInfo = new DataInfo();

                dataInfo.height = 50 + random.nextInt(400);
                dataInfo.text = "" + dataInfo.height;
                mDatas.add(dataInfo);

                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return true;
    }

    protected void initData() {
        mDatas = new ArrayList<DataInfo>();
        for (int i = 'A'; i < 'z'; i++) {
            DataInfo d = new DataInfo();
            d.text = "" + (char) i;
            d.height = 50 + random.nextInt(400);
            mDatas.add(d);
        }
    }
}
