package com.example.unotes.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;

import com.example.unotes.MainActivity;
import com.example.unotes.R;
import com.example.unotes.bean.User;
import com.example.unotes.utils.ToastUtils;

public class InstructionsActivity extends AppCompatActivity {
    private Button btAgreeToUse;
    private Button btRefuseToUse;
    private TextView tv_instrucitons_known, tv_instrucitons_label;
    private CardView cv_instructions_describe;
    User user;
    boolean isGranted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setFullscreen(true, true);
        setAndroidNativeLightStatusBar(this, true);
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
            checkPermission();
            AlertDialog.Builder aler = new AlertDialog.Builder(this)
                        .setTitle("权限未开启")
                        .setPositiveButton("去设置", (dialog, which) -> {
                                    toSelfSetting(getApplicationContext());
                                }
                        ).setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        });
            if (!isGranted) {
                        aler.create().show();
            } else {
                startActivity(new Intent(getApplicationContext(), WelActivity.class));
            }
        });

    }

    //动态访问权限弹窗

    public Boolean checkPermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            Log.i("读写权限获取", " ： " + isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
            }
        }
        return isGranted;
    }

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

    public void gotoWelcome(View view) {
        startActivity(new Intent(getApplicationContext(), WelActivity.class));
    }

    public void cannelUse(View view) {

        onDestroy();
    }
}