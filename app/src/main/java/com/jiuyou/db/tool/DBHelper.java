package com.jiuyou.db.tool;

import com.jiuyou.db.dao.CategoryDao;
import com.jiuyou.db.dao.DaoSession;
import com.jiuyou.db.dao.GoodsDao;
import com.jiuyou.db.dao.RegionDao;
import com.jiuyou.db.dao.VersionDao;

import android.content.Context;

public class DBHelper {
	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static Context appContext;
	//private DaoSession mDaoSession;
	//private GoodsDao goodDao;
	//private RegionDao regionDao;
	
	private DBHelper() {
	}

	// 单例模式，DBHelper只初始化一次
	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (appContext == null) {
				appContext = context.getApplicationContext();
			}
			//instance.mDaoSession = ControlApp.getDaoSession(context);
			//instance.goodDao = instance.mDaoSession.getGoodsDao();
			//instance.regionDao = instance.mDaoSession.getRegionDao();
		}
		return instance;
	}
	//返回DaoSession对象
	public static DaoSession getdaosession(Context context){
		return ControlApp.getDaoSession(context);
	}
	
    //返回regionDao对象
	public static RegionDao getRegionDao(DaoSession mDaoSession){
		return mDaoSession.getRegionDao();
	}
	//返回goodsDao对象
	public static GoodsDao getGoodDao(DaoSession mDaoSession){
		return mDaoSession.getGoodsDao();
	}
	//返回versionDao对象
	public static VersionDao getVersionDao(DaoSession mDaoSession){
		return mDaoSession.getVersionDao();
	}
	//返回CategoryDao对象
	public static CategoryDao getCategoryDao(DaoSession mDaoSession){
		return mDaoSession.getCategoryDao();
	}
	
	
	// 删除所有表
	public void dropAllTable(DaoSession mDaoSession) {
		GoodsDao.dropTable(mDaoSession.getDatabase(), true);
		RegionDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 创建所有表
	public void createAllTable(DaoSession mDaoSession) {
		GoodsDao.createTable(mDaoSession.getDatabase(), true);
		RegionDao.createTable(mDaoSession.getDatabase(), true);
	}

	/**
	 * insert or update note
	 * 
	 * @param note
	 * @return insert or update note id
	 */
	/*// 插入或者修改goods项
	public long saveGoods(Goods goods) {
		return goodDao.insertOrReplace(goods);
	}

	// 获得所有的Goods倒序排存到List列表里面
	public List<Goods> loadAllGood() {
		List<Goods> goods = new ArrayList<Goods>();
		List<Goods> tmpgoods = goodDao.loadAll();
		int len = tmpgoods.size();
		for (int i = len - 1; i >= 0; i--) {
			goods.add(tmpgoods.get(i));
		}
		return goods;
	}

	// 删除某一项Goods
	public void DeleteNoteBySession(Goods entity) {
		QueryBuilder<Goods> mqBuilder = goodDao.queryBuilder();
		mqBuilder.where(Properties.Id.eq(entity.getId()));
		List<Goods> chatEntityList = mqBuilder.build().list();
		goodDao.deleteInTx(chatEntityList);
	}

	// 根据id找到某一项
	public Goods loadNote(long id) {
		return goodDao.load(id);
	}

	// 获得所有的Goods列表
	public List<Goods> loadAllNote() {
		return goodDao.loadAll();
	}

	// 查询满足params条件的列表
	public List<Goods> queryNote(String where, String... params) {
		//ArrayList<Goods> ad = new ArrayList<Goods>();
		return goodDao.queryRaw(where, params);
	}

	// QueryBuilder来查询
	public List<Goods> loadLastMsgBySessionid(String sessionid) {
		QueryBuilder<Goods> mqBuilder = goodDao.queryBuilder();
		mqBuilder.where(Properties.Id.eq(sessionid))
				.orderDesc(Properties.Id).limit(1);
		return mqBuilder.list();
	}

	// 插入或者修改goods项
		public long saveRegion(Region region) {
			//regionDao.insertOrReplaceInTx(entities)
			
			return regionDao.insertOrReplace(region);
		}

		// 获得所有的Region倒序排存到List列表里面
		public List<Region> loadAllRegionDESC() {
			List<Region> region = new ArrayList<Region>();
			List<Region> tmpregions = regionDao.loadAll();
			int len = tmpregions.size();
			for (int i = len - 1; i >= 0; i--) {
				region.add(tmpregions.get(i));
			}
			return region;
		}

		// 删除某一项Goods
		public void DeleteRegionById(Region entity) {
			QueryBuilder<Region> mqBuilder = regionDao.queryBuilder();
			mqBuilder.where(Properties.Id.eq(entity.getId()));
			List<Region> chatEntityList = mqBuilder.build().list();
			regionDao.deleteInTx(chatEntityList);
		}

		// 根据id找到某一项
		public Region loadregionById(long id) {
			return regionDao.load(id);
		}

		// 获得所有的Region列表
		public List<Region> loadAllRegion() {
			return regionDao.loadAll();
		}

		// 查询满足params条件的列表
		public List<Region> queryRegion(String where, String... params) {
			//ArrayList<Goods> ad = new ArrayList<Goods>();
			return regionDao.queryRaw(where, params);
		}

		// QueryBuilder来查询
		public List<Region> loadLastMsgByRegion(String sessionid) {
			QueryBuilder<Region> mqBuilder = regionDao.queryBuilder();
			mqBuilder.where(Properties.Id.eq(sessionid))
					.orderDesc(Properties.Id).limit(1);
			return mqBuilder.list();
		}*/
	
	
	/*public List<Goods> loadMoreMsgById(String sessionid, Long id) {
		QueryBuilder<Goods> mqBuilder = goodDao.queryBuilder();
		mqBuilder.where(Properties.Id.lt(id))
				.where(Properties.Id.eq(sessionid))
				.orderDesc(Properties.Id).limit(20);
		return mqBuilder.list();
	}*/
}