package com.example.unotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unotes.MainActivity;
import com.example.unotes.R;
import com.example.unotes.databseHelper.UserDB;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;
    private EditText et_id,et_pwd;
    private Button mButton;
    private String TAG=LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        mButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id = et_id.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                if (id.equals("") || pwd.equals("")) {
                    Looper.prepare();
                    Toast toast = Toast.makeText(LoginActivity.this, "输入不能为空！", Toast.LENGTH_SHORT);
                    toast.show();
                    Looper.loop();
                }
                UserDB ud = new UserDB();
                Boolean result;
                try {
                    result = ud.login(id, pwd);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Looper.prepare();
                Toast toast;
                if (!result) {
                    toast = Toast.makeText(getApplicationContext(), "用户名不存在或密码错误！", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT);
                    startActivities(new Intent[]{new Intent(getApplicationContext(), MainActivity.class)});
                }
                toast.show();
                Looper.loop();
            }
        }).start();
    }
}