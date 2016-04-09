package com.nnoco.woodman;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nnoco.data.DBHelper;
import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;
import com.nnoco.woodman.work.UniquenessAdapter;
import com.nnoco.woodman.work.UniquenessItem;

public class UniquenessSubActivity extends Activity implements View.OnClickListener, TextWatcher{
	//============================================================//
	// Member Variables
	//============================================================//
	// 뷰와 연결된 멤버
	private TextView tvTitle;
	private ListView lvItems;
	private EditText etItems;
	private Button btNewItem;
	private Button btBack;
	private Button btComplete;
	
	// UniquenessActivity로부터 받은 Extra 저장
	private String activityTitle;
	private String fileName;
	private String DBTableName;
	
	private boolean isChanged; // EditText의 내용이 변하는 경우 true
	
	private boolean loadComplete;// 액티비티 로딩 상태
	
	
	// 항목을 관리하는 리스트
	private ArrayList<UniquenessItem> DBItems;
	
	// 리스트뷰 어댑터
	private UniquenessAdapter listViewAdapter;

	//============================================================//
	// Override Methods
	//============================================================//
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uniqueness_sub_layout);
        
        init();
        setListeners();
        
        loadComplete = true;
    }
    
	//============================================================//
	// Member Methods
	//============================================================//
    /** 멤버와 뷰 연결*/
    private void init() {
    	btBack = (Button)findViewById(R.id.uniqueness_sub_btBack);
    	btComplete = (Button)findViewById(R.id.uniqueness_sub_btComplete);
    	
    	tvTitle = (TextView)findViewById(R.id.uniqueness_sub_tvTitle);
    	lvItems = (ListView)findViewById(R.id.uniqueness_sub_lvDBItems);
    	etItems = (EditText)findViewById(R.id.uniqueness_sub_etMyItems);
    	btNewItem = (Button)findViewById(R.id.uniqueness_sub_btNewItem);
    	
		// 타이틀 이름 변경, 파일이름 받기
    	Intent intent = getIntent();
    	activityTitle = intent.getStringExtra("activityTitle");
    	tvTitle.setText(activityTitle);
    	fileName = intent.getStringExtra("fileName");
    	DBTableName = intent.getStringExtra("DBTableName");
    	
    	// 인스턴스 할당
    	DBItems = new ArrayList<UniquenessItem>();
    	
    	// 항목 로드
    	loadDBItems();
    	loadMyItems();
    	
    	// 리스트 뷰 어댑터 연결
    	initListView();
	}
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    
    private void initListView() {
    	listViewAdapter = new UniquenessAdapter(this, DBItems, R.layout.uniqueness_item_view);
    	lvItems.setAdapter(listViewAdapter);
	}

	/** 뷰에 리스너 연결 */
    private void setListeners() {
    	btBack.setOnClickListener(this);
    	btComplete.setOnClickListener(this);
    	btNewItem.setOnClickListener(this);
    	
    	etItems.addTextChangedListener(this);
    	
    	lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				UniquenessItem item = DBItems.get(position);
				String itemText = item.getText();
				
				String mText = etItems.getText().toString();
				if(mText.contains(itemText + "\n")){
					mText = mText.replace(itemText+"\n", "");
					etItems.setText(mText);
				}
				else{
					if(mText.endsWith("\n")){
						etItems.append(itemText + "\n");
					}
					else if(mText.length() == 0){
						etItems.append(itemText + "\n");
					}
					else{
						etItems.append("\n" + itemText + "\n");
					}
					
				}
			}
    		
    	});
    	
    	etItems.setOnClickListener(this);
	}
    
    private void closeActivity(){
    	if(isChanged){
    		new AlertDialog.Builder(this)
    		.setTitle("저장 확인")
    		.setMessage("변경된 내용이 있습니다. 저장하시겠습니까?")
    		.setIcon(R.drawable.icon)
    		.setPositiveButton("저장", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					saveMyItems();
					finish();
				}
    		})
    		.setNegativeButton("저장하지 않음", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setNeutralButton("취소", null)
			.show();
    	} else{
    		finish();
    	}
    }

	private void saveMyItems() {
		String myData = etItems.getText().toString().replace("\n", "\r\n");
		HandleExternalData.save(fileName, myData);
	}
	
	private void loadDBItems(){
		String sql = String.format("SELECT * FROM %s ORDER BY item", DBTableName);
		Cursor cursor = DBHelper.read(sql);
		cursor.moveToFirst();
		while(cursor.getCount() != 0){
			DBItems.add(new UniquenessItem(cursor.getString(0)));
			
			if(cursor.isLast()) break;
			cursor.moveToNext();
		}
		
		cursor.close();
	}
	
	private void loadMyItems(){
		String data = HandleExternalData.load(fileName).replace("\r\n", "\n");
		
		etItems.setText(data);
	}

	/** 버튼 클릭 이벤트 리스너 */
	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.uniqueness_sub_btBack:
			closeActivity();
			break;
			
		case R.id.uniqueness_sub_btComplete:
			saveMyItems();
			finish();
			break;
			
		case R.id.uniqueness_sub_btNewItem:
			showAddItemDialog();
			break;
			
		case R.id.uniqueness_sub_etMyItems:
			etItems.setFocusable(true);
			break;

		default:
			break;
		}
		
	}
	
	private void insertToItems(UniquenessItem item){
		// ListView 에 추가
		int diff = 0;
		String temp;
		int index;
		
		for(index = 0 ; index < DBItems.size(); index++){
			temp = DBItems.get(index).getText();
			
			diff = temp.compareTo(item.getText());
			
			if(diff == 0){
				Util.shortToast("이미 존재하는 항목입니다.");
				return;
			}
			else if(diff > 0){
				break;
			}
		}
		
		DBItems.add(index, item);
		
		// DB에 추가
		String sql = String.format("INSERT INTO %s VALUES('%s')", DBTableName, item.getText());
		DBHelper.write(sql);
		
		Util.shortToast("항목이 추가되었습니다.");
		
		listViewAdapter.notifyDataSetChanged();
	}
	
	private void showAddItemDialog() {
		final LinearLayout linear = (LinearLayout)View.inflate(this, R.layout.new_item_dialog, null);
		new AlertDialog.Builder(this)
		.setView(linear)
		.setTitle("새 항목 추가")
		.setIcon(R.drawable.icon)
		.setPositiveButton("추가", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				addItem();
			}

			private void addItem() {
				EditText etInput = (EditText)linear.findViewById(R.id.new_item_dialog_etInput);
				String text = etInput.getText().toString();
				if(text.length() != 0){
					UniquenessItem item = new UniquenessItem(text);
					insertToItems(item);
				}
				
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
			}
		})
		.setNegativeButton("취소", null)
		.show();		
	}

	@Override
	public void onBackPressed(){
		closeActivity();
	}

	//========== Implements TextWatcher Interface Start ==========//
	public void afterTextChanged(Editable s) {
		if(loadComplete) isChanged = true;
	}

	// unused event
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
	public void onTextChanged(CharSequence s, int start, int before, int count) {}
	//========== Implements TextWatcher Interface End ==========//
}
