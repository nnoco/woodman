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
	private String[] types; // �з� ���
	
	private ListView lvSpecies;
	private ArrayList<PrefSpeciesItem> items;
	private PrefSpeciesAdapter adapter;
	private Dialog diagAdd;
	private LinearLayout diagView;
	private Spinner spinType;
	
	//=== ���� ��ȭ ���� ===//
	private Dialog diagEdit; // ��ȭâ
	private String originalName; // ���� �� �̸�
	private String originalType; // ���� �� �з�
	private PrefSpeciesItem selectedItem; // ���õ� �׸��� ����Ʈ ������
	private int selectedIndex; // ���õ� �׸��� �ε���
	private View selectedView; // ���õ� ��
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pref_species);
		
		init();
		setListeners();
		
	}
	
	// DB�κ��� ���� ����� �о��
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
	
	// ���� ��� - ����
	private void update(){
		// ������ ���� ��� â�� ����
		String currentType = (String)spinType.getSelectedItem();
		EditText etName = (EditText)diagView.findViewById(R.id.edit_species_etName);
		String currentName = etName.getText().toString();
		
		if(!currentName.equals(originalName) || !currentType.equals(originalType)){
			// DB ������Ʈ
			String sql = String.format("UPDATE tblSpecies SET name='%s', type='%s', initials='%s' WHERE name='%s' AND type='%s'",
					currentName, currentType, Hangul.getOnsets(currentName),
					originalName, originalType);
			DBHelper.write(sql);
		
			// ����Ʈ �� ������Ʈ
			selectedItem.setName(currentName);
			selectedItem.setType(currentType);
			items.remove(selectedIndex);
			insertionSort(selectedItem);
			adapter.notifyDataSetChanged();
			
			// ���� ����
			selectedItem.setChanged(true);
			
			Util.shortToast("���� �Ǿ����ϴ�.");
		}
		
		
		diagEdit.dismiss();
	}
	
	// ���� ��忡�� ���� ��ȭ����
	private void showRemoveDialog(){
		new AlertDialog.Builder(this)
		.setTitle("���� Ȯ��")
		.setMessage(String.format("���� %s(%s) �׸��� �����Ͻðڽ��ϱ�?", originalName, originalType))
		.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				remove();
			}
		})
		.setNegativeButton("���", null)
		.setIcon(R.drawable.icon)
		.show();
	}
	
	// ���� ��忡�� ���� �� ó��
	private void remove(){
		// ����Ʈ �信�� ����
		items.remove(selectedIndex);
		adapter.notifyDataSetChanged();
		
		// DB���� ����
		String sql = String.format("DELETE FROM tblSpecies WHERE name='%s' AND type='%s'",
				originalName, originalType);
		
		DBHelper.write(sql);
		
		Util.shortToast(String.format("���� ���� - %s(%s)", originalName, originalType));
		diagEdit.dismiss();
	}
	
	// items���� ���ĵ� ���·� �ֱ� ���� �Լ�
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
			Util.shortToast("�̸��� �Է��� �ֽʽÿ�.");
		} else {
			// �ߺ� üũ ���� ����
			onsets = Hangul.getOnsets(name);
			sql = String.format("INSERT INTO tblSpecies" +
					" (name, type, initials) VALUES('%s', '%s', '%s')", name,type, onsets);
			DBHelper.write(sql);
			
			item = new PrefSpeciesItem(name, type, true);
			insertionSort(item);
			
			adapter.notifyDataSetChanged();
			
			Util.shortToast(String.format("���� �߰� - %s(%s)", name,type));
			diagAdd.dismiss();
		}
	}
	
	// ���� �߰� ���̾�α� ����
	private void showAddDialog(){
		diagView = (LinearLayout)View.inflate(this, R.layout.add_species, null);
		
		spinType = (Spinner)diagView.findViewById(R.id.add_species_spinType);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this, R.array.species_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinType.setPrompt("�з��� �����ϼ���.");
		spinType.setAdapter(adapter);
		
		diagView.findViewById(R.id.add_species_btOK).setOnClickListener(this);
		diagView.findViewById(R.id.add_species_btCancle).setOnClickListener(this);
		
		diagAdd = new Dialog(this);
		diagAdd.setContentView(diagView);
		diagAdd.setTitle("�� ������ �߰��մϴ�.");
		diagAdd.show();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PrefSpeciesItem item = items.get(position);
		
		diagView = (LinearLayout)View.inflate(this, R.layout.edit_species, null);
		
		spinType = (Spinner)diagView.findViewById(R.id.edit_species_spinType);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this, R.array.species_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinType.setPrompt("�з��� �����ϼ���.");
		spinType.setAdapter(adapter);
		
		// ������ ��츦 ���� ���� �����͸� ����
		originalName = item.getName();
		originalType = item.getType();
		
		// �з��� ���õǾ��ֵ��� ��
		for(int i = 0;i < types.length ; i++){
			if(types[i].equals(originalType)){
				spinType.setSelection(i);
				break;
			}
		}
		
		// ���� ������� �̸��� EditText�� ä��
		EditText etName = (EditText)diagView.findViewById(R.id.edit_species_etName);
		etName.setText(originalName);
		
		// ���õ� ��, �ε���, ������ ����
		selectedIndex = position;
		selectedItem = item;
		selectedView = view;
		
		// ��ư �̺�Ʈ ����
		diagView.findViewById(R.id.edit_species_btOK).setOnClickListener(this);
		diagView.findViewById(R.id.edit_species_btCancle).setOnClickListener(this);
		diagView.findViewById(R.id.edit_species_btRemove).setOnClickListener(this);
		
		diagEdit = new Dialog(this);
		diagEdit.setContentView(diagView);
		diagEdit.setTitle("������ ������ �����մϴ�");
		diagEdit.show();
	}
}
