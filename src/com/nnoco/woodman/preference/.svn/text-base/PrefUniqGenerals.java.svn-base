package com.nnoco.woodman.preference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.nnoco.woodman.R;

public class PrefUniqGenerals extends Activity implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pref_uniq_generals);
		
		setListeners();
	}

	private void setListeners() {
		findViewById(R.id.pref_uniq_generals_btBack).setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.pref_uniq_generals_btBack:
			finish();
			break;

		default:
			break;
		}
	}
}
