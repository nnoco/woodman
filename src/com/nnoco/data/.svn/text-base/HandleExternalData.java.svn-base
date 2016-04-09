package com.nnoco.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.Toast;

import com.nnoco.woodman.WoodmanActivity;

public class HandleExternalData {
	public static String BASE_DIR;
	public static String CURRENT_DIR;
	public static String REL_CURRENT_DIR;
	
	public static final String GENERALS = "/기본 사항.txt";
	
	private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static Date TODAY = new Date();
	public static void createDir(){
		String prefix = FORMAT.format(TODAY);
		
		String[] joblist = new File(BASE_DIR).list();
		
		int count = 1;
		for(String temp : joblist){
			if(temp.startsWith(prefix) && !temp.endsWith(".zip")) count++;
		}
		
		File jobdir = new File(BASE_DIR +  prefix + "_" + count);
		jobdir.mkdir();
		
		CURRENT_DIR = jobdir.getAbsolutePath() + "/";
		REL_CURRENT_DIR = jobdir.getName();
		
		// 매목 조사 폴더 만들기
		new File(CURRENT_DIR + "매목 조사").mkdir();
		Toast.makeText(WoodmanActivity.CONTEXT, "새 작업 폴더 생성\n" + CURRENT_DIR, Toast.LENGTH_SHORT).show();
	}
	
	public static void newJob(){
		createDir();
	}
	
	public static void loadJob(String path){
		REL_CURRENT_DIR = path;
		CURRENT_DIR = BASE_DIR + "/" + path + "/";
	}
	
	public static void save(String filename, String contents){
		try{
			FileOutputStream output = new FileOutputStream(CURRENT_DIR + filename);
			
			output.write(contents.getBytes());
			
			output.flush();
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String load(String filename){
		String contents = "";
		
		File file = new File(CURRENT_DIR + filename);
		if(!file.exists()) return contents;
		
		try{
			FileInputStream input = new FileInputStream(CURRENT_DIR + filename);
			byte[] buf = new byte[input.available()];
			
			input.read(buf);
			
			contents = new String(buf);
			
			input.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return contents;
	}
}
