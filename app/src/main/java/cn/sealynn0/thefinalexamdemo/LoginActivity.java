package cn.sealynn0.thefinalexamdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SeaLynn0 on 2017/10/16.
 */

public class LoginActivity extends AppCompatActivity {
    EditText et_usersName;
    EditText et_password;
    ImageButton login;
    TextView signUp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findviewbyid();

        // 注册完后自动设置账号密码
        Intent getData = getIntent();
        if (getData.getStringExtra("usersname") != null) {
            et_usersName.setText(getData.getStringExtra("usersname"));
            et_password.setText(getData.getStringExtra("password"));
        }

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    view.setBackgroundResource(R.drawable.denglu02);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的图片
                    view.setBackgroundResource(R.drawable.denglu);
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 判断账号密码非空
                if (et_usersName.getText().length() == 0
                        || et_password.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, R.string.INPUT_EMPTY, Toast.LENGTH_SHORT).show();
                } else {
                    if (!Data.userList.containsKey(et_usersName.getText()
                            .toString())) {
                        // 不存在该用户
                        Toast.makeText(LoginActivity.this, R.string.NOT_A_USERNAME, Toast.LENGTH_SHORT).show();
                    } else if (!et_password
                            .getText()
                            .toString()
                            .equals(Data.userList.get(et_usersName.getText()
                                    .toString()))) {
                        // 密码错误
                        Toast.makeText(LoginActivity.this, R.string.WRONG_PASSWORD, Toast.LENGTH_SHORT).show();
                    } else {
                        //框框部分
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle(R.string.hint)
                                .setMessage(R.string.getpassed)
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                et_password.setText("");
                                                et_usersName.setText("");
                                            }
                                        })
                                .create().show();
                    }
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void findviewbyid() {
        et_usersName = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login = (ImageButton) findViewById(R.id.bt_login);
        signUp = (TextView) findViewById(R.id.tv_signUp);
    }
}