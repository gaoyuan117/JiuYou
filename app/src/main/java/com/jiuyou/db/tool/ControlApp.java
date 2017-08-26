package com.jiuyou.db.tool;

import com.jiuyou.db.dao.DaoMaster;
import com.jiuyou.db.dao.DaoMaster.OpenHelper;
import com.jiuyou.db.dao.DaoSession;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ControlApp extends Application {

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	public static SQLiteDatabase db;
	// 数据库名，表名是自动被创建的
	public static final String DB_NAME = "dedou.db";

	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME,
					null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}

	public static SQLiteDatabase getSQLDatebase(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			db = daoMaster.getDatabase();
		}
		return db;
	}

	@Override
	public void onCreate() {

	}
}
