package com.nnoco.woodman;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nnoco.data.HandleExternalData;
import com.nnoco.data.Util;
import com.nnoco.woodman.work.SurveySpeciesAdapter;
import com.nnoco.woodman.work.SurveySpeciesItem;

public class SurveyActivity extends Activity {
	PopupWindow popup;
	View popupview;
	RelativeLayout relative;
	FrameLayout graymask;
	Button popup_btClose;
	Button popup_btMode;
	TextView popup_tvTitle;
	TextView popup_tvAmount; // 팝업에서 현재 항목의 수량 표시
	
	
	
	// 전체 합계
	private TextView tvSumAll;

	// 현재 선택한 팝업
	private SurveySpeciesItem currentItem;
	private int currentPosition;
	private View currentView;

	// 리스트 뷰 관련
	ListView speciesListView;
	SurveySpeciesAdapter adapter;
	ArrayList<SurveySpeciesItem> listViewItems;

	// 타이틀 바 뒤로, 완료 버튼
	Button btBack, btComplete;

	// 입목, 제거 조절에서 더할 수, 빼기 모드면 -1
	int mode = 1;

	// 현재 팝업창의 상태, 켜져있으면 true;
	boolean popupState = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey);

		init();
		setListeners();
		
		sumAll();
	}

	private void init() {
		btBack = (Button) findViewById(R.id.survey_btBack);
		btComplete = (Button) findViewById(R.id.survey_btComplete);
		
		tvSumAll = (TextView) findViewById(R.id.survey_tvSumAll);

		relative = (RelativeLayout) findViewById(R.id.survey_mainLayout);
		popupview = View.inflate(this, R.layout.adjust, null);
		
		int width = (int)Util.convertDpToPixel(282);
		int height = (int)Util.convertDpToPixel(485);
		popup = new PopupWindow(popupview, width, height);
		graymask = (FrameLayout) findViewById(R.id.survey_graymask);
		popup_btClose = (Button) popupview.findViewById(R.id.adjust_btClose);
		popup_btMode = (Button) popupview.findViewById(R.id.adjust_btSubstract);
		popup_tvTitle = (TextView) popupview.findViewById(R.id.adjust_tvTitle);
		popup_tvAmount = (TextView) popupview.findViewById(R.id.adjust_tvAmount);

		speciesListView = (ListView) findViewById(R.id.survey_lvSpecies);
		listViewItems = new ArrayList<SurveySpeciesItem>();
		loadSelectedList(listViewItems);
		adapter = new SurveySpeciesAdapter(this, listViewItems,
				R.layout.survey_species_item);
		speciesListView.setAdapter(adapter);
		speciesListView.setDivider(new ColorDrawable(0xffffffff));
		speciesListView.setDividerHeight(1);
	}

	// 파일로부터 선택한 수종을 읽어옴
	private void loadSelectedList(ArrayList<SurveySpeciesItem> list) {
		String strList = HandleExternalData.load("주 수종 목록.txt");
		if (strList != null) {
			String[] splitList = strList.replace("\r", "").split("\n");
			String[] splitItem = null;

			for (int i = 0; i < splitList.length; i++) {
				if (splitList[0].length() == 0)
					break;

				splitItem = splitList[i].split("\t");

				list.add(new SurveySpeciesItem(splitItem[0], splitItem[1]));
			}
		}
	}

	private void setListeners() {
		setListeners_popup();

		findViewById(R.id.survey_btBack).setOnClickListener(mClickListener);
		findViewById(R.id.survey_btComplete).setOnClickListener(mClickListener);
	}

	private void setListeners_popup() {
		// Adjust Popup 타이틀 버튼
		popup_btClose.setOnClickListener(mClickListener);
		popup_btMode.setOnClickListener(mClickListener);

		Button[] btsTree = {
				(Button) popupview.findViewById(R.id.adjust_btTree06),
				(Button) popupview.findViewById(R.id.adjust_btTree08),
				(Button) popupview.findViewById(R.id.adjust_btTree10),
				(Button) popupview.findViewById(R.id.adjust_btTree12),
				(Button) popupview.findViewById(R.id.adjust_btTree14),
				(Button) popupview.findViewById(R.id.adjust_btTree16),
				(Button) popupview.findViewById(R.id.adjust_btTree18),
				(Button) popupview.findViewById(R.id.adjust_btTree20),
				(Button) popupview.findViewById(R.id.adjust_btTree22),
				(Button) popupview.findViewById(R.id.adjust_btTree24),
				(Button) popupview.findViewById(R.id.adjust_btTree26),
				(Button) popupview.findViewById(R.id.adjust_btTree28),
				(Button) popupview.findViewById(R.id.adjust_btTree30),
				(Button) popupview.findViewById(R.id.adjust_btTree32),
				(Button) popupview.findViewById(R.id.adjust_btTree34),
				(Button) popupview.findViewById(R.id.adjust_btTree36),
				(Button) popupview.findViewById(R.id.adjust_btTree38),
				(Button) popupview.findViewById(R.id.adjust_btTree40),
				(Button) popupview.findViewById(R.id.adjust_btTree42),
				(Button) popupview.findViewById(R.id.adjust_btTree44),
				(Button) popupview.findViewById(R.id.adjust_btTree46),
				(Button) popupview.findViewById(R.id.adjust_btTree48), };

		Button[] btsRemove = {
				(Button) popupview.findViewById(R.id.adjust_btRemove06),
				(Button) popupview.findViewById(R.id.adjust_btRemove08),
				(Button) popupview.findViewById(R.id.adjust_btRemove10),
				(Button) popupview.findViewById(R.id.adjust_btRemove12),
				(Button) popupview.findViewById(R.id.adjust_btRemove14),
				(Button) popupview.findViewById(R.id.adjust_btRemove16),
				(Button) popupview.findViewById(R.id.adjust_btRemove18),
				(Button) popupview.findViewById(R.id.adjust_btRemove20),
				(Button) popupview.findViewById(R.id.adjust_btRemove22),
				(Button) popupview.findViewById(R.id.adjust_btRemove24),
				(Button) popupview.findViewById(R.id.adjust_btRemove26),
				(Button) popupview.findViewById(R.id.adjust_btRemove28),
				(Button) popupview.findViewById(R.id.adjust_btRemove30),
				(Button) popupview.findViewById(R.id.adjust_btRemove32),
				(Button) popupview.findViewById(R.id.adjust_btRemove34),
				(Button) popupview.findViewById(R.id.adjust_btRemove36),
				(Button) popupview.findViewById(R.id.adjust_btRemove38),
				(Button) popupview.findViewById(R.id.adjust_btRemove40),
				(Button) popupview.findViewById(R.id.adjust_btRemove42),
				(Button) popupview.findViewById(R.id.adjust_btRemove44),
				(Button) popupview.findViewById(R.id.adjust_btRemove46),
				(Button) popupview.findViewById(R.id.adjust_btRemove48), };

		View.OnClickListener treeClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				int diameter = Integer.parseInt((String) v.getTag());
				int index = (diameter - 6) / 2;
				int amount = currentItem.adjustTree(index, mode);
				if (mode == -1)
					modeChange();
				Util.ps.clickTree();
				
				// 팝업에서 수량 표시
				popup_tvAmount.setText(amount + "");
			}
		};

		View.OnClickListener removeClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				int diameter = Integer.parseInt((String) v.getTag());
				int index = (diameter - 6) / 2;
				int amount = currentItem.adjustRemove(index, mode);
				if (mode == -1)
					modeChange();
				Util.ps.clickRemove();
				Util.vibe.vibrate(60);
				
				// 팝업에서 수량 표시
				popup_tvAmount.setText(amount + "");
			}

		};

		for (int i = 0; i < btsRemove.length; i++) {
			btsTree[i].setOnClickListener(treeClickListener);
			btsRemove[i].setOnClickListener(removeClickListener);
		}
	}

	private Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = null;

			switch (id) {
			case R.id.survey_btBack:
				finish();
				break;

			case R.id.survey_btComplete:
				Util.shortToast("매목 조사 폴더에 저장하였습니다.");
				finish();
				break;

			case R.id.adjust_btSubstract:
				modeChange();
				break;
			case R.id.adjust_btClose:
				closePopup();
				break;
			}
		}

	};

	private void modeChange() {
		if (mode == -1) {
			Util.shortToast("빼기가 취소되었습니다.");
			popup_btMode.setText("빼기");
			mode = 1;
		} else {
			Util.shortToast("버튼을 클릭하면 1 감소합니다.");
			popup_btMode.setText("빼기 취소");
			mode = -1;
		}
	}

	private void sumAll() {
		int allTree = 0;
		int allRemove = 0;
		double removeRate = .0;

		for (SurveySpeciesItem item : listViewItems) {
			allTree += item.getSumOfTreeCount();
			allRemove += item.getSumOfRemoveCount();
		}

		removeRate = (double) allRemove / (allTree + allRemove) * 100 ;

		String sumString = String.format("합계 : 입목(%d), 제거(%d), 제거율(%.2f%%)",
				allTree, allRemove, removeRate);
		
		tvSumAll.setText(sumString);
	}

	public void showPopup(String treename) {
		popupState = true;
		graymask.setVisibility(View.VISIBLE);
		popup_tvTitle.setText(treename);
		popup.showAtLocation(relative, Gravity.CENTER, 0, 0);
	}

	public void closePopup() {
		popupState = false;
		popup.dismiss();
		graymask.setVisibility(View.INVISIBLE);

		adapter.notifyDataSetChanged();
		currentItem.saveCounts();
		
		sumAll(); // 합계 정산
		
		popup_tvAmount.setText("수량"); // 수량 표시 텍스트뷰 원래대로.
	}

	public void setCurrent(SurveySpeciesItem item, int position, View view) {
		currentItem = item;
		currentPosition = position;
		currentView = view;
	}

	// 팝업창이 떴을 때 뒤로가기 버튼 동작
	@Override
	public void onBackPressed() {
		if (popupState) {
			closePopup();
		} else {
			super.onBackPressed();
		}
	}
}
