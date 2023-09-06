package com.example.unotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.unotes.R;
import com.example.unotes.bean.User;

public class InstructionsActivity extends AppCompatActivity {
    private Button btAgreeToUse;
    private Button btRefuseToUse;
    private TextView tvUserKnown;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setFullscreen(true, true);
        setAndroidNativeLightStatusBar(this, true);
        initview();
    }

    private void initview() {


        btAgreeToUse = (Button) findViewById(R.id.bt_agreeToUse);
        btRefuseToUse = (Button) findViewById(R.id.bt_refuseToUse);
        tvUserKnown = (TextView) findViewById(R.id.tv_user_known);



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
        startActivity(new Intent(getApplicationContext(),WelActivity.class));
    }

    public void cannelUse(View view) {

        onDestroy();
    }
}