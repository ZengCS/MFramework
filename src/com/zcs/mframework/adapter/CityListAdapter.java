package com.zcs.mframework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.entity.CityEntity;
import com.zcs.mframework.utils.StringUtils;

/**
 * 城市列表Adapter
 * 
 * @author ZengCS
 * @since 2014年9月10日
 */
public class CityListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CityEntity> itemList;

	public CityListAdapter(Context context, List<CityEntity> itemList) {
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
			convertView = mInflater.inflate(R.layout.list_item_city, null);

			holder = new ViewHolder();
			holder.cName = (TextView) convertView.findViewById(R.id.cityItemName);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CityEntity item = itemList.get(position);

		if (StringUtils.isEmpty(item.getSortCode()) || item.getSortCode().equals(item.getCityName())) {
			holder.cName.setText(item.getCityName());
		} else {
			holder.cName.setText("[" + item.getSortCode() + "]" + item.getCityName());
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView cName;
	}

	/**
	 * 刷新列表
	 * 
	 * @param data
	 */
	public void notifyDataSetChanged(List<CityEntity> data) {
		this.itemList = data;
		notifyDataSetChanged();
	}
}
