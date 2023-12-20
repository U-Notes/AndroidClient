package com.example.unotes;

import static android.content.ContentValues.TAG;
import static com.example.unotes.constant.Constant.LOGIN_STATU_OFF;
import static com.example.unotes.constant.Constant.LOGIN_STATU_ON;

import android.animation.FloatEvaluator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.unotes.activity.LoginActivity;
import com.example.unotes.adapter.MyViewPagerAdapter;
import com.example.unotes.database.PagerSqlite;
import com.example.unotes.utils.ToastUtils;
import com.example.unotes.view.SlidingMenu;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu vSlidingMenu;
    private ConstraintLayout devmenuList;
    private FrameLayout flMenuInfo;
    private Button bt_gotologin;
    private LinearLayout llMenuInfo, llMenuSetting;
    private ImageView ivUserIcon, ivGoright, ivStatus;
    private LinearLayout llUserName;
    private TextView tvUserInfoName, tvUserDept;
    private RecyclerView rlMenuDept;
    private TabLayout tl_dev;
    private ImageButton ib_add;
    private ViewPager2 viewPager2;
    /**
     * 透明度估值器
     */
    private FloatEvaluator mAlphaEvaluator;
    private View vContentBg;
    PagerSqlite pagerSqlite;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        bindView();
        initEvent();
    }

    private void initEvent() {
        pagerSqlite = new PagerSqlite(this);
//        db = pagerSqlite.getReadableDatabase();
    }

    private void bindView() {
        //创建估值器
        mAlphaEvaluator = new FloatEvaluator();

        //------------ 重点：设置侧滑菜单的状态切换监听 ------------
        vSlidingMenu.setOnMenuStateChangeListener(new SlidingMenu.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen() {
                Log.d(TAG, "菜单打开");
                //让黑色遮罩，禁用触摸
                vContentBg.setClickable(false);
            }

            @Override
            public void onSliding(float fraction) {
                Log.d(TAG, "菜单拽托中，百分比：" + fraction);
                //设定最小、最大透明度值
                float startValue = 0;
                float endValue = 0.55f;
                //估值当前的透明度值，并设置
                Float value = mAlphaEvaluator.evaluate(fraction, startValue, endValue);
                vContentBg.setAlpha(value);
            }

            @Override
            public void onMenuClose() {
                Log.d(TAG, "菜单关闭");
                //让黑色遮罩，恢复触摸
                vContentBg.setClickable(false);
            }
        });
        //------------ 重点：设置侧滑菜单的状态切换监听 ------------
    }

    private void initview() {
        vSlidingMenu = findViewById(R.id.sliding_menu);
        vContentBg = findViewById(R.id.content_bg);
        devmenuList = (ConstraintLayout) findViewById(R.id.devmenu_list);
        flMenuInfo = (FrameLayout) findViewById(R.id.fl_menuInfo);
        bt_gotologin = (Button) findViewById(R.id.bt_gotologin);
        llMenuInfo = (LinearLayout) findViewById(R.id.ll_menuInfo);
        ivUserIcon = (ImageView) findViewById(R.id.iv_userIcon);
        llUserName = (LinearLayout) findViewById(R.id.ll_userName);
        tvUserInfoName = (TextView) findViewById(R.id.tv_userInfoName);
        ivGoright = (ImageView) findViewById(R.id.iv_goright);
        tvUserDept = (TextView) findViewById(R.id.tv_userDept);
        ivStatus = (ImageView) findViewById(R.id.iv_status);
        rlMenuDept = (RecyclerView) findViewById(R.id.rl_MenuDept);
        llMenuSetting = (LinearLayout) findViewById(R.id.ll_menuSetting);
        tl_dev = findViewById(R.id.tl_dev);
        ib_add = findViewById(R.id.ib_add);
        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.bringToFront();
//        viewPager2.setOnTouchListener((view,event) -> {
//            int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//                    view.getParent().requestDisallowInterceptTouchEvent(true);
//                    break;
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    view.getParent().requestDisallowInterceptTouchEvent(true);
//                    break;
//            }
//            return false;
//        });
        /*
         * 原先业务端和角色端切换交互的旧方案，暂被弃用
         * */
//        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
//        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this);
//        viewPager2.setAdapter(mainPagerAdapter);
        ib_add.setOnClickListener((click) -> {
            ToastUtils.showToast(this, "click");

        });
        // 设置ViewPager2的适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        if (viewPager2.getAdapter() != null) {
            // 使用TabLayoutMediator将TabLayout与ViewPager2关联
            new TabLayoutMediator(tl_dev, viewPager2, (tab, position) -> {
                // 设置标签页标题
                tab.setText("Tab " + (position + 1));
            }).attach();
        }

        tl_dev.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setText("awa");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("" + tab.getId());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 在手指按下时，请求父容器不要拦截事件
                viewPager2.getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 在手指抬起或取消时，请求父容器可以拦截事件
                viewPager2.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getIntExtra("loginStatu", LOGIN_STATU_OFF) == LOGIN_STATU_ON) {
            bt_gotologin.setVisibility(View.INVISIBLE);
            llMenuInfo.setVisibility(View.VISIBLE);
        }
    }

    public void mainlogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}