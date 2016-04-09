package com.nnoco.woodman.work;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nnoco.data.DBHelper;
import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;
import com.nnoco.woodman.R;

public class MajorSpecies extends Activity implements OnClickListener {
	private Spinner spinType;
	private EditText etQuery;

	private ListView lvDBSpecies;
	private ListView lvSelectedSpecies;

	// 검색창에서 선택한 분류와, 입력한 텍스트
	private String selectedType = "전체";
	private String typedText = "";

	// 검색 리스트쪽
	private ArrayList<HashMap<String, String>> searchResult; // 표시될 리스트
	private SimpleAdapter dbAdapter; // 어댑터

	// 선택된 리스트 쪽
	private ArrayList<HashMap<String, String>> selectedList; // 표시될 리스트
	private SimpleAdapter selectedAdapter;

	// Query 입력시 바로 처리를 위한 리스너
	TextWatcher textWatcherInput = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			typedText = s.toString();
			search();
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void afterTextChanged(Editable s) {
		}
	};
	
	private boolean isChanged;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.major_species);

		init();
		setListeners();
	}

	@Override
	public void onResume() {
		super.onResume();

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void init() {
		// 스피너 초기화
		spinType = (Spinner) findViewById(R.id.major_species_spType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.species_types_major,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinType.setPrompt("분류 선택");
		spinType.setAdapter(adapter);

		etQuery = (EditText) findViewById(R.id.major_species_etQuery);

		initDBListView();
		initSelectedListView();
	}

	private void initDBListView() {
		searchResult = new ArrayList<HashMap<String, String>>();

		String[] from = { "name", "type" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		selectQuery(searchResult, "", "전체");
		dbAdapter = new SimpleAdapter(this, searchResult,
				android.R.layout.simple_list_item_2, from, to);
		lvDBSpecies = (ListView) findViewById(R.id.major_species_lvDBSpecies);
		lvDBSpecies.setAdapter(dbAdapter);

	}

	private void selectQuery(ArrayList<HashMap<String, String>> list,
			String name, String type) {
		list.clear();

		String where = name.length() == 0 ?
		// 이름란이 비어있을 때
		type.equals("전체") ? "" : String.format("WHERE type='%s'", type)
				// 이름란에 입력되어있을 때
				: type.equals("전체") ? String.format(
						"WHERE name LIKE '%%%s%%' OR initials LIKE '%%%s%%'",
						name, name)
						: String.format(
								"WHERE name LIKE '%%%s%%' AND type='%s' OR initials LIKE '%%%s%%' AND type='%s'",
								name, type, name, type);

		HashMap<String, String> map = null;

		String sql = String.format(
				"SELECT name, type FROM tblSpecies %s ORDER BY name", where);
		Log.d("Structured Query Language", sql);

		Cursor cursor = DBHelper.read(sql);
		cursor.moveToFirst();
		while (cursor.getCount() != 0) {
			map = new HashMap<String, String>();

			map.put("name", cursor.getString(0));
			map.put("type", cursor.getString(1));

			list.add(map);

			if (cursor.isLast())
				break;

			cursor.moveToNext();
		}
		cursor.close();

	}

	private void initSelectedListView() {
		selectedList = new ArrayList<HashMap<String, String>>();

		loadSelectedList(selectedList);

		String[] from = { "name", "type" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		selectedAdapter = new SimpleAdapter(this, selectedList,
				android.R.layout.simple_list_item_2, from, to);
		lvSelectedSpecies = (ListView) findViewById(R.id.major_species_lvSeletedSpecies);
		lvSelectedSpecies.setAdapter(selectedAdapter);
	}

	// 파일로부터 선택한 수종을 읽어옴
	private void loadSelectedList(ArrayList<HashMap<String, String>> list) {
		String strList = HandleExternalData.load("주 수종 목록.txt");
		if (strList != null) {
			String[] splitList = strList.replace("\r", "").split("\n");
			String[] splitItem = null;
			HashMap<String, String> map;

			for (int i = 0; i < splitList.length; i++) {
				if (splitList[0].length() == 0)
					break;

				splitItem = splitList[i].split("\t");

				map = new HashMap<String, String>();
				map.put("name", splitItem[0]);
				map.put("type", splitItem[1]);

				list.add(map);
			}
		}
	}

	// 파일에 선택한 수종 저장
	private void saveSelectedList(ArrayList<HashMap<String, String>> list) {
		StringBuilder builder = new StringBuilder();
		for (HashMap<String, String> item : list) {
			builder.append(item.get("name") + "\t" + item.get("type") + "\r\n");
		}

		HandleExternalData.save("주 수종 목록.txt", builder.toString());

		Util.shortToast("주 수종 목록을 저장하였습니다.");
	}

	private void setListeners() {
		findViewById(R.id.major_species_btBack).setOnClickListener(this);
		findViewById(R.id.major_species_btComplete).setOnClickListener(this);

		spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				TextView tvTemp = (TextView) view
						.findViewById(android.R.id.text1);
				selectedType = tvTemp.getText().toString();
				search();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		lvDBSpecies
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> map = searchResult
								.get(position);
						String name = map.get("name");
						// String type = map.get("type");

						int insertIndex = selectedList.size();
						// 중복 검사, 삽입 인덱스
						HashMap<String, String> temp = null;
						int compare;
						for (int i = 0; i < selectedList.size(); i++) {
							temp = selectedList.get(i);

							compare = temp.get("name").compareTo(name);
							if (compare == 0) {
								Util.shortToast("이미 선택한 항목입니다.");
								return;
							}
							if (compare > 0) {
								insertIndex = i;
								break;
							}
						}

						isChanged = true;
						selectedList.add(insertIndex, map);
						selectedAdapter.notifyDataSetChanged();
					}
				});

		lvSelectedSpecies
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> item = selectedList
								.get(position);
						selectedList.remove(position);
						selectedAdapter.notifyDataSetChanged();

						isChanged = true;
						Util.shortToast(item.get("name") + "의 선택이 취소되었습니다.");
					}
				});

		etQuery.addTextChangedListener(textWatcherInput);
	}

	private void search() {
		selectQuery(searchResult, typedText, selectedType);
		dbAdapter.notifyDataSetChanged();
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.major_species_btBack:
			finish();
			break;

		case R.id.major_species_btComplete:
			saveSelectedList(selectedList);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();

		if (isChanged) {
			new AlertDialog.Builder(this)
					.setIcon(R.drawable.icon)
					.setTitle("저장 여부 확인")
					.setMessage("변경 내용을 저장하시겠습니까?")
					.setPositiveButton("저장",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									saveSelectedList(selectedList);
									finish();
								}
							})
					.setNegativeButton("닫기",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setNeutralButton("취소", null)
					.setCancelable(false).show();
		}
		else{
			finish();
		}
	}
}
