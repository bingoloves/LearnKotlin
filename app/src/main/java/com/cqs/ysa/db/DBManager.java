package com.cqs.ysa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.cqs.ysa.ws.model.DaoMaster;
import com.cqs.ysa.ws.model.DaoSession;

/**
 * Created by bingo on 2020/7/31 0031.
 * 数据库管理类
 */

public class DBManager {
    private static String dbName = "ysa_db";
    private static DBManager mInstance;
    //可升级保存原数据
    private ReleaseOpenHelper openHelper;
    private Context context;
    private DaoSession daoSession;

    private DBManager(Context context) {
        this.context = context;
        initGreenDao();
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    /**
     * 切换数据库
     * @param dbName 数据库名称
     */
    public void switchDatabase(String dbName){
        this.dbName = dbName;
        initGreenDao();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new ReleaseOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
//        SQLiteDatabase db =  openHelper.getEncryptedWritableDb("aserbao"); //数据库加密密码为"aserbao"的写法
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new ReleaseOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }
}
