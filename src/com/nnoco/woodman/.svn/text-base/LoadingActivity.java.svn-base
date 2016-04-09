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
	private boolean criticalError = false; // 치명적 오류가 있는 경우 true
	private boolean complete = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		initialize();
	}
	
	private void initialize(){
		// static context 연결
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
	 * 외부 메모리 사용 여부와 기본 디렉토리 유무 확인
	 */
	private void initBaseDirectory(){
		// 외부 메모리 사용가능 여부 확인
		String extState = Environment.getExternalStorageState();
		if(!extState.equals(Environment.MEDIA_MOUNTED)){
			Util.longToast("외부 메모리를 사용할 수 없습니다.\n프로그램을 종료합니다.");
			new AlertDialog.Builder(LoadingActivity.this)
			.setTitle("초기화 오류")
			.setMessage("외부 메모리가 없거나 사용할 수 없습니다.\n프로그램을 종료합니다.")
			.setPositiveButton("확인", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.show();
			
			criticalError = true;
		} else {
			String extPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			File baseDirectory = new File(extPath + "/woodman");
			
			// 기본 앱 디렉토리가 존재하지 않는 경우 생성함
			if(!baseDirectory.exists()) baseDirectory.mkdir();
			
			HandleExternalData.BASE_DIR = baseDirectory.getAbsolutePath() + "/";
		}
	}
	private void initDatabase(){		
		// 외부 메모리에 woodman.db 파일 있는지 확인
		File dbFile = new File(HandleExternalData.BASE_DIR + "woodman.db");
		
		// 없다면 생성
		if(!dbFile.exists()){
			// raw에 있는 woodman.db 초기 파일 가져오기
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
		
		// DBHelper 인스턴스 생성 및 초기화
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
	String[] messeges = {"DB 인스턴스 초기화..",
	"외부 메모리 사용가능 여부 확인 중..",
	"애플리케이션 기본 디렉토리 확인..",
	"리소스 MD5 Check sum 확인..",
	"데이터베이스 파일 확인..",
	"초기 데이터베이스 파일 생성 중..",
	"초기 데이터베이스 파일 생성 완료..",
	"데이터베이스 유효성 체크..",
	"로딩 완료, 프로그램을 시작합니다."};
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
