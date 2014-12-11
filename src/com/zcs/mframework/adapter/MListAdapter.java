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
import com.zcs.mframework.entity.CountryEntity;
import com.zcs.mframework.utils.MFCommons;

public class MListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CountryEntity> itemList;

	public MListAdapter(Context context, List<CountryEntity> itemList) {
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
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();
			holder.rank = (TextView) convertView.findViewById(R.id.tv_rank);
			holder.zhName = (TextView) convertView.findViewById(R.id.tv_zh_name);
			holder.enName = (TextView) convertView.findViewById(R.id.tv_en_name);
			holder.desc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.state = (TextView) convertView.findViewById(R.id.tv_state);
			holder.img = (ImageView) convertView.findViewById(R.id.img_icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CountryEntity item = itemList.get(position);

		holder.rank.setText("Rank:" + item.getRank());
		holder.zhName.setText("zhName:" + item.getZhName());
		holder.enName.setText("enName:" + item.getEnName());
		holder.desc.setText("Description:" + item.getDescription());
		holder.state.setText("State:" + item.getState());

		int resId = 0;
		try {
			resId = MFCommons.getDrawableIdByName("flag_" + item.getEnName());
			holder.img.setImageResource(resId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView rank;
		TextView zhName;
		TextView enName;
		TextView desc;
		TextView state;
		ImageView img;
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
