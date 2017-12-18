package cn.sealynn0.thefinalexamdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by SeaLynn0 on 2017/10/16.
 */

public class RegisterActivity extends AppCompatActivity {

	Button signUp;
	EditText et_usersName;
	EditText et_password;
	EditText et_passwordV2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		findViewById();

		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (et_usersName.getText().length() == 0
						|| et_password.getText().length() == 0
						|| et_passwordV2.getText().length() == 0) {
					// 判断输入为空的情况
					Toast.makeText(RegisterActivity.this, R.string.INPUT_EMPTY2,
							Toast.LENGTH_SHORT).show();
				} else {
					if (!et_password.getText().toString()
							.equals(et_passwordV2.getText().toString())) {
						// 判断两次密码输入是否相同
						Toast.makeText(RegisterActivity.this, R.string.NOT_THE_SAME_PASSWORD, Toast.LENGTH_SHORT).show();
					} else if (Data.userList.containsKey(et_usersName.getText()
							.toString())) {
						// 在HashMap中遍历，判断用户是否存在
						Toast.makeText(RegisterActivity.this, R.string.ALREADY_EXIST, Toast.LENGTH_SHORT).show();
					} else {
						// 符合要求
						Data.userList.put(et_usersName.getText().toString(),
								et_password.getText().toString());
						Toast.makeText(RegisterActivity.this, R.string.SIGNUP_DONE, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
						intent.putExtra("usersname", et_usersName.getText().toString());
						intent.putExtra("password", et_password.getText().toString());

						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				}
			}
		});

	}

	void findViewById() {
		signUp = (Button) findViewById(R.id.bt_register);
		et_usersName = (EditText) findViewById(R.id.signup_usersname);
		et_password = (EditText) findViewById(R.id.signup_password);
		et_passwordV2 = (EditText) findViewById(R.id.signUp_passwordRe);
	}
}
