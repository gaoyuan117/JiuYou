package com.jiuyou.db.dao;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jiuyou.db.model.Category;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table CATEGORY.
 */
public class CategoryDao extends AbstractDao<Category, Long> {

	public static final String TABLENAME = "CATEGORY";

	/**
	 * Properties of entity Category.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id",
				true, "_id");
		public final static Property Name = new Property(1, String.class,
				"name", false, "NAME");
		public final static Property ParentId = new Property(2, Integer.class,
				"parentId", false, "PARENT_ID");
		public final static Property BigImg = new Property(3, String.class,
				"bigImg", false, "BIG_IMG");
		public final static Property SmallImg = new Property(4, String.class,
				"smallImg", false, "SMALL_IMG");
		public final static Property IsHot = new Property(5, Integer.class,
				"isHot", false, "IS_HOT");
		public final static Property Status = new Property(6, Integer.class,
				"status", false, "STATUS");
		public final static Property Type = new Property(7, Integer.class,
				"type", false, "TYPE");
	};

	public CategoryDao(DaoConfig config) {
		super(config);
	}

	public CategoryDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'CATEGORY' (" + //
				"'_id' INTEGER PRIMARY KEY ," + // 0: id
				"'NAME' TEXT," + // 1: name
				"'PARENT_ID' INTEGER," + // 2: parentId
				"'BIG_IMG' TEXT," + // 3: bigImg
				"'SMALL_IMG' TEXT," + // 4: smallImg
				"'IS_HOT' INTEGER," + // 5: isHot
				"'STATUS' INTEGER," + // 6: status
				"'TYPE' INTEGER);"); // 7: type
	}
    //批量增加
	public void insertAll(SQLiteDatabase db, List<Category> dataList) {

		/*
		 * String sql =
		 * "insert into CATEGORY (_id,NAME,PARENT_ID,BIG_IMG,SMALL_IMG,IS_HOT,STATUS,TYPE)values(?,?,?,?,?,?,?,?)"
		 * ; SQLiteStatement statment = db.compileStatement(sql);
		 * db.beginTransaction(); for (Category category : dataList) {
		 * statment.bindLong(1, category.getId()); statment.bindString(2,
		 * category.getName()); statment.bindLong(3, category.getParentId());
		 * statment.bindString(4, category.getBigImg()); statment.bindString(5,
		 * category.getSmallImg()); statment.bindLong(6, category.getIsHot());
		 * statment.bindLong(7, category.getStatus()); statment.bindLong(8,
		 * category.getType()); statment.executeInsert(); }
		 */
		String sql = "insert into CATEGORY (_id,NAME,STATUS)values(?,?,?)";
		SQLiteStatement statment = db.compileStatement(sql);
		db.beginTransaction();
		for (Category category : dataList) {
			statment.bindLong(1, category.getId());
			statment.bindString(2, category.getName());
			statment.bindLong(3, category.getStatus());
			statment.executeInsert();
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		//db.close();
		// statment.close();
	}
    //删除数据
	public void delteAllByCategory(SQLiteDatabase db) {

		String sql = "delete from category";
		db.beginTransaction();
		db.execSQL(sql);
        db.setTransactionSuccessful();
		db.endTransaction();
		
	}
	
	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "'CATEGORY'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, Category entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}

		String name = entity.getName();
		if (name != null) {
			stmt.bindString(2, name);
		}

		Integer parentId = entity.getParentId();
		if (parentId != null) {
			stmt.bindLong(3, parentId);
		}

		String bigImg = entity.getBigImg();
		if (bigImg != null) {
			stmt.bindString(4, bigImg);
		}

		String smallImg = entity.getSmallImg();
		if (smallImg != null) {
			stmt.bindString(5, smallImg);
		}

		Integer isHot = entity.getIsHot();
		if (isHot != null) {
			stmt.bindLong(6, isHot);
		}

		Integer status = entity.getStatus();
		if (status != null) {
			stmt.bindLong(7, status);
		}

		Integer type = entity.getType();
		if (type != null) {
			stmt.bindLong(8, type);
		}
	}

	/** @inheritdoc */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/** @inheritdoc */
	@Override
	public Category readEntity(Cursor cursor, int offset) {
		Category entity = new Category(
				//
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
				cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // parentId
				cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bigImg
				cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // smallImg
				cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // isHot
				cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // status
				cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // type
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, Category entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor
				.getLong(offset + 0));
		entity.setName(cursor.isNull(offset + 1) ? null : cursor
				.getString(offset + 1));
		entity.setParentId(cursor.isNull(offset + 2) ? null : cursor
				.getInt(offset + 2));
		entity.setBigImg(cursor.isNull(offset + 3) ? null : cursor
				.getString(offset + 3));
		entity.setSmallImg(cursor.isNull(offset + 4) ? null : cursor
				.getString(offset + 4));
		entity.setIsHot(cursor.isNull(offset + 5) ? null : cursor
				.getInt(offset + 5));
		entity.setStatus(cursor.isNull(offset + 6) ? null : cursor
				.getInt(offset + 6));
		entity.setType(cursor.isNull(offset + 7) ? null : cursor
				.getInt(offset + 7));
	}

	/** @inheritdoc */
	@Override
	protected Long updateKeyAfterInsert(Category entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/** @inheritdoc */
	@Override
	public Long getKey(Category entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
