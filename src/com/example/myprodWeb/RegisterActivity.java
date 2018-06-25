package com.example.myprodWeb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import com.example.myprodWeb.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Button btn_send;
	EditText et_name;
	EditText et_password;
	EditText et_birthday;
	private Button btn_save;
	private Button btn_read;
	RadioButton radioMale;
	RadioButton radioFemale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		btn_send = (Button) findViewById(R.id.btn_OK);
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		et_birthday = (EditText) findViewById(R.id.et_birthday);
		// tvbirthday=(TextView)findViewById(R.id.tvbirthday);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_read = (Button) findViewById(R.id.btn_read);
		radioMale = (RadioButton) findViewById(R.id.radioMale);
		radioFemale = (RadioButton) findViewById(R.id.radioFemale);
		// ȡ���û���������

		Map<String, String> userInfo = Utils.getUserInfo(this);
		if (userInfo != null) {
			// ��ʾ�ڽ�����
			et_name.setText(userInfo.get("name"));
			et_password.setText(userInfo.get("password"));
		}

		btn_save.setOnClickListener(new ButtonListener());
		btn_read.setOnClickListener(new ButtonListener());

		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String name = et_name.getText().toString().trim();
				String password = et_password.getText().toString().trim();
				String birthday = et_birthday.getText().toString().trim();
				String sex = "";
				if (radioMale.isChecked()) {
					sex = "��";
				} else if (radioFemale.isChecked()) {
					sex = "Ů";
				}
				// У�����������ʽ�Ƿ���ȷ
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(RegisterActivity.this, "�������ֻ�����",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(RegisterActivity.this, "����������",
							Toast.LENGTH_SHORT).show();

					return;
				}
				if (TextUtils.isEmpty(birthday)) {
					Toast.makeText(RegisterActivity.this, "��ѡ������",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Log.i("MainActivity", "��ס����: " + name + ", " + password);

				Intent intent = new Intent();

				// intent.setClass(RegisterActivity.this,DisplayActivity.class);
				intent.setClassName(RegisterActivity.this,
						"com.example.myprodWeb.DisplayActivity");
				intent.putExtra("name", name);
				intent.putExtra("password", password);
				intent.putExtra("birthday", birthday);
				intent.putExtra("sex", sex);

				//������ע����˺ŵ�data.xml
				Utils.saveUserInfo(getBaseContext(), name, password);
				
				startActivity(intent);

			}

		});

		et_birthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, BirthdayActivity.class);
				startActivityForResult(intent, 1);
			}

		});

	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_save:
				String saveinfo = et_name.getText().toString().trim();
				FileOutputStream fos;
				try {
					fos = openFileOutput("data.txt", Context.MODE_APPEND);
					fos.write(saveinfo.getBytes());
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Toast.makeText(RegisterActivity.this, "���ݱ���ɹ�", 0).show();
				break;
			case R.id.btn_read:
				String content = "";
				try {
					FileInputStream fis = openFileInput("data.txt");
					byte[] buffer = new byte[fis.available()];
					fis.read(buffer);
					content = new String(buffer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				et_name.setText(content);
				Toast.makeText(RegisterActivity.this, "����������ǣ�" + content, 0)
						.show();
				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		et_birthday = (EditText) findViewById(R.id.et_birthday);
		if (resultCode == 1) {
			String strdata = data.getStringExtra("birthday_data");
			// spbirthday.
			et_birthday.setText(strdata);
		}
	}

}
