package com.example.myprodWeb;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

import com.example.myprodWeb.R;
import java.sql.Statement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText etNumber;
	private EditText etPassword;
//	private CheckBox cbRemember;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();
		
		// ȡ������
		Map<String, String> userInfo = Utils.getUserInfo(this);
		
		if(userInfo != null) {
			// ��ʾ�ڽ�����
			etNumber.setText(userInfo.get("number"));
			etPassword.setText(userInfo.get("password"));
		}
		
	}
	
	private void initView() {
		etNumber = (EditText) findViewById(R.id.et_number);
		etPassword = (EditText) findViewById(R.id.et_password);
		Button btn_register=(Button)findViewById(R.id.btn_register);
//		cbRemember = (CheckBox) findViewById(R.id.cb_remember);
		findViewById(R.id.btn_login).setOnClickListener(this);
		btn_register.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				
				intent.setClassName(LoginActivity.this,"com.example.myprodWeb.RegisterActivity");
				
				startActivity(intent);
			}

		});
	}

	public void onClick(View v) {
		// �������¼ʱ,��ȡ�˺� ������
		final String number = etNumber.getText().toString().trim();
		final String password = etPassword.getText().toString().trim();
		// У�����������Ƿ���ȷ
		if(TextUtils.isEmpty(number)) {
			Toast.makeText(this, "�������ֻ�����", Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(password)) {
			Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		
		new Thread() {//�������̷߳�������
			public void run() {
                   //����LoginService����ķ������������ȡ����
				final String result = LoginService.loginByClientGet(number,password);
				result.trim();
				if ((result != null)&&result.equals("0")) {
				//if ((result != null)) {
                        //ʹ��UI�̵߳���toast
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//Toast.makeText(LoginActivity.this, result, 0).show();
							Intent intent=new Intent();
							
							intent.setClassName(LoginActivity.this,"com.example.myprodWeb.MainActivity");
							
							startActivity(intent);
						}
					});
				} else {// ����ʧ�� ʹ��UI�̵߳���toast
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(LoginActivity.this, "��¼ʧ��...", 0)
									.show();
						}
					});
				}
			};
		}.start();
		
		// �����ȷ, �ж��Ƿ�ѡ�˼�ס����
       //		if(cbRemember.isChecked()) {
			Log.i("MainActivity", "��ס����: " + number + ", " + password);
			// �����û���Ϣ
			boolean isSaveSuccess = Utils.saveUserInfo(this, number, password);
			if(isSaveSuccess) {
				Toast.makeText(this, "����ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
//		}
	}
}

