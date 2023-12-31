package com.example.unotes.activity;

import static com.example.unotes.constant.Constant.LOGIN_STATU_ON;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unotes.MainActivity;
import com.example.unotes.R;
import com.example.unotes.database.UserDao;
import com.example.unotes.utils.ToastUtils;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_loginName;
    private EditText et_id, et_pwd;
    private Button bt_login;
    private String TAG = LoginActivity.class.getSimpleName();
    private CheckBox cb_rememberPwd;
    boolean isRead = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        tv_loginName = (TextView) findViewById(R.id.tv_loginName);
        bt_login = (Button) findViewById(R.id.bt_login);
        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        bt_login.setOnClickListener(this);
        cb_rememberPwd = findViewById(R.id.cb_rememberPwd);
        //记住密码
        cb_rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRead = isChecked;
            }
        });
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
                if (!isRead) {
                    Looper.prepare();
                    ToastUtils.showToast(getApplicationContext(), "请同意协议", 200);
                    Looper.loop();
                }
                UserDao ud = new UserDao();
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
                    Intent intent = getIntent();
                    intent.putExtra("loginStatu", LOGIN_STATU_ON);
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivities(new Intent[]{intent});
                }
                toast.show();
                Looper.loop();
            }
        }).start();
    }
}