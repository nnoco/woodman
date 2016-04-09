package com.nnoco.woodman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;

public class UniquenessActivity extends Activity implements OnClickListener{
	private EditText etInput;
	private boolean isChanged = false;
	
	private TextWatcher textWatcher = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			isChanged = true;
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		public void afterTextChanged(Editable s) {}
	};

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uniqueness);
        
        init();
        setListeners();
    }
    
    private void init() {
		etInput = (EditText)findViewById(R.id.uniqeness_etInput);
		etInput.addTextChangedListener(textWatcher);
		load(); // ���� �Է¶��� ������ �ε�
		isChanged = false; // �ε��� �Ϸ��� �Ŀ� ��ȭ ���¸� ������
	}
    
    private void setListeners() {
    	findViewById(R.id.uniqueness_btPremium).setOnClickListener(this);
        findViewById(R.id.uniqueness_btDesignElement).setOnClickListener(this);
        findViewById(R.id.uniqueness_btGenerals).setOnClickListener(this);
        findViewById(R.id.uniqueness_btSave).setOnClickListener(this);
        findViewById(R.id.uniqueness_btBack).setOnClickListener(this);
	}
    
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		
		switch(id){
		case R.id.uniqueness_btPremium:
			intent = new Intent(UniquenessActivity.this, UniquenessSubActivity.class);
			intent.putExtra("fileName", "Ư�̻���_���� ���.txt");
			intent.putExtra("DBTableName", "tblPremium");
			intent.putExtra("activityTitle", "���� ���");
			startActivity(intent);
			break;
		case R.id.uniqueness_btDesignElement:
			intent = new Intent(UniquenessActivity.this, UniquenessSubActivity.class);
			intent.putExtra("fileName", "Ư�̻���_���� ���.txt");
			intent.putExtra("DBTableName", "tblDesignElement");
			intent.putExtra("activityTitle", "���� ���");
			startActivity(intent);
			break;
		case R.id.uniqueness_btGenerals:
			intent = new Intent(UniquenessActivity.this, UniquenessSubActivity.class);
			intent.putExtra("fileName", "Ư�̻���_�Ϲ� ����.txt");
			intent.putExtra("DBTableName", "tblUniqGenerals");
			intent.putExtra("activityTitle", "�Ϲ� ����");
			startActivity(intent);
			break;
		case R.id.uniqueness_btSave:
			save();
			break;
			
		case R.id.uniqueness_btBack:
			close();
			break;
		}
		
	}
	
	@Override public void onBackPressed(){
		close();
	}
	
	private void close(){
		if(isChanged){
			new AlertDialog.Builder(this)
			.setTitle("���� Ȯ��")
			.setMessage("����� ������ �ֽ��ϴ�. �����Ͻðڽ��ϱ�?")
			.setIcon(R.drawable.icon)
			.setPositiveButton("���� �� �ݱ�", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					save();
					finish();
				}
			})
			.setNegativeButton("�ٷ� �ݱ�", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.show();
		} else {
			finish();
		}
		
	}

	private void save() {
		HandleExternalData.save("Ư�̻���_�����Է�.txt", etInput.getText().toString());
		Util.shortToast("���� �Է¶��� ������ ����Ǿ����ϴ�.");
		
		isChanged = false;
	}
	
	private void load(){
		etInput.setText(HandleExternalData.load("Ư�̻���_�����Է�.txt"));
	}
}