package com.example.unotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.FloatEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.unotes.activity.LoginActivity;
import com.example.unotes.adapter.MainPagerAdapter;
import com.example.unotes.view.SlidingMenu;

import static android.content.ContentValues.TAG;
import static com.example.unotes.constant.Constant.LOGIN_STATU_OFF;
import static com.example.unotes.constant.Constant.LOGIN_STATU_ON;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu vSlidingMenu;

    private ConstraintLayout devmenuList;
    private FrameLayout flMenuInfo;
    private Button btLogin;
    private LinearLayout llMenuInfo;
    private ImageView ivUserIcon;
    private LinearLayout llUserName;
    private TextView tvUserInfoName;
    private ImageView ivGoright;
    private TextView tvUserDept;
    private ImageView ivStatus;
    private RecyclerView rlMenuDept;
    private LinearLayout llMenuSetting;
    /**
     * 透明度估值器
     */
    private FloatEvaluator mAlphaEvaluator;
    private View vContentBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        bindView();
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
                vContentBg.setClickable(true);
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
        btLogin = (Button) findViewById(R.id.bt_login);
        llMenuInfo = (LinearLayout) findViewById(R.id.ll_menuInfo);
        ivUserIcon = (ImageView) findViewById(R.id.iv_userIcon);
        llUserName = (LinearLayout) findViewById(R.id.ll_userName);
        tvUserInfoName = (TextView) findViewById(R.id.tv_userInfoName);
        ivGoright = (ImageView) findViewById(R.id.iv_goright);
        tvUserDept = (TextView) findViewById(R.id.tv_userDept);
        ivStatus = (ImageView) findViewById(R.id.iv_status);
        rlMenuDept = (RecyclerView) findViewById(R.id.rl_MenuDept);
        llMenuSetting = (LinearLayout) findViewById(R.id.ll_menuSetting);
        /*
         * 原先业务端和角色端切换交互的旧方案，暂被弃用
         * */
//        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
//        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this);
//        viewPager2.setAdapter(mainPagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getIntExtra("loginStatu", LOGIN_STATU_OFF) == LOGIN_STATU_ON) {
            btLogin.setVisibility(View.INVISIBLE);
            llMenuInfo.setVisibility(View.VISIBLE);
        }
    }

    public void mainlogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}