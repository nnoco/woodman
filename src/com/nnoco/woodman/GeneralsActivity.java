package com.nnoco.woodman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nnoco.data.HandleExternalData;

public class GeneralsActivity extends Activity {
	private EditText tvGroup;
	private EditText tvStandardLandNo;
	private EditText tvGPSNo;
	private EditText tvPicture;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generals);

		init();
		setListeners();
	}

	private void setListeners() {
		findViewById(R.id.generals_btBack).setOnClickListener(mClickListener);
		findViewById(R.id.generals_btComplete).setOnClickListener(
				mClickListener);
	}

	private OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.generals_btBack:
				finish();
				break;

			case R.id.generals_btComplete:
				SharedPreferences pref = getSharedPreferences("application",
						MODE_PRIVATE);
				Editor editor = pref.edit();
				editor.putString("�Ӽҹ�", tvGroup.getText().toString());
				editor.commit();

				save();
				
				Toast.makeText(GeneralsActivity.this, "�����Ͽ����ϴ�",
						Toast.LENGTH_SHORT).show();
				
				finish();
				break;
			}
		}
	};

	private void init() {
		// ��� ����(��) �ʱ�ȭ
		tvGroup = (EditText) findViewById(R.id.generals_tvGroup);
		tvStandardLandNo = (EditText) findViewById(R.id.generals_tvStandardLandNo);
		tvGPSNo = (EditText) findViewById(R.id.generals_tvGPSNo);
		tvPicture = (EditText) findViewById(R.id.generals_tvPicture);

		// Ÿ��Ʋ�� �ʱ�ȭ
		TextView tvTitle = (TextView) findViewById(R.id.generals_tvTitle);
		tvTitle.setText(R.string.title_generals);

		// Preference �ҷ�����
		SharedPreferences pref = getSharedPreferences("application",
				MODE_PRIVATE);
		tvGroup.setText(pref.getString("�Ӽҹ�", ""));

		// ������ �ҷ�����
		load();

	}

	private void load() {
		File file = new File(HandleExternalData.CURRENT_DIR + HandleExternalData.GENERALS);
		if (file.exists()) {
			try {
				FileInputStream input = new FileInputStream(file);

				byte[] buf = new byte[input.available()];
				input.read(buf);

				String[] data = new String(buf).replace("\r", "").split("[\n\t]");
				tvGroup.setText(data[1]);
				tvStandardLandNo.setText(data[3]);
				tvGPSNo.setText(data[5]);

				input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void save() {
		File file = new File(HandleExternalData.CURRENT_DIR + HandleExternalData.GENERALS);
		try {
			FileOutputStream output = new FileOutputStream(file);

			StringBuilder builder = new StringBuilder();
			builder.append("�Ӽҹ�\t");
			builder.append(tvGroup.getText().toString());
			builder.append("\r\nǥ���� ��ȣ\t");
			builder.append(tvStandardLandNo.getText().toString());
			builder.append("\r\nGPS ��ȣ\t");
			builder.append(tvGPSNo.getText().toString());
			
			output.write(builder.toString().getBytes());

			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
