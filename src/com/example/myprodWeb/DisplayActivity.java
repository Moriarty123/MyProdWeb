package com.example.myprodWeb;

import com.example.myprodWeb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity {
	String name;
	String password;
	String birthday;
	String sex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent=getIntent();
		name=intent.getStringExtra("name");
		password=intent.getStringExtra("password");
		birthday=intent.getStringExtra("birthday");
		sex=intent.getStringExtra("sex");
		TextView tv_name=(TextView)findViewById(R.id.tvname);
		TextView tv_password=(TextView)findViewById(R.id.tvpassword);
		TextView tv_birthday=(TextView)findViewById(R.id.tvbirthday);
		TextView tv_sex=(TextView)findViewById(R.id.tvsex);
		Button OK_btn=(Button)findViewById(R.id.OK_btn);
		tv_name.setText("������"+name);
		tv_password.setText("���룺"+password);
		tv_birthday.setText("���գ�"+birthday);
		tv_sex.setText("�Ա�"+sex);
		
        if(sex.equals("��") ) 
        	sex="M";
		else 
			sex="F";
        
		OK_btn.setOnClickListener(new OnClickListener(){
 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new Thread() {//�������̷߳�������
					public void run() {
		                   //����LoginService����ķ������������ȡ����
						final String result = RegService.RegByClientGet(name,password,birthday,sex);
						
						if ((result != null)&&result.equals("0")) {
		                        //ʹ��UI�̵߳���toast
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//Toast.makeText(LoginActivity.this, result, 0).show();
									Toast.makeText
									(
											DisplayActivity.this,
											"��ϲ����ע��ɹ�", 
											Toast.LENGTH_SHORT
									).show();
								}
							});
						} 
						else if((result != null)&&result.equals("2")) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//Toast.makeText(LoginActivity.this, result, 0).show();
									Toast.makeText
									(
											DisplayActivity.this,
											"��һ������ע��", 
											Toast.LENGTH_SHORT
									).show();
								}
							});
						}
						
						else {// ����ʧ�� ʹ��UI�̵߳���toast
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(DisplayActivity.this, "ע��ʧ��...", 0)
											.show();
								}
							});
						}
					};
				}.start();
				Intent intent=new Intent();
				
				intent.setClassName(DisplayActivity.this,"com.example.myprodWeb.LoginActivity");
				
				startActivity(intent);
			}
			
		});
		
	}

}
