package com.cqs.ysa.ws.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_MESSAGE".
*/
public class ChatMessageDao extends AbstractDao<ChatMessage, Long> {

    public static final String TABLENAME = "CHAT_MESSAGE";

    /**
     * Properties of entity ChatMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Time = new Property(2, String.class, "time", false, "TIME");
        public final static Property IsMeSend = new Property(3, int.class, "isMeSend", false, "IS_ME_SEND");
        public final static Property IsRead = new Property(4, int.class, "isRead", false, "IS_READ");
    }


    public ChatMessageDao(DaoConfig config) {
        super(config);
    }
    
    public ChatMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_MESSAGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CONTENT\" TEXT," + // 1: content
                "\"TIME\" TEXT," + // 2: time
                "\"IS_ME_SEND\" INTEGER NOT NULL ," + // 3: isMeSend
                "\"IS_READ\" INTEGER NOT NULL );"); // 4: isRead
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_MESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatMessage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(3, time);
        }
        stmt.bindLong(4, entity.getIsMeSend());
        stmt.bindLong(5, entity.getIsRead());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatMessage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(3, time);
        }
        stmt.bindLong(4, entity.getIsMeSend());
        stmt.bindLong(5, entity.getIsRead());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChatMessage readEntity(Cursor cursor, int offset) {
        ChatMessage entity = new ChatMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // time
            cursor.getInt(offset + 3), // isMeSend
            cursor.getInt(offset + 4) // isRead
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChatMessage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsMeSend(cursor.getInt(offset + 3));
        entity.setIsRead(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChatMessage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChatMessage entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatMessage entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}