package com.nnoco.woodman.preference;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nnoco.data.DBHelper;
import com.nnoco.data.Util;
import com.nnoco.text.Hangul;
import com.nnoco.woodman.R;

public class PrefSpecies extends Activity implements OnClickListener, OnItemClickListener{
	private String[] types; // 분류 목록
	
	private ListView lvSpecies;
	private ArrayList<PrefSpeciesItem> items;
	private PrefSpeciesAdapter adapter;
	private Dialog diagAdd;
	private LinearLayout diagView;
	private Spinner spinType;
	
	//=== 편집 대화 상자 ===//
	private Dialog diagEdit; // 대화창
	private String originalName; // 편집 전 이름
	private String originalType; // 편집 전 분류
	private PrefSpeciesItem selectedItem; // 선택된 항목의 리스트 아이템
	private int selectedIndex; // 선택된 항목의 인덱스
	private View selectedView; // 선택된 뷰
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pref_species);
		
		init();
		setListeners();
		
	}
	
	// DB로부터 수종 목록을 읽어옴
	private void readSpecies(){
		Cursor cursor = DBHelper.read("SELECT * FROM tblSpecies ORDER BY name");
		cursor.moveToFirst();
		
		while(cursor.getCount() != 0){
			items.add(new PrefSpeciesItem(cursor.getString(0), cursor.getString(1)));
			
			if(cursor.isLast()) break;
			cursor.moveToNext();
		}
		
		cursor.close();
	}
	
	private void init(){
		lvSpecies = (ListView)findViewById(R.id.pref_species_lvSpecies);
		items = new ArrayList<PrefSpeciesItem>();
		
		readSpecies();
		
		adapter = new PrefSpeciesAdapter(this, items, R.layout.species_item);
		
		lvSpecies.setAdapter(adapter);
		lvSpecies.setDivider(new ColorDrawable(0xffdddddd));
		lvSpecies.setDividerHeight(1);
		
		types = getResources().getStringArray(R.array.species_types);
		
	}
	
	private void setListeners(){
		findViewById(R.id.pref_species_btBack).setOnClickListener(this);
		findViewById(R.id.pref_species_btAdd).setOnClickListener(this);
		
		lvSpecies.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.pref_species_btBack:
			finish();
			break;
		case R.id.pref_species_btAdd:
			showAddDialog();
			break;
		case R.id.add_species_btOK:
			addSpecies();
			break;
		case R.id.add_species_btCancle:
			diagAdd.dismiss();
			break;
		case R.id.edit_species_btOK:
			update();
			break;
			
		case R.id.edit_species_btCancle:
			diagEdit.dismiss();
			break;
			
		case R.id.edit_species_btRemove:
			showRemoveDialog();
			break;
			
		default:
			break;
		}
		
	}
	
	// 편집 모드 - 편집
	private void update(){
		// 변동이 없는 경우 창만 닫음
		String currentType = (String)spinType.getSelectedItem();
		EditText etName = (EditText)diagView.findViewById(R.id.edit_species_etName);
		String currentName = etName.getText().toString();
		
		if(!currentName.equals(originalName) || !currentType.equals(originalType)){
			// DB 업데이트
			String sql = String.format("UPDATE tblSpecies SET name='%s', type='%s', initials='%s' WHERE name='%s' AND type='%s'",
					currentName, currentType, Hangul.getOnsets(currentName),
					originalName, originalType);
			DBHelper.write(sql);
		
			// 리스트 뷰 업데이트
			selectedItem.setName(currentName);
			selectedItem.setType(currentType);
			items.remove(selectedIndex);
			insertionSort(selectedItem);
			adapter.notifyDataSetChanged();
			
			// 색상 변경
			selectedItem.setChanged(true);
			
			Util.shortToast("수정 되었습니다.");
		}
		
		
		diagEdit.dismiss();
	}
	
	// 편집 모드에서 삭제 대화상자
	private void showRemoveDialog(){
		new AlertDialog.Builder(this)
		.setTitle("삭제 확인")
		.setMessage(String.format("정말 %s(%s) 항목을 삭제하시겠습니까?", originalName, originalType))
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				remove();
			}
		})
		.setNegativeButton("취소", null)
		.setIcon(R.drawable.icon)
		.show();
	}
	
	// 편집 모드에서 삭제 시 처리
	private void remove(){
		// 리스트 뷰에서 삭제
		items.remove(selectedIndex);
		adapter.notifyDataSetChanged();
		
		// DB에서 삭제
		String sql = String.format("DELETE FROM tblSpecies WHERE name='%s' AND type='%s'",
				originalName, originalType);
		
		DBHelper.write(sql);
		
		Util.shortToast(String.format("수종 삭제 - %s(%s)", originalName, originalType));
		diagEdit.dismiss();
	}
	
	// items에서 정렬된 상태로 넣기 위한 함수
	private int insertionSort(PrefSpeciesItem item){
		String tempName = null;
		for(int i = 0 ; i < items.size() ; i++){
			tempName = items.get(i).getName();
			if(tempName.compareTo(item.getName()) >= 0){
				items.add(i, item);				
				return i;
			}
		}
		
		items.add(items.size(), item);
		return items.size()-1;
	}
	private void addSpecies(){
		TextView tvName = (TextView)diagView.findViewById(R.id.add_species_etName);
		
		String type = spinType.getSelectedItem().toString();
		String name = tvName.getText().toString();
		String onsets = null;
		String sql;
		
		PrefSpeciesItem item = null;
		
		if(name.length() == 0){
			Util.shortToast("이름을 입력해 주십시오.");
		} else {
			// 중복 체크 아직 안함
			onsets = Hangul.getOnsets(name);
			sql = String.format("INSERT INTO tblSpecies" +
					" (name, type, initials) VALUES('%s', '%s', '%s')", name,type, onsets);
			DBHelper.write(sql);
			
			item = new PrefSpeciesItem(name, type, true);
			insertionSort(item);
			
			adapter.notifyDataSetChanged();
			
			Util.shortToast(String.format("수종 추가 - %s(%s)", name,type));
			diagAdd.dismiss();
		}
	}
	
	// 수종 추가 다이얼로그 띄우기
	private void showAddDialog(){
		diagView = (LinearLayout)View.inflate(this, R.layout.add_species, null);
		
		spinType = (Spinner)diagView.findViewById(R.id.add_species_spinType);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this, R.array.species_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinType.setPrompt("분류를 선택하세요.");
		spinType.setAdapter(adapter);
		
		diagView.findViewById(R.id.add_species_btOK).setOnClickListener(this);
		diagView.findViewById(R.id.add_species_btCancle).setOnClickListener(this);
		
		diagAdd = new Dialog(this);
		diagAdd.setContentView(diagView);
		diagAdd.setTitle("새 수종을 추가합니다.");
		diagAdd.show();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PrefSpeciesItem item = items.get(position);
		
		diagView = (LinearLayout)View.inflate(this, R.layout.edit_species, null);
		
		spinType = (Spinner)diagView.findViewById(R.id.edit_species_spinType);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this, R.array.species_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinType.setPrompt("분류를 선택하세요.");
		spinType.setAdapter(adapter);
		
		// 편집의 경우를 위해 원본 데이터를 저장
		originalName = item.getName();
		originalType = item.getType();
		
		// 분류가 선택되어있도록 함
		for(int i = 0;i < types.length ; i++){
			if(types[i].equals(originalType)){
				spinType.setSelection(i);
				break;
			}
		}
		
		// 원래 정보대로 이름을 EditText에 채움
		EditText etName = (EditText)diagView.findViewById(R.id.edit_species_etName);
		etName.setText(originalName);
		
		// 선택된 뷰, 인덱스, 아이템 저장
		selectedIndex = position;
		selectedItem = item;
		selectedView = view;
		
		// 버튼 이벤트 연결
		diagView.findViewById(R.id.edit_species_btOK).setOnClickListener(this);
		diagView.findViewById(R.id.edit_species_btCancle).setOnClickListener(this);
		diagView.findViewById(R.id.edit_species_btRemove).setOnClickListener(this);
		
		diagEdit = new Dialog(this);
		diagEdit.setContentView(diagView);
		diagEdit.setTitle("수종의 정보를 수정합니다");
		diagEdit.show();
	}
}
