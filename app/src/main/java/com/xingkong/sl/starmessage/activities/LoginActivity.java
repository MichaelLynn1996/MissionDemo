package com.xingkong.sl.starmessage.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xingkong.sl.starmessage.HttpCallbackListener;
import com.xingkong.sl.starmessage.R;
import com.xingkong.sl.starmessage.util.Global;
import com.xingkong.sl.starmessage.util.HttpUtil;


public class LoginActivity extends Activity {

    private Button regist;
    private Button login;
    private AppCompatEditText username;
    private AppCompatEditText usercode;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        if (pref.getBoolean("isAutoLogin", false)) {
            loginMethod(pref.getString("username", null), pref.getString("password", null));
        }

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = username.getText().toString();
                final String userPass = usercode.getText().toString();
                loginMethod(userName, userPass);
            }
        });
    }

    private void loginMethod(final String userName, final String userPass) {
        username.setEnabled(false);
        usercode.setEnabled(false);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
            Toast.makeText(LoginActivity.this, R.string.name_code_empty, Toast.LENGTH_SHORT).show();
        } else {
            HttpUtil.sendHttpRequest(Global.BASE_URL + Global.API_PARAM[0] + "?u=" + userName + "&p=" + userPass
                    , Global.REQUEST_METHOD[1], new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (response) {
                                        case "succ":
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            //SharedPreferences保存登陆信息
                                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                            editor.putString("username", userName);
                                            editor.putString("password", userPass);
                                            editor.putBoolean("isAutoLogin", true);
                                            editor.apply();

                                            Toast.makeText(LoginActivity.this, R.string.landing_succ, Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "error:0":
                                            Toast.makeText(LoginActivity.this, R.string.login_error0, Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:1":
                                            Toast.makeText(LoginActivity.this, R.string.login_error1, Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:2":
                                            Toast.makeText(LoginActivity.this, R.string.login_error2, Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:3":
                                            Toast.makeText(LoginActivity.this, R.string.login_error3, Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }
        username.setEnabled(true);
        usercode.setEnabled(true);
    }

    private void initViews() {
        regist = findViewById(R.id.bt_regist);
        login = findViewById(R.id.bt_landing);
        username = findViewById(R.id.et_username);
        usercode = findViewById(R.id.et_usercode);
        iv = findViewById(R.id.iv);
    }

}


