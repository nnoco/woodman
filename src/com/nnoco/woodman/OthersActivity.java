package com.nnoco.woodman;

import com.nnoco.data.HandleExternalData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OthersActivity extends Activity{
	LinearLayout linear_slope;
	LinearLayout linear_geo;
	private final String slopeValue = "완중급";
	private int slopeCheck = 0;
	private String slopeNumber = "";
	
	RadioButton[] radios;
	EditText etNumber;
	RadioGroup rg;
	Button[] geoButtons;
	
	AlertDialog geoPopup;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others);
        
        init();
        setListeners();
    }
    
    private void init(){
    	getNewSlopeLayout();
    	
    	String slopeContents = HandleExternalData.load("경사도.txt");
    	if(slopeContents.length() != 0){
    		String[] temp = slopeContents.split("\t");
    		slopeCheck = slopeValue.indexOf(temp[0]);
    		slopeNumber = temp[1].equals(" ") ? "" : temp[1];
    	}    	
    }
    
    private void setListeners(){
    	findViewById(R.id.others_btBack).setOnClickListener(mClickListener);
    	findViewById(R.id.others_btSlope).setOnClickListener(mClickListener);
        findViewById(R.id.others_btGeography).setOnClickListener(mClickListener);
        findViewById(R.id.others_btUniqueness).setOnClickListener(mClickListener);
        findViewById(R.id.others_btSubstratum).setOnClickListener(mClickListener);
        
    }
    
    private LinearLayout getNewGeoLayout(){
    	linear_geo =(LinearLayout)View.inflate(this, R.layout.geography, null);
    	Button[] bts = {(Button)linear_geo.findViewById(R.id.geography_bt1), 
    			(Button)linear_geo.findViewById(R.id.geography_bt2),
    			(Button)linear_geo.findViewById(R.id.geography_bt3),
    			(Button)linear_geo.findViewById(R.id.geography_bt4),
    			(Button)linear_geo.findViewById(R.id.geography_bt5),
    			(Button)linear_geo.findViewById(R.id.geography_bt6),
    			(Button)linear_geo.findViewById(R.id.geography_bt7),
    			(Button)linear_geo.findViewById(R.id.geography_bt8),
    			(Button)linear_geo.findViewById(R.id.geography_bt9),
    			(Button)linear_geo.findViewById(R.id.geography_bt10)};
    	
    	geoButtons = bts;
    	
    	for(Button temp : bts){
    		temp.setOnClickListener(geoClickListener);
    	}
    	return linear_geo;
    }
    
    private Button.OnClickListener geoClickListener = new Button.OnClickListener(){
		public void onClick(View v) {
			int geoIndex = 0;
			for(int i = 0 ; i < geoButtons.length ; i++){
				if(geoButtons[i].getId() == v.getId()){
					geoIndex = i;
					break;
				}
			}
			
			geoIndex += 1; // 실제 급지는 인덱스 + 1
			
			HandleExternalData.save("급지.txt", geoIndex + "");
			
			Toast.makeText(OthersActivity.this, geoIndex + " 급지가 선택되었습니다.",
					Toast.LENGTH_LONG).show();
			
			geoPopup.cancel();
		}
    	
    };
    private LinearLayout getNewSlopeLayout(){
    	linear_slope = (LinearLayout)View.inflate(OthersActivity.this, R.layout.slope, null);
    	
    	radios = new RadioButton[3];
    			radios[0] = (RadioButton)linear_slope.findViewById(R.id.slope_rb1);
				radios[1] = (RadioButton)linear_slope.findViewById(R.id.slope_rb2);
				radios[2] = (RadioButton)linear_slope.findViewById(R.id.slope_rb3);
				
		etNumber = (EditText)linear_slope.findViewById(R.id.slope_etNumber);
		rg = (RadioGroup)linear_slope.findViewById(R.id.slope_rgRadios);
		
		radios[slopeCheck].setChecked(true);
    	etNumber.setText(slopeNumber);
		
		return linear_slope;
    }
    
    private DialogInterface.OnClickListener getSlopePositiveButton(){
    	return new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int which) {
    			RadioButton[] radios = OthersActivity.this.radios;
    			EditText etNumber = OthersActivity.this.etNumber;
    			RadioGroup rg = OthersActivity.this.rg;
    			
    			int checkedId = rg.getCheckedRadioButtonId();
    			slopeCheck = 0;
    			for(int i = 0 ; i < radios.length ; i++){
    				if(radios[i].getId() == checkedId){
    					slopeCheck = i;
    					break;
    				}
    			}
    			
    			slopeNumber = etNumber.getText().toString();
    			if(slopeNumber.length() == 0) slopeNumber = " ";
    			
    			StringBuilder builder = new StringBuilder();
    			builder.append(slopeValue.charAt(slopeCheck) + "\t");
    			builder.append(slopeNumber);
    			
    			HandleExternalData.save("경사도.txt", builder.toString());
    		}
    	};
    }
    private DialogInterface.OnClickListener getNegativeButton(){
    	return new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int which) {
    			
    		}
        };
    }
    private Button.OnClickListener mClickListener = new Button.OnClickListener(){
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = null;
			LinearLayout linear_Slope = getNewSlopeLayout();
			LinearLayout linear_Geography = getNewGeoLayout();
			
			switch(id){
			case R.id.others_btSlope:
				new AlertDialog.Builder(OthersActivity.this)
				.setTitle("경사도를 설정합니다.")
				.setIcon(R.drawable.ic_launcher)
				.setView(linear_Slope)
				.setNegativeButton("취소", getNegativeButton())
				.setPositiveButton("확인", getSlopePositiveButton())
				.show();
				break;
				
			case R.id.others_btGeography:
				geoPopup = new AlertDialog.Builder(OthersActivity.this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("급지를 선택해 주세요.")
				.setNegativeButton("닫기", getNegativeButton())
				.setView(linear_Geography)
				.show();
				break;
				
			case R.id.others_btUniqueness:
				intent = new Intent(OthersActivity.this, UniquenessActivity.class);
				startActivity(intent);
				break;
				
			case R.id.others_btSubstratum:
				intent = new Intent(OthersActivity.this, SubstratumActivity.class);
				startActivity(intent);
				break;
				
			case R.id.others_btBack:
				finish();
				break;
			}
		}
    };
}