package com.nnoco.woodman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.TextView;

import com.nnoco.data.DBHelper;
import com.nnoco.data.HandleExternalData;
import com.nnoco.data.PlaySound;
import com.nnoco.data.Util;

public class LoadingActivity extends Activity implements Runnable{
	private TextView tvStatus;
	private boolean criticalError = false; // ġ���� ������ �ִ� ��� true
	private boolean complete = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		initialize();
	}
	
	private void initialize(){
		// static context ����
		Util.CONTEXT = this;
		WoodmanActivity.CONTEXT = this;
		
		Util.ps = new PlaySound(this);
		Util.vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		tvStatus = (TextView)findViewById(R.id.loading_tvStatus);
		/*
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();*/
		
		initBaseDirectory();
		
		if(!criticalError){
			initDatabase();
			
			startMainActivity();
		}
	}
	
	private void startMainActivity() {
		Intent intent = new Intent(LoadingActivity.this, WoodmanActivity.class);
		finish();
		startActivity(intent);
	}
	
	private void showStatus(String msg){
		tvStatus.setText(msg);
	}
	/**
	 * �ܺ� �޸� ��� ���ο� �⺻ ���丮 ���� Ȯ��
	 */
	private void initBaseDirectory(){
		// �ܺ� �޸� ��밡�� ���� Ȯ��
		String extState = Environment.getExternalStorageState();
		if(!extState.equals(Environment.MEDIA_MOUNTED)){
			Util.longToast("�ܺ� �޸𸮸� ����� �� �����ϴ�.\n���α׷��� �����մϴ�.");
			new AlertDialog.Builder(LoadingActivity.this)
			.setTitle("�ʱ�ȭ ����")
			.setMessage("�ܺ� �޸𸮰� ���ų� ����� �� �����ϴ�.\n���α׷��� �����մϴ�.")
			.setPositiveButton("Ȯ��", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.show();
			
			criticalError = true;
		} else {
			String extPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			File baseDirectory = new File(extPath + "/woodman");
			
			// �⺻ �� ���丮�� �������� �ʴ� ��� ������
			if(!baseDirectory.exists()) baseDirectory.mkdir();
			
			HandleExternalData.BASE_DIR = baseDirectory.getAbsolutePath() + "/";
		}
	}
	private void initDatabase(){		
		// �ܺ� �޸𸮿� woodman.db ���� �ִ��� Ȯ��
		File dbFile = new File(HandleExternalData.BASE_DIR + "woodman.db");
		
		// ���ٸ� ����
		if(!dbFile.exists()){
			// raw�� �ִ� woodman.db �ʱ� ���� ��������
			int dbFileId = R.raw.woodman;
			InputStream instream = getResources().openRawResource(dbFileId);
			try {
				FileOutputStream outstream = new FileOutputStream(dbFile);
				byte[] buffer = new byte[instream.available()];
				instream.read(buffer);				
				outstream.write(buffer);
				
				outstream.flush();
				outstream.close();
				instream.close();

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// DBHelper �ν��Ͻ� ���� �� �ʱ�ȭ
		DBHelper.makeInstance(this);
	}
	
	@Override
	public void onBackPressed(){
		
	}

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == 0){
				showStatus((String)msg.obj);
			}
			else{
				Intent intent = new Intent(LoadingActivity.this, WoodmanActivity.class);
				finish();
				startActivity(intent);
			}
		}
	};
	String[] messeges = {"DB �ν��Ͻ� �ʱ�ȭ..",
	"�ܺ� �޸� ��밡�� ���� Ȯ�� ��..",
	"���ø����̼� �⺻ ���丮 Ȯ��..",
	"���ҽ� MD5 Check sum Ȯ��..",
	"�����ͺ��̽� ���� Ȯ��..",
	"�ʱ� �����ͺ��̽� ���� ���� ��..",
	"�ʱ� �����ͺ��̽� ���� ���� �Ϸ�..",
	"�����ͺ��̽� ��ȿ�� üũ..",
	"�ε� �Ϸ�, ���α׷��� �����մϴ�."};
	public void run() {
		Message msg;
		int time;
		Random r= new Random();
		for(int i = 0 ; i < messeges.length ; i++){
			msg = Message.obtain();
			msg.what = 0;
			msg.obj = messeges[i];
			mHandler.sendMessage(msg);
			
			time = r.nextInt(500);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		msg = Message.obtain();
		msg.what = 1;
		mHandler.sendMessage(msg);
		
	}
}
