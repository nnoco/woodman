package com.nnoco.woodman;

import com.nnoco.woodman.preference.PrefDesign;
import com.nnoco.woodman.preference.PrefPremium;
import com.nnoco.woodman.preference.PrefSpecies;
import com.nnoco.woodman.preference.PrefSubstratum;
import com.nnoco.woodman.preference.PrefUniqGenerals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PreferenceActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        
        setListeners();
    }

	private void setListeners() {
		findViewById(R.id.pref_btBack).setOnClickListener(this);
		findViewById(R.id.pref_btSpecies).setOnClickListener(this);
		findViewById(R.id.pref_btPremium).setOnClickListener(this);
		findViewById(R.id.pref_btDesignElement).setOnClickListener(this);
		findViewById(R.id.pref_btSubstratum).setOnClickListener(this);
		findViewById(R.id.pref_btUniqGenerals).setOnClickListener(this);
		
	}

	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		
		switch (id) {
		case R.id.pref_btBack:
			finish();
			break;
		case R.id.pref_btSpecies:
			intent = new Intent(PreferenceActivity.this, PrefSpecies.class);
			startActivity(intent);
			break;
			
		case R.id.pref_btSubstratum:
			intent = new Intent(PreferenceActivity.this, PrefSubstratum.class);
			startActivity(intent);
			break;
		case R.id.pref_btPremium:
			intent = new Intent(PreferenceActivity.this, PrefPremium.class);
			startActivity(intent);
			break;
		case R.id.pref_btDesignElement:
			intent = new Intent(PreferenceActivity.this, PrefDesign.class);
			startActivity(intent);
			break;
		case R.id.pref_btUniqGenerals:
			intent = new Intent(PreferenceActivity.this, PrefUniqGenerals.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}