package com.example.unotes.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.Manifest;

import com.example.unotes.MainActivity;
import com.example.unotes.R;
import com.example.unotes.utils.ActivityUtils;
import com.example.unotes.utils.SPUtils;

public class WelActivity extends AppCompatActivity {
    boolean isGranted = true;
    boolean isInstruction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler mHandler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                                //如已授权，则直接进入主页
                isInstruction = SPUtils.getBoolean("instruction", false, getApplicationContext());
                if (isInstruction) {
                    intent.setClass(getApplicationContext(), MainActivity.class);

                } else {
                    intent.setClass(getApplicationContext(), InstructionsActivity.class);
                }
                startActivity(intent);
                finish();//销毁欢迎页面
            }
        }, 1500);//1.5秒后跳转
    }


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


}