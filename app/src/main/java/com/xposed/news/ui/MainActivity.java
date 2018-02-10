package com.xposed.news.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;
import com.xposed.news.ViewPagerIndicatorAdapter;
import com.xposed.news.view.CustomViewPager;
import com.xposed.news.view.FlowLayout;
import com.xposed.news.view.IndicatorPopupWindow;

import ju.xposed.com.jumodle.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private TabPageIndicator mTabPageIndicator;
    private CustomViewPager mCustomViewPager;
    private ViewPagerIndicatorAdapter mAdapter;
    private ImageButton mArrowButton;

    private LayoutInflater mInflater;
    private View mIndicatorView;
    private IndicatorPopupWindow mIndicatorPopupWindow;
    private FlowLayout mCurrentFlowLayout;
    private FlowLayout mAllFlowLayout;

    private HeadlineFragment mHeadlineFragment;
    private EnjoyFragment mEnjoyFragment;
    private HotspotFragment mHotspotFragment;
    private SportFragment mSportFragment;
    private HouseFragment mHouseFragment;
    private NBAFragment mNBAFragment;
    private CBAFragment mCBAFragment;

    private MoreFragment mMoreFragment;
    private List<Fragment> mFragmentList;
    private List<TextView> mCurrentItemList;
    private List<TextView> mAllItemList;

    private static final String[] INDICATOR_CURRENT_ITEM = new String[] { "头条", "娱乐", "热点", "体育", "房产", "NBA", "CBA" };
    private static final String[] INDICATOR_ALL_ITEM = { "杭州", "财经", "科技", "跟帖", "直播", "时尚", "轻松一刻", "汽车", "段子", "军事",
                                                         "历史", "家居", "原创", "游戏", "健康", "政务", "漫画", "哒哒","彩票", "手机",
                                                         "移动互联", "中国足球", "社会", "影视", "国际足球", "跑步", "数码", "云课堂", "旅游", "读书",
                                                         "酒香", "教育", "亲子", "暴雪游戏", "情感", "艺术", "值得买", "图片", "博客", "论坛", "订阅" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();
        initData();
        data2View();
        initEvent();
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);

        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mCustomViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mCustomViewPager.setIsPagingEnabled(false);
        mArrowButton = (ImageButton) findViewById(R.id.indicator_arrow) ;

        mIndicatorView = mInflater.inflate(R.layout.activity_indicator, null);
        mIndicatorPopupWindow = new IndicatorPopupWindow(this, mIndicatorView);
        mCurrentFlowLayout = (FlowLayout) mIndicatorView.findViewById(R.id.current_flow_layout);
        mAllFlowLayout = (FlowLayout) mIndicatorView.findViewById(R.id.all_flow_layout);
    }

    private void initData() {
        mHeadlineFragment = new HeadlineFragment();
        mEnjoyFragment = new EnjoyFragment();
        mHotspotFragment = new HotspotFragment();
        mSportFragment = new SportFragment();
        mHouseFragment = new HouseFragment();
        mNBAFragment = new NBAFragment();
        mCBAFragment = new CBAFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mHeadlineFragment);
        mFragmentList.add(mEnjoyFragment);
        mFragmentList.add(mHotspotFragment);
        mFragmentList.add(mSportFragment);
        mFragmentList.add(mHouseFragment);
        mFragmentList.add(mNBAFragment);
        mFragmentList.add(mCBAFragment);

        mCurrentItemList = new ArrayList<>();

        mAllItemList = new ArrayList<>();

        //初始化当前条目
        for (int i = 0; i < INDICATOR_CURRENT_ITEM.length; i++) {
            TextView textView = (TextView) mInflater.inflate(R.layout.indicator_item_textview, mCurrentFlowLayout, false);
            textView.setText(INDICATOR_CURRENT_ITEM[i]);
            mCurrentItemList.add(textView);
            mCurrentFlowLayout.addView(textView);
        }

        //初始化候选条目
        for (int i = 0; i < INDICATOR_ALL_ITEM.length; i++) {
            TextView textView = (TextView) mInflater.inflate(R.layout.indicator_item_textview, mAllFlowLayout, false);
            textView.setText(INDICATOR_ALL_ITEM[i]);
            mAllItemList.add(textView);
            mAllFlowLayout.addView(textView);
        }
    }

    private void data2View() {
        mAdapter = new ViewPagerIndicatorAdapter(getSupportFragmentManager(), mFragmentList, mCurrentItemList);
        mCustomViewPager.setAdapter(mAdapter);

        //实例化ViewPagerIndicatorAdapter然后设置ViewPager与之关联
        mTabPageIndicator.setViewPager(mCustomViewPager, 0);
    }

    private void initEvent() {
        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrowButton.setBackgroundResource(R.drawable.arrow_up);

                //弹出
                mIndicatorPopupWindow.setAnimationStyle(R.style.popup_window_anim);
                mIndicatorPopupWindow.showAsDropDown(mArrowButton, 0, mTabPageIndicator.getHeight() / 2 - mArrowButton.getHeight());
            }
        });

        mIndicatorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mArrowButton.setBackgroundResource(R.drawable.arrow_down);
            }
        });

        //更新指示器，会调用getPageTitle方法
        mTabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabPageIndicator.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < mCurrentItemList.size(); i++) {
            final int position = i;
            final TextView textView = mCurrentItemList.get(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIndicatorPopupWindow.dismiss();
                    //直接显示
                    mTabPageIndicator.setViewPager(mCustomViewPager, mCurrentItemList.indexOf(textView));
                }
            });

            //长按删除事件
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mFragmentList.remove(position);
                    mCurrentItemList.remove(position);
                    mCurrentFlowLayout.removeView(textView);
                    mAdapter.notifyDataSetChanged();
                    //更新指示器
                    mTabPageIndicator.notifyDataSetChanged();

                    mAllItemList.add(textView);
                    mAllFlowLayout.addView(textView);

                    //先将textView的长按事件移除，然后再重新初始化所有的点击事件
                    textView.setLongClickable(false);
                    initEvent();
                    return true;
                }
            });
        }

        for (int i = 0; i < mAllItemList.size(); i++) {
            final TextView textView = mAllItemList.get(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加新的Fragment
                    mMoreFragment = new MoreFragment(textView.getText().toString());
                    mFragmentList.add(mMoreFragment);

                    //添加相应的标题
                    mCurrentItemList.add(textView);
                    //通知适配器数据更新
                    mAdapter.notifyDataSetChanged();

                    mAllItemList.remove(textView);
                    mAllFlowLayout.removeView(textView);

                    mCurrentFlowLayout.addView(textView);
                    //重新初始化点击事件
                    /**
                     * 从mAllItemList中移除一个元素添加到mCurrentItemList中
                     * 先将textView在mAllItemList的点击事件清除掉，然后再重新初始化所有的点击事件
                     * 否则，移除的元素的点击事件还是在mAllItemList中响应
                     */
                    textView.setClickable(false);
                    initEvent();
                }
            });
        }
    }
}
