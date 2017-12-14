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

public class TestRecyclerViewActivity extends Activity {
    private static final String TAG = "TestRecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private List<DataInfo> mDatas;
    private HomeAdapter mAdapter;
    private Random random = new Random(12313123l);

    class DataInfo {
        String text;
        int height;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        getActionBar().setIcon(ju.xposed.com.jumodle.R.drawable.ic_launcher);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,        StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

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

                dataInfo.height = 50+ random.nextInt(400);
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
            d.height = 50+ random.nextInt(400);
            mDatas.add(d);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    TestRecyclerViewActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position).text);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(50, mDatas.get(position).height);
            holder.tv.setLayoutParams(layoutParams);

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
