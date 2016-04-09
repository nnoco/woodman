package com.nnoco.woodman.work;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nnoco.woodman.R;

public class UniquenessAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UniquenessItem> items;
	private int layout;
	private LayoutInflater inflater;
	public UniquenessAdapter(Context context, ArrayList<UniquenessItem> items, int layout){
		this.context = context;
		this.items = items;
		this.layout = layout;
		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
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
		if(convertView == null){
			convertView = inflater.inflate(layout, parent, false);
		}
		final UniquenessItem item = items.get(pos);
		TextView tvText = (TextView)convertView.findViewById(R.id.uniqueness_item_view_tvText);
		
		tvText.setText(item.getText());
		
		return convertView;
	}

}
