package com.nnoco.data;

import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	public static DBHelper helper;
	public static SQLiteDatabase READABLE_DB;
	public static SQLiteDatabase WRITABLE_DB;
	
	private DBHelper(Context context){
		super(context, HandleExternalData.BASE_DIR + "woodman.db", null, 1);
		
		helper = this;
		READABLE_DB = getReadableDatabase();
//		READABLE_DB.setLocale(Locale.KOREA);
		WRITABLE_DB = getWritableDatabase();
	}
	
	// singleton
	public static DBHelper makeInstance(Context context){
		if(helper == null) helper = new DBHelper(context);
		
		return helper;
	}
	
	public static Cursor read(String query){
		if(!READABLE_DB.isOpen()) READABLE_DB = helper.getReadableDatabase();
		return READABLE_DB.rawQuery(query, null);
	}
	
	public static void write(String query){
		if(!WRITABLE_DB.isOpen()) WRITABLE_DB = helper.getWritableDatabase();
		WRITABLE_DB.execSQL(query);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 파일이 없는 경우 만듬
//		String db_scheme = Util.CONTEXT.getResources().getString(R.string.db_scheme);
//		db.execSQL(db_scheme);
		// nothing to do
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to do
	}
}
/*
CREATE TABLE tblSpecies (
  type      varchar(50) NOT NULL DEFAULT "",
  name      varchar(50) PRIMARY KEY NOT NULL DEFAULT "",
  initials  varchar(50) DEFAULT ""
);
*/