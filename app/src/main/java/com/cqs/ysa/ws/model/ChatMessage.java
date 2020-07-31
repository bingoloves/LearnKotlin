package com.cqs.ysa.ws.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 创建数据库实体类
 @Entity 表示这个实体类一会会在数据库中生成对应的表，
 @Id 表示该字段是id，注意该字段的数据类型为包装类型Long
 @Property 则表示该属性将作为表的一个字段，其中nameInDb看名字就知道这个属性在数据库中对应的数据名称。
 @Transient 该注解表示这个属性将不会作为数据表中的一个字段。
 @NotNull 表示该字段不可以为空
 @Unique 表示该字段唯一
 */
@Entity
public class ChatMessage {

    @Id
    private Long id;

    private String content;
    private String time;
    private int isMeSend;//0是对方发送 1是自己发送
    private int isRead;//是否已读（0未读 1已读）

    @Generated(hash = 79830637)
    public ChatMessage(Long id, String content, String time, int isMeSend,
            int isRead) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.isMeSend = isMeSend;
        this.isRead = isRead;
    }

    @Generated(hash = 2271208)
    public ChatMessage() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsMeSend() {
        return isMeSend;
    }

    public void setIsMeSend(int isMeSend) {
        this.isMeSend = isMeSend;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
