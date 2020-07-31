package com.cqs.ysa.ws.model;

import android.content.Context;

import com.cqs.ysa.db.DBManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2020/7/31 0031.
 * 数据库表操作类
 */

public class ChatMessageOpt {

    /**
     * 插入一条消息记录
     *
     * @param context
     * @param chatMessage
     */
    public static void insert(Context context,ChatMessage chatMessage) {
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.insert(chatMessage);
    }

    /**
     * 插入一条数据 (如果存在则替换)
     * @param context
     * @param chatMessage
     */
    public static void insertOrReplace(Context context,ChatMessage chatMessage) {
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.insertOrReplace(chatMessage);
    }
    /**
     * 插入多条数据
     *
     * @param list
     */
    public static void insertList(Context context,List<ChatMessage> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.insertInTx(list);
    }

    /**
     * 删除一条数据
     * @param context
     * @param chatMessage
     */
    public static void delete(Context context,ChatMessage chatMessage){
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.delete(chatMessage);
    }

    /**
     * 删除多条数据
     * @param context
     * @param list
     */
    public static void deleteList(Context context,List<ChatMessage> list){
        if (list == null || list.isEmpty()) {
            return;
        }
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.deleteInTx(list);
    }

    /**
     * 更新数据
     * @param context
     * @param chatMessage
     */
    public static void update(Context context,ChatMessage chatMessage){
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        chatMessageDao.update(chatMessage);
    }

    /**
     * 查询全部数据
     * @param context
     * @return
     */
    public static List<ChatMessage> queryAll(Context context){
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        QueryBuilder<ChatMessage> chatMessageQueryBuilder = chatMessageDao.queryBuilder();
        return chatMessageQueryBuilder.list();
    }

    /**
     * 条件查询
     * @param context
     * @param id       示例：根据ID查询
     * @param limit    查询的条数
     * @return
     */
    public static List<ChatMessage> queryWhere(Context context,long id,int limit){
        ChatMessageDao chatMessageDao = DBManager.getInstance(context).getDaoSession().getChatMessageDao();
        QueryBuilder<ChatMessage> chatMessageQueryBuilder = chatMessageDao.queryBuilder();
        return chatMessageQueryBuilder.where(ChatMessageDao.Properties.Id.gt(id)).limit(limit).list();
    }
}
