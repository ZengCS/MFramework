package com.zcs.mframework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.entity.CountryEntity;

public class MSlideListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CountryEntity> itemList;

	public MSlideListAdapter(Context context, List<CountryEntity> itemList) {
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
			convertView = mInflater.inflate(R.layout.slide_list_item, null);

			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.listTxt);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CountryEntity item = itemList.get(position);
		holder.name.setText(item.getZhName());

		return convertView;
	}

	private static class ViewHolder {
		TextView name;
	}

	/**
	 * 刷新列表
	 * 
	 * @param data
	 */
	public void notifyDataSetChanged(List<CountryEntity> data) {
		this.itemList = data;
		notifyDataSetChanged();
	}
}
