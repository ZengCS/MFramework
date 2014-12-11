package com.zcs.mframework.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CountryHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "Country.db";
	private final static int DATABASE_VERSION = 1;

	public final static String TABLE_NAME = "tb_country";
	public final static String C_ID = "c_id";// id
	public final static String C_RANK = "c_rank";// 排名
	public final static String C_ZHNAME = "c_zhName";// 中文名称
	public final static String C_ENNAME = "c_enName";// 英文名称
	public final static String C_DESCRIPTION = "c_description";// 描述
	public final static String C_STATE = "c_state";// 状态

	public CountryHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static CountryHelper mInstance;

	public synchronized static CountryHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new CountryHelper(context);
		}
		return mInstance;
	};

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE " + TABLE_NAME + "(");
		sb.append(C_ID + " INTEGER primary key autoincrement,");
		sb.append(C_RANK + " INTEGER,");
		sb.append(C_ZHNAME + " VARCHAR,");
		sb.append(C_ENNAME + " VARCHAR,");
		sb.append(C_DESCRIPTION + " VARCHAR,");
		sb.append(C_STATE + " VARCHAR);");

		// TODO 创建数据库
		try {
			db.execSQL(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
