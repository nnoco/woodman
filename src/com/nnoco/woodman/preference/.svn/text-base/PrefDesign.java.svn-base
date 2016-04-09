package com.nnoco.woodman.preference;

import com.nnoco.woodman.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PrefDesign extends Activity  implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pref_design);
		setListeners();
	}

	private void setListeners() {
		findViewById(R.id.pref_design_btBack).setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.pref_design_btBack:
			finish();
			break;

		default:
			break;
		}
	}
}
