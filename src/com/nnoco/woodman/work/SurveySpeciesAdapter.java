package com.nnoco.woodman.work;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nnoco.woodman.R;
import com.nnoco.woodman.SurveyActivity;

public class SurveySpeciesAdapter extends BaseAdapter {
	private ArrayList<SurveySpeciesItem> items;
	SurveyActivity surveyActivity;
	LayoutInflater inflater;
	int layout;

	public SurveySpeciesAdapter(SurveyActivity activity,
			ArrayList<SurveySpeciesItem> items, int layout) {
		this.surveyActivity = activity;
		this.items = items;
		this.layout = layout;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
		}

		TextView tvName = (TextView) convertView
				.findViewById(R.id.survey_species_item_tvName);
		Button btTree = (Button) convertView
				.findViewById(R.id.survey_species_item_btTree);
		Button btRemove = (Button) convertView
				.findViewById(R.id.survey_species_item_btRemove);
		TextView tvSumOfTree = (TextView) convertView
				.findViewById(R.id.survey_species_item_tvCountOfTree);
		TextView tvSumOfRemove = (TextView) convertView
				.findViewById(R.id.survey_species_item_tvCountOfRemove);

		final SurveySpeciesItem item = items.get(position);
		final String name = item.getName();
		final View view = convertView;

		tvName.setText(item.getName());
		btTree.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				surveyActivity.setCurrent(item, pos, view);
				surveyActivity.showPopup(name);
			}
		});

		btRemove.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				surveyActivity.setCurrent(item, pos, view);
				surveyActivity.showPopup(name);
			}
		});

		tvSumOfTree.setText(item.getSumOfTreeCount() + "");
		tvSumOfRemove.setText(item.getSumOfRemoveCount() + "");

		return convertView;
	}

}
