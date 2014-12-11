package com.zcs.mframework.adapter;

import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zcs.mframework.R;
import com.zcs.mframework.entity.CountryEntity;

@SuppressLint("DefaultLocale")
public class DBListAdapter {
	private LayoutInflater mInflater;
	private LinearLayout layout;

	public DBListAdapter(Context context, LinearLayout layout) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
	}

	public void initList(List<CountryEntity> itemList) {
		for (CountryEntity e : itemList) {
			layout.addView(getView(e));
		}
	}

	public View getView(CountryEntity entity) {
		ViewHolder holder = new ViewHolder();
		LinearLayout convertView = (LinearLayout) mInflater.inflate(R.layout.db_list_row, null);

		holder.id = (TextView) convertView.findViewById(R.id.db_row_id);
		holder.rank = (TextView) convertView.findViewById(R.id.db_row_rank);
		holder.zhName = (TextView) convertView.findViewById(R.id.db_row_zh_name);
		holder.enName = (TextView) convertView.findViewById(R.id.db_row_en_name);
		holder.desc = (TextView) convertView.findViewById(R.id.db_row_desc);
		holder.state = (TextView) convertView.findViewById(R.id.db_row_state);

		holder.id.setText(entity.getId() + "");
		holder.rank.setText(entity.getRank() + "");
		holder.zhName.setText(entity.getZhName() + "");
		holder.enName.setText(entity.getEnName().toUpperCase(Locale.getDefault()) + "");
		holder.desc.setText(entity.getDescription() + "");
		holder.state.setText(entity.getState() + "");
		return convertView;
	}

	private static class ViewHolder {
		TextView id;
		TextView rank;
		TextView zhName;
		TextView enName;
		TextView desc;
		TextView state;
	}
}
