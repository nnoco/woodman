package com.nnoco.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JobInfo {
	public String file_others;
	public String group;
	public String standardLandNo;
	public String GPSNo;
	
	public void saveGenerals(){
		try {
			FileOutputStream output = new FileOutputStream(file_others);
			
			
			
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
