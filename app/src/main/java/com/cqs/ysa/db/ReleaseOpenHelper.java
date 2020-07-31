package com.cqs.ysa.db;

import android.content.Context;
import android.util.Log;

import com.cqs.ysa.ws.model.ChatMessageDao;
import com.cqs.ysa.ws.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by bingo on 2020/7/31 0031.
 *
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper {

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }
            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },ChatMessageDao.class);//最后参数是当前数据库表
        Log.e("ReleaseOpenHelper", "onUpgrade: " + oldVersion + " newVersion = " + newVersion);
    }
}
