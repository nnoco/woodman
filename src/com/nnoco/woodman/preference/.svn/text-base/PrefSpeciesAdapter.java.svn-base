package com.nnoco.woodman.preference;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nnoco.woodman.R;

public class PrefSpeciesAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<PrefSpeciesItem> items;
	private LayoutInflater inflater;
	
	
	int layout;
	public PrefSpeciesAdapter(Context context, ArrayList<PrefSpeciesItem> items, int layout){
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
		
		TextView tvName = (TextView)convertView.findViewById(R.id.species_item_tvName);
		TextView tvType = (TextView)convertView.findViewById(R.id.species_item_tvType);
		PrefSpeciesItem item = items.get(pos);
		
		if(item.isNew() || item.isChanged()){
			convertView.findViewById(R.id.species_item_rlLayout)
				.setBackgroundColor(0xff555555);
		}else{
			convertView.findViewById(R.id.species_item_rlLayout)
			.setBackgroundColor(0x00000000);
		}
		
		
		tvName.setText(item.getName());
		tvType.setText(item.getType());
		
		return convertView;
	}

}
