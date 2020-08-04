package com.cqs.ysa.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.TaskStackBuilder
import android.widget.RemoteViews
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.service.NotificationCancelService
import com.cqs.ysa.utils.NotificationUtil
import org.jetbrains.anko.button
import org.jetbrains.anko.verticalLayout
import java.util.*

/**
 * Created by bingo on 2020/8/4 0004.
 * 通知栏类型
 */
class NotificationActivity:BaseActivity(){
    private val PROGRESS_NO_ID = 3//进度条通知id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button("普通通知一").setOnClickListener {
                showNotification()
            }
            button("普通通知二").setOnClickListener {
                showNotion()
            }
            button("大文本样式").setOnClickListener {
                showBigTextNotification()
            }
            button("大图片样式").setOnClickListener {
                showBigPictureNotification()
            }
            button("多行文本样式").setOnClickListener {
                showInboxNotification()
            }
            button("自定义view 的普通通知").setOnClickListener {
                showCustomNotification()
            }
            button("带按钮的自定义通知").setOnClickListener {
                showCustomNotificationButton()
            }
            button("进度条通知").setOnClickListener {
                showProgressNotification()
            }
        }
    }
    /**
     * 第一种方式，普通通知
     */
    private fun showNotification() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        //(new Random().nextInt(1000) 这个地方这么写的原因，是部分低版本的不能跳转，比如说小米3
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        NotificationUtil.showNotification(this, getString(R.string.app_name), "普通通知", resultPendingIntent)
    }


    /**
     * 第二种方式的普通通知
     */
    private fun showNotion() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(ChatActivity::class.java!!)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        NotificationUtil.showNotification(this, getString(R.string.app_name), "第二种方式的普通通知", resultPendingIntent)
    }

    /**
     * 自定义view 的普通通知
     *
     * @param
     */
    private fun showCustomNotification() {
        //自定义显示布局
        val contentViews = RemoteViews(this.packageName, R.layout.custom_notification)
        //通过控件的Id设置属性
        contentViews.setImageViewResource(R.id.ImageView, R.mipmap.ic_launcher)
        contentViews.setTextViewText(R.id.title, "自定义通知标题")
        contentViews.setTextViewText(R.id.content, "自定义通知内容")
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        //(new Random().nextInt(1000) 这个地方这么写的原因，是部分低版本的不能跳转，比如说小米3
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        NotificationUtil.showCustomNotification(this, contentViews, "ticker", resultPendingIntent)
    }

    /**
     * 带按钮的自定义通知
     *
     * @param
     */
    private fun showCustomNotificationButton() {
        //自定义显示布局
        val contentViews = RemoteViews(this.packageName, R.layout.custom_notification_with_button)
        //通过控件的Id设置属性
        contentViews.setImageViewResource(R.id.ImageView, R.mipmap.ic_launcher)
        contentViews.setTextViewText(R.id.title, "自定义通知标题")
        contentViews.setTextViewText(R.id.content, "自定义通知内容")


        val cancelIntent = Intent(this, NotificationCancelService::class.java)
        cancelIntent.putExtra("key_cancel", "cancel")
        cancelIntent.putExtra("key_conform", "confirm")
        val cancelPendingIntent = PendingIntent.getService(this, 1, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        contentViews.setOnClickPendingIntent(R.id.cancel_button, cancelPendingIntent)
        contentViews.setOnClickPendingIntent(R.id.confirm_button, cancelPendingIntent)

        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        //(new Random().nextInt(1000) 这个地方这么写的原因，是部分低版本的不能跳转，比如说小米3
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        NotificationUtil.showCustomNotification(this, contentViews, "ticker", resultPendingIntent)
    }


    /**
     * 进度条通知
     */
    private fun showProgressNotification() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationUtil.showProgressNotify(this, "下载", "正在下载", resultPendingIntent)

        val notification = builder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL//当通知被用户点击之后会自动被清除(cancel)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager?.notify(PROGRESS_NO_ID, notification)
        builder.setProgress(100, 0, false)
        //下载以及安装线程模拟
        Thread(Runnable {
            for (i in 0..99) {
                builder.setProgress(100, i, false)
                notificationManager.notify(PROGRESS_NO_ID, builder.build())
                //下载进度提示
                builder.setContentText("下载$i%")
                try {
                    Thread.sleep(50)//演示休眠50毫秒
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            //下载完成后更改标题以及提示信息
            builder.setContentTitle("开始安装")
            builder.setContentText("安装中...")
            //设置进度为不确定，用于模拟安装
            builder.setProgress(0, 0, true)
            notificationManager.notify(PROGRESS_NO_ID, builder.build())
        }).start()
    }

    /**
     * 展示InboxStyle 通知
     */
    private fun showInboxNotification() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val lineList = ArrayList<String>()
        for (i in 0..4) {
            lineList.add("多行文本" + i)
        }
        NotificationUtil.showInboxNotification(this, "标题标题", "InboxStyle形式的通知", lineList, resultPendingIntent)
    }

    /**
     * 展示bigPicture 通知
     */
    private fun showBigPictureNotification() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        NotificationUtil.showBigPictureNotification(this, "标题", "bigPictureStyle形式的通知", resultPendingIntent)
    }

    /**
     * 展示bigText 通知
     */
    private fun showBigTextNotification() {
        val resultIntent = Intent(this, ChatActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val resultPendingIntent = PendingIntent.getActivity(this, Random().nextInt(1000),
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        NotificationUtil.showBigTextNotification(this, "标题", "摘要摘要", "bigTextStyle通知", resultPendingIntent)
    }
}