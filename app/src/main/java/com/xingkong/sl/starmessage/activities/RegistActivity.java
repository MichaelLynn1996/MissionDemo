package com.xingkong.sl.starmessage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xingkong.sl.starmessage.HttpCallbackListener;
import com.xingkong.sl.starmessage.R;
import com.xingkong.sl.starmessage.util.Global;
import com.xingkong.sl.starmessage.util.HttpUtil;

/**
 * Created by LinHai on 2017/1/9.
 */

public class RegistActivity extends Activity {

    private Button back;
    private Button regist;
    private AppCompatEditText username;
    private AppCompatEditText password1;
    private AppCompatEditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                finish();
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setEnabled(false);
                password1.setEnabled(false);
                password2.setEnabled(false);
                final String userName = username.getText().toString();
                final String userPass = password1.getText().toString();
                final String reuserPass = password2.getText().toString();

                if (userPass.equals(reuserPass)) {
                    HttpUtil.sendHttpRequest(Global.BASE_URL + Global.API_PARAM[1] + "?u=" + userName + "&p="
                            + userPass, Global.REQUEST_METHOD[1], new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (response){
                                        case "succ":
                                            startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                                            Toast.makeText(RegistActivity.this,R.string.regist_succ,Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "error:0":
                                            Toast.makeText(RegistActivity.this,R.string.name_repeat,Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:1":
                                            Toast.makeText(RegistActivity.this,R.string.name_toolong,Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:2":
                                            Toast.makeText(RegistActivity.this,R.string.login_error2,Toast.LENGTH_SHORT).show();
                                            break;
                                        case "error:3":
                                            Toast.makeText(RegistActivity.this,R.string.login_error3,Toast.LENGTH_SHORT).show();
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
                password1.setEnabled(true);
                password2.setEnabled(true);
            }
        });
    }

    private void initViews() {
        back = findViewById(R.id.back);
        regist = findViewById(R.id.Button);
        username = findViewById(R.id.regist_user);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
    }
}
