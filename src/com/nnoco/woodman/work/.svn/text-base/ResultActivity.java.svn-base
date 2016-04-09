package com.nnoco.woodman.work;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.nnoco.woodman.R;

public class ResultActivity extends Activity implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		init();
		setListeners();
	}
	
	private void init() {
	}
		
	
	private void setListeners() {
		findViewById(R.id.result_btBack).setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.result_btBack:
			finish();
			break;

		default:
			break;
		}
	}
}
