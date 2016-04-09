package com.nnoco.woodman;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nnoco.data.DBHelper;
import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;

public class WoodmanActivity extends Activity {
	public static Context CONTEXT;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setListeners();
	}
	
	private void setListeners(){
		findViewById(R.id.main_newWork).setOnClickListener(mClickListener);
		findViewById(R.id.main_Load).setOnClickListener(mClickListener);
		findViewById(R.id.main_transmission).setOnClickListener(mClickListener);
		findViewById(R.id.main_preference).setOnClickListener(mClickListener);
		findViewById(R.id.main_exit).setOnClickListener(mClickListener);
		
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = null;

			switch (v.getId()) {
			case R.id.main_newWork:
				HandleExternalData.newJob();
				intent = new Intent(WoodmanActivity.this, NewWorkActivity.class);
				startActivity(intent);
				break;

			case R.id.main_Load:
				intent = new Intent(WoodmanActivity.this, LoadActivity.class);
				startActivity(intent);
				break;

			case R.id.main_transmission:
				intent = new Intent(WoodmanActivity.this,
						TransmissionActivity.class);
				startActivity(intent);
				break;

			case R.id.main_preference:
				intent = new Intent(WoodmanActivity.this,
						PreferenceActivity.class);
				startActivity(intent);
				break;

			case R.id.main_exit:
				finish();
				break;
			default:

				break;
			}
		}

	};
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		DBHelper.READABLE_DB.close();
		DBHelper.WRITABLE_DB.close();
		DBHelper.helper.close();
	}
}