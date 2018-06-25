package com.example.myprodWeb;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button dialViewBtn;
	Button dialBtn;
	Button homeBtn;
	Button urlBtn;
	Button sendSmsBtm;
	Button openCameraBtn;
	Button prodActivityBtn;
	
	//新添加
	Button backAttenBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setListeners();
	}
    void init()
    {   
    	homeBtn =(Button)findViewById(R.id.homeBtn);
    	urlBtn=(Button)findViewById(R.id.urlBtn);
    	sendSmsBtm=(Button)findViewById(R.id.sendSmsBtm);
    	dialViewBtn =(Button)findViewById(R.id.dialViewBtn);
    	dialBtn=(Button)findViewById(R.id.dialBtn);
    	openCameraBtn=(Button)findViewById(R.id.openCameraBtn);
    	prodActivityBtn=(Button)findViewById(R.id.ProdActivityBtn);
    	
    	//新添加
    	backAttenBtn = (Button)findViewById(R.id.btnBackATTEN);
    }
    void setListeners()
    {
    	homeBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
    			startActivity(intent);
    		}
    		
    	}
    	);
    	urlBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("www.baidu.com"));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
    			startActivity(intent);
    		}
    		
    	}
    	);
    	sendSmsBtm.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction(Intent.ACTION_SENDTO);
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("smsto:10086"));
				intent.putExtra("sms_body", "土豪我们做朋友吧");
    			startActivity(intent);
    		}
    		
    	}
    	);
    	dialViewBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction(intent.ACTION_DIAL);
    			intent.setData(Uri.parse("tel:13800000000"));
    			startActivity(intent);
    		}
    		
    	}
    	);
    	dialBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction(intent.ACTION_CALL);
    			intent.setData(Uri.parse("tel:13800000000"));
    			startActivity(intent);
    		}
    		
    	}
    	);
    	openCameraBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setAction("android.media.action.IMAGE_CAPTURE");
				intent.addCategory("android.intent.category.DEFAULT");
    			startActivity(intent);
    		}
    		
    	}
    	);
    	prodActivityBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setClassName(MainActivity.this,"com.example.myprodWeb.ProdispActivity");
    			startActivity(intent);
    		}
    		
    	}
    	);
    	
    	//新添加
    	backAttenBtn.setOnClickListener(new OnClickListener(){
    		@Override 
    		public void onClick(View v){
    			Intent intent=new Intent();
    			intent.setClassName(MainActivity.this,"com.example.myprodWeb.MyContactActivity");
    			startActivity(intent);
    		}
    		
    	}
    	);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
