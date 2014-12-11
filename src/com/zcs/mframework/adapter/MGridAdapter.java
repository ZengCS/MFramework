package com.zcs.mframework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.entity.MainGridItemEntity;

public class MGridAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MainGridItemEntity> itemList;

	public MGridAdapter(Context context, List<MainGridItemEntity> itemList) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.itemList = itemList;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.night_item_circle, null);

			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.itemName);
			holder.icon = (ImageView) convertView.findViewById(R.id.itemIcon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MainGridItemEntity item = itemList.get(position);

		holder.name.setText(item.getName());
		holder.icon.setImageResource(item.getIconId());

		return convertView;
	}

	private static class ViewHolder {
		TextView name;
		ImageView icon;
	}

	/**
	 * 刷新列表
	 * 
	 * @param newList
	 */
	public void notifyDataSetChanged(List<MainGridItemEntity> newList) {
		this.itemList = newList;
		notifyDataSetChanged();
	}

}
