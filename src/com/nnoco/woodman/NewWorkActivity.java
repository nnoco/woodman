package com.nnoco.woodman;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;
import com.nnoco.woodman.work.MajorSpecies;
import com.nnoco.woodman.work.ResultActivity;

public class NewWorkActivity extends Activity{
	private TextView tvTitle;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwork);
        
        init();
        
    }
    
    private void init(){
    	setListeners();
    	
    	// 뒤로, 완료 버튼 속성 설정
    	findViewById(R.id.newwork_btComplete).setVisibility(View.INVISIBLE);
    	
    	// 타이틀 텍스트 지정
    	tvTitle = (TextView)findViewById(R.id.newwork_tvTitle);
    	tvTitle.setText(HandleExternalData.REL_CURRENT_DIR);
    	/*tvTitle.setText(R.string.title_newwork);
    	
    	// 불러오기 작업인 경우
    	Intent intent = getIntent();
    	String jobname = intent.getStringExtra("작업 이름");
    	if(jobname != null){
    		((TextView)findViewById(R.id.newwork_tvTitle)).setText(jobname);
    	}
    	  */	
    	
    	// 기본 사항
    	
    }
    
    private void setListeners(){
    	findViewById(R.id.newwork_generals).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_others).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_species).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_survey).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_results).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_finish).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_btBack).setOnClickListener(mClickListener);
        findViewById(R.id.newwork_btRename).setOnClickListener(mClickListener);
    }
    
    private void showRenameDialog(){
    	LinearLayout view = (LinearLayout)View.inflate(this, R.layout.rename_dialog_view, null);
    	final EditText etJobName = (EditText)view.findViewById(R.id.rename_dialog_view_etWorkName);
    	etJobName.setText(HandleExternalData.REL_CURRENT_DIR);
    	
    	new AlertDialog.Builder(this)
    	.setTitle("작업 이름 변경")
    	.setIcon(R.drawable.icon)
    	.setView(view)
    	.setMessage("변경할 이름을 입력해 주세요.")
    	.setNegativeButton("취소", null)
    	.setPositiveButton("변경", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String name = etJobName.getText().toString();
				if(name.length() != 0 && !name.matches(".*[/\\\\:?|*<>\"].*")){
					File jobDir = new File(HandleExternalData.CURRENT_DIR);
					jobDir.renameTo(new File(HandleExternalData.BASE_DIR + name));
					
					HandleExternalData.CURRENT_DIR = HandleExternalData.BASE_DIR + name + "/";
					HandleExternalData.REL_CURRENT_DIR = name;
					
					tvTitle.setText(name);
					
					Util.shortToast("작업 이름이 변경되었습니다.\n" + name);
				}else{
					Util.shortToast("잘못된 파일이름을 입력하여\n정상적으로 처리되지 않았습니다.");
				}
			}
		})
		.show();
    }
    
    private Button.OnClickListener mClickListener = new Button.OnClickListener(){
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = null;
			
			switch(id){
			case R.id.newwork_generals:
				intent = new Intent(NewWorkActivity.this, GeneralsActivity.class);
				startActivity(intent);
				break;
			case R.id.newwork_others:
				intent = new Intent(NewWorkActivity.this, OthersActivity.class);
				startActivity(intent);
				break;
			case R.id.newwork_species:
				intent = new Intent(NewWorkActivity.this, MajorSpecies.class);
				startActivity(intent);
				break;
				
			case R.id.newwork_survey:
				intent = new Intent(NewWorkActivity.this, SurveyActivity.class);
				startActivity(intent);
				break;
				
			case R.id.newwork_results:
				intent = new Intent(NewWorkActivity.this, ResultActivity.class);
				startActivity(intent);
				break;
				
			case R.id.newwork_btRename:
				showRenameDialog();				
				break;
				
			case R.id.newwork_finish:
				finish();
				HandleExternalData.newJob();
				intent = new Intent(NewWorkActivity.this, NewWorkActivity.class);
				startActivity(intent);
				break;
				
			case R.id.newwork_btBack:
				finish();
				break;
			}
		}
    	
    };
}
