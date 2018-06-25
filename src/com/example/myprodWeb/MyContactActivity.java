package com.example.myprodWeb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.example.myprodWeb.R;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.widget.Button;

public class MyContactActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btn;
	private Button btn2;
	private Button btn3;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atten);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setTitle("save success!");
				// setTitle(str);

				try {
					save_Contacts();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setTitle("load success!");

				try {
					load_Contacts();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setTitle("delete all contacts");

				try {
					delete_Contacts();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// delete_Contacts();
			}

		});

	}

	public String str;

	public void getContact() {

		str = "";
		// 获得所有的联系人
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		// 循环遍历
		if (cur.moveToFirst()) {
			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);

			int displayNameColumn = cur
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			do {
				// 获得联系人的ID号
				String contactId = cur.getString(idColumn);
				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);
				str += disPlayName;
				// 查看该联系人有多少个电话号码。如果没有则返回值为0
				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					int i = 0;
					String phoneNumber;
					if (phones.moveToFirst()) {
						do {
							i++;
							phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							if (i == 1)
								str = str + "," + phoneNumber;
							System.out.println(phoneNumber);
						} while (phones.moveToNext());
					}

				}
				str += "\r\n";
			} while (cur.moveToNext());

		}
	}

	private void addContacts(String name, String num) {
		ContentValues values = new ContentValues();
		Uri rawContactUri = getContentResolver().insert(
				RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);

		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.GIVEN_NAME, name);
		// values.put(StructuredName.FAMILY_NAME, "Mike");

		getContentResolver().insert(Data.CONTENT_URI, values);

		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, num);
		values.put(Phone.TYPE, Phone.TYPE_HOME);
		// values.put(Email.DATA, "ligang.02@163.com");
		// values.put(Email.TYPE, Email.TYPE_WORK);
		getContentResolver().insert(Data.CONTENT_URI, values);
	}

	// 还原联系人
	public String load_Contacts() {

		try {
			delete_Contacts();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FileInputStream in = null;
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			// 设置将要打开的存储文件名称
			in = openFileInput("contacts.txt");
			// FileInputStream -> InputStreamReader ->BufferedReader
			reader = new BufferedReader(new InputStreamReader(in));
			String line = new String();
			// 读取每一行数据，并追加到StringBuilder对象中，直到结束
			while ((line = reader.readLine()) != null) {
				content.append(line);
				// content.append("\r\n");
				System.out.println(line);
			}

			String txt = content.toString();
			// setTitle(txt);

			String[] str = txt.split("\n");
			for (int i = 0; i < str.length; i++) {

				if (str[i].indexOf(",") >= 0) {
					String[] NameAndTel = str[i].split(",");
					addContacts(NameAndTel[0], NameAndTel[1]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}

	// 备份联系人
	public void save_Contacts() {
		getContact();

		String data = str;
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			// 设置文件名称，以及存储方式

			out = openFileOutput("contacts.txt", Context.MODE_PRIVATE);
			// 创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
			writer = new BufferedWriter(new OutputStreamWriter(out));
			// 向文件中写入数据
			writer.write(data);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 删除所有联系人
	public void delete_Contacts() {
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cur.moveToNext()) {
			try {
				String lookupKey = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
				Uri uri = Uri
						.withAppendedPath(
								ContactsContract.Contacts.CONTENT_LOOKUP_URI,
								lookupKey);
				System.out.println("The uri is " + uri.toString());
				cr.delete(uri, null, null);// 删除所有的联系人
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}

	public void testDelete() throws Exception {

		// 求id
		Uri uri = Uri.parse("content://com.android.contacts/data");
		ContentResolver resolver = getContentResolver();
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		if (cursor.moveToFirst()) {
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			// 根据id删除data中的相应数据
			resolver.delete(uri, "raw_contact_id=?", new String[] { idColumn
					+ "" });
		}
	}

	public void testDelete2() throws Exception {
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cur.moveToNext()) {
			try {
				String lookupKey = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
				Uri uri = Uri
						.withAppendedPath(
								ContactsContract.Contacts.CONTENT_LOOKUP_URI,
								lookupKey);
				System.out.println("The uri is " + uri.toString());
				cr.delete(uri, null, null);// 删除所有的联系人
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}
}
