package com.example.unotes.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.unotes.R;
import com.example.unotes.utils.ActivityUtils;
import com.example.unotes.utils.PermissionUtils;
import com.example.unotes.utils.SPUtils;

/**
 * @author WTL
 * @date 2023/10/27
 */
public class InstructionsActivity extends AppCompatActivity {
    private Button btAgreeToUse;
    private Button btRefuseToUse;
    private TextView tv_instrucitons_known, tv_instrucitons_label;
    private CardView cv_instructions_describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setFullscreen(true, true);
        setAndroidNativeLightStatusBar(this, true);
        SPUtils.getBoolean("instruction",false,this);
        initview();
        initEvent();
    }
    /*
    * 跳转到应用设置界面
    * */
    public static void toSelfSetting(Context context) {

        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);

    }
    private void initEvent() {
        btAgreeToUse.setOnClickListener((click)->{

            SPUtils.putBoolean("permission",  PermissionUtils.checkPermission(this), this);
            SPUtils.putBoolean("instruction",true,this);
                startActivity(new Intent(this, LoginActivity.class));
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    //动态访问权限弹窗
    private void initview() {
        //获取屏幕宽高
        Resources resource = this.getResources();
        DisplayMetrics metrics = resource.getDisplayMetrics();
        tv_instrucitons_label = findViewById(R.id.tv_instrucitons_label);
        cv_instructions_describe = findViewById(R.id.cv_instructions_describe);
        btAgreeToUse = (Button) findViewById(R.id.bt_agreeToUse);
        btRefuseToUse = (Button) findViewById(R.id.bt_refuseToUse);
        tv_instrucitons_known = (TextView) findViewById(R.id.tv_instrucitons_known);
        AnimatorSet animatorSet = new AnimatorSet();
        /*
         * 动画显示
         * 先进行标题行的透明度动画，之后再进行标题行，说明行，描述控件的上移动画
         * */
        ObjectAnimator oa_title_move = ObjectAnimator.ofFloat(tv_instrucitons_label, "translationY", metrics.widthPixels / 2, 0);
        ObjectAnimator oa_title_stop = ObjectAnimator.ofFloat(tv_instrucitons_label, "translationY", metrics.widthPixels / 2, metrics.widthPixels / 2);
        ObjectAnimator oa_title_show = ObjectAnimator.ofFloat(tv_instrucitons_label, "alpha", 0, 1);

        ObjectAnimator oa_kwon_move = ObjectAnimator.ofFloat(tv_instrucitons_known, "translationY", metrics.widthPixels * 2, 0);
        ObjectAnimator oa_kwon_show = ObjectAnimator.ofFloat(tv_instrucitons_known, "alpha", 0, 1);

        ObjectAnimator oa_describe_show = ObjectAnimator.ofFloat(cv_instructions_describe, "alpha", 0, 1);
        ObjectAnimator oa_describe_move = ObjectAnimator.ofFloat(cv_instructions_describe, "translationY", metrics.widthPixels * 2, 0);
        animatorSet.play(oa_describe_move).with(oa_describe_show).after(oa_kwon_move).with(oa_kwon_show).after(oa_title_show).after(oa_title_stop).after(3000).after(oa_title_move);

        animatorSet.setDuration(1000);
        animatorSet.start();

    }

    public void setFullscreen(boolean isShowStatusBar, boolean isShowNavigationBar) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (!isShowStatusBar) {
            uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (!isShowNavigationBar) {
            uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        //隐藏标题栏
        // getSupportActionBar().hide();
        setNavigationStatusColor(Color.TRANSPARENT);
    }

    public void setNavigationStatusColor(int color) {
        //VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(color);
            getWindow().setStatusBarColor(color);
        }
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    public void cannelUse(View view) {

        onDestroy();
    }
}