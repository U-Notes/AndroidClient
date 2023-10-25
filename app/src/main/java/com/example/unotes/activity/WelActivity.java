package com.example.unotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.unotes.MainActivity;
import com.example.unotes.R;

public class WelActivity extends AppCompatActivity {

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
//                if ()
                intent.setClass(getApplicationContext(), InstructionsActivity.class);
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();//销毁欢迎页面
            }
        }, 1000);//1秒后跳转
    }
}