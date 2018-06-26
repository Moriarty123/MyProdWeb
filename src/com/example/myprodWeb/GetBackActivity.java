package com.example.myprodWeb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetBackActivity extends Activity implements OnClickListener {

	private EditText gb_username;
	private EditText gb_password;
	private Button btn_getback;
	private String result;

	public void initView() {
		gb_username = (EditText) findViewById(R.id.gb_number);
		gb_password = (EditText) findViewById(R.id.gb_password);
		btn_getback = (Button) findViewById(R.id.gb_btn);

		// btn_getback.setOnClickListener(this);
		btn_getback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// System.out.println(username);
				// String result = GetBackService.GetBackByClientGet(username);
				// System.out.println("result:"+result);
				// System.out.println("btn_getBack");

				
				
				//String username = gb_username.getText().toString();
				
//				System.out.println("activity result:" + result);
				
				final String username = gb_username.getText().toString().trim();
				Log.d("MyAndriod", username);
				if (TextUtils.isEmpty(username)) {
					
					// ʹ��UI�̵߳���toast
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(GetBackActivity.this, "�������û���", Toast.LENGTH_SHORT).show();
						}
					});
					
					return;
				}
				result = GetBackService.GetBackByClientGet(username);
				
				gb_password.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
				if (result != null && !result.equals("null")) {
					gb_password.setText(result);
					
					// ʹ��UI�̵߳���toast
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(GetBackActivity.this, "���룺"+result, 0).show();
							gb_password.setText(result);
						}
					});
				}
				else if (result.equals("null")) {
					
					// ʹ��UI�̵߳���toast
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(GetBackActivity.this, "�Ҳ������û�", 0).show();
							gb_password.setText("");
						}
					});
				}
				else {
					gb_password.setText("");
				}
				
				
			}

		});

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		final String username = gb_username.getText().toString().trim();
		Log.d("MyAndriod", username);
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "�������û���", Toast.LENGTH_SHORT).show();
			return;
		}

		new Thread() {// �������̷߳�������
			public void run() {
				// ����GetBackService����ķ������������ȡ����
				final String result = GetBackService
						.GetBackByClientGet(username);
				result.trim();
				// if ((result != null)) {
				// ʹ��UI�̵߳���toast
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(GetBackActivity.this, "���룺"+result, 0).show();
						System.out.println(result);
						
					}
				});
			};
		}.start();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getback);

		initView();
		
		gb_password.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
		if (result != null && !result.equals("null")) {
			gb_password.setText(result);
			
		}
		else {
			gb_password.setText(" ");
		}

	}

}
