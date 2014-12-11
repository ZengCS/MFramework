package com.zcs.mframework.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zcs.mframework.entity.CountryEntity;

public class CountryManager {
	private CountryHelper helper;
	private SQLiteDatabase db;

	public CountryManager(Context context) {
		helper = CountryHelper.getInstance(context);
		db = helper.getWritableDatabase();
	}

	/**
	 * 插入
	 * 
	 * @param CountryEntity
	 */
	public void insert(CountryEntity entity) {
		synchronized (helper) {
			// 看数据库是否关闭
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			// 开始事务
			db.beginTransaction();
			try {
				ContentValues cv = new ContentValues();
				cv.put(CountryHelper.C_RANK, entity.getRank());
				cv.put(CountryHelper.C_ZHNAME, entity.getZhName());
				cv.put(CountryHelper.C_ENNAME, entity.getEnName());
				cv.put(CountryHelper.C_DESCRIPTION, entity.getDescription());
				cv.put(CountryHelper.C_STATE, entity.getState());
				db.insert(CountryHelper.TABLE_NAME, null, cv);
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	/**
	 * 更新
	 * 
	 * @param CountryEntity
	 */
	public void update(CountryEntity entity) {
		synchronized (helper) {
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				String where = CountryHelper.C_ID + " = ?";
				String[] whereValue = { Integer.toString(entity.getId()) };

				ContentValues cv = new ContentValues();
				cv.put(CountryHelper.C_RANK, entity.getRank());
				cv.put(CountryHelper.C_ZHNAME, entity.getZhName());
				cv.put(CountryHelper.C_ENNAME, entity.getEnName());
				cv.put(CountryHelper.C_DESCRIPTION, entity.getDescription());
				cv.put(CountryHelper.C_STATE, entity.getState());
				db.update(CountryHelper.TABLE_NAME, cv, where, whereValue);
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	// 删除指定数据
	public void deleteById(String id) {
		synchronized (helper) {
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				db.execSQL("DELETE FROM " + CountryHelper.TABLE_NAME + " WHERE id = ? ", new String[] { id });
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	/**
	 * 删除所有数据
	 */
	public void deleteAll() {
		synchronized (helper) {
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				db.execSQL("DELETE FROM " + CountryHelper.TABLE_NAME);
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	public List<CountryEntity> list() {
		List<CountryEntity> list = new ArrayList<CountryEntity>();
		synchronized (helper) {
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			String order = " ORDER BY " + CountryHelper.C_RANK;
			Cursor c = queryTheCursor(order, "ASC");
			CountryEntity entity;
			try {
				while (c.moveToNext()) {
					entity = new CountryEntity();
					entity.setId(c.getInt(c.getColumnIndex(CountryHelper.C_ID)));
					entity.setRank(c.getInt(c.getColumnIndex(CountryHelper.C_RANK)));
					entity.setZhName(c.getString(c.getColumnIndex(CountryHelper.C_ZHNAME)));
					entity.setEnName(c.getString(c.getColumnIndex(CountryHelper.C_ENNAME)));
					entity.setDescription(c.getString(c.getColumnIndex(CountryHelper.C_DESCRIPTION)));
					entity.setState(c.getString(c.getColumnIndex(CountryHelper.C_STATE)));
					list.add(entity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.close();
			}
		}
		return list;
	}

	/**
	 * 根据ID获取指定对象
	 * 
	 * @param id
	 * @return
	 */
	public CountryEntity getById(String id) {
		CountryEntity entity = null;
		synchronized (helper) {
			if (!db.isOpen()) {
				db = helper.getWritableDatabase();
			}
			Cursor c = queryTheCursor(id);
			try {
				while (c.moveToNext()) {
					entity = new CountryEntity();
					entity.setId(c.getInt(c.getColumnIndex(CountryHelper.C_ID)));
					entity.setRank(c.getInt(c.getColumnIndex(CountryHelper.C_RANK)));
					entity.setZhName(c.getString(c.getColumnIndex(CountryHelper.C_ZHNAME)));
					entity.setEnName(c.getString(c.getColumnIndex(CountryHelper.C_ENNAME)));
					entity.setDescription(c.getString(c.getColumnIndex(CountryHelper.C_DESCRIPTION)));
					entity.setState(c.getString(c.getColumnIndex(CountryHelper.C_STATE)));
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.close();
			}
		}
		return entity;
	}

	/**
	 * 根据ID获取单个游标
	 * 
	 * @param id
	 * @return
	 */
	public Cursor queryTheCursor(String id) {
		String sql = "SELECT * FROM " + CountryHelper.TABLE_NAME + " WHERE id = ?";
		String[] selectionArgs = new String[] { id };
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		return cursor;
	}

	/**
	 * 获取所有游标
	 * 
	 * @return
	 */
	public Cursor queryTheCursor(String orderBy, String flag) {
		// String sql = "SELECT * FROM " + CountryHelper.TABLE_NAME +
		// " ORDER BY " + CountryHelper.C_RANK + " DESC";
		String sql = "SELECT * FROM " + CountryHelper.TABLE_NAME + orderBy + " " + flag;
		String[] selectionArgs = new String[] {};
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		return cursor;
	}
}
