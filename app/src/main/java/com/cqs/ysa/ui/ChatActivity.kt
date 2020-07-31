package com.cqs.ysa.ui

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.adapter.recyclerview.MultiItemTypeAdapter
import com.cqs.ysa.adapter.recyclerview.base.ItemViewDelegate
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.statusbar.StatusBarUtil
import com.cqs.ysa.ws.JWebSocketClient
import com.cqs.ysa.ws.model.ChatMessage
import com.cqs.ysa.ws.model.ChatMessageOpt
import com.cqs.ysa.ws.receiver.ChatMessageReceiver
import com.cqs.ysa.ws.service.JWebSocketClientService
import com.cqs.ysa.ws.utils.MessageNotification
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.layout_keyboard_xhs.*
import java.util.*

/**
 * Created by bingo on 2020/7/29 0029.
 */
class ChatActivity : BaseActivity(){
    //初始化，有add，remove方法的集合
    var chatList = ArrayList<ChatMessage>()
    var adapter: MultiItemTypeAdapter<ChatMessage>? = null
    var socketClientService:JWebSocketClientService? = null
    var client: JWebSocketClient? = null
    var chatMessageReceiver:ChatMessageReceiver? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("ChatActivity", "服务与活动成功绑定")
            var binder = iBinder as JWebSocketClientService.JWebSocketClientBinder
            socketClientService = binder.service
            client = socketClientService?.client
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("MainActivity", "服务与活动成功断开")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        initWebSocket()
        initView()
    }

    /**
     * 初始化IM 服务
     */
    private fun initWebSocket() {
        //启动服务
        startJWebSClientService()
        //绑定服务
        bindService()
        //注册广播
        doRegisterReceiver()
        //检测通知是否开启
        MessageNotification.checkNotification(this)
    }

    /**
     * 绑定服务
     */
    private fun bindService() {
        val bindIntent = Intent(this, JWebSocketClientService::class.java)
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private fun startJWebSClientService() {
        val intent = Intent(this, JWebSocketClientService::class.java)
        startService(intent)
    }

    /**
     * 动态注册广播
     */
    private fun doRegisterReceiver() {
        chatMessageReceiver = ChatMessageReceiver()
        chatMessageReceiver?.setChatMessageListener (object : ChatMessageReceiver.ChatMessageListener{
            override fun onReceive(message: ChatMessage?) {
                chatList.add(message!!)
                ChatMessageOpt.insertOrReplace(activity,message)
                adapter?.notifyDataSetChanged()
            }
        })
        val filter = IntentFilter(ChatMessageReceiver.ACTION_CHAT_MESSAGE)
        registerReceiver(chatMessageReceiver, filter)
    }

    private fun initView() {
        contentRv.layoutManager = LinearLayoutManager(this)
        chatList = ChatMessageOpt.queryAll(this) as ArrayList<ChatMessage>
        adapter = ChatAdapter(this,chatList)
        //设置缓存，避免数据混乱问题
        //contentRv.setItemViewCacheSize(100)
        contentRv.adapter = adapter
        smartRefresh.setEnableLoadMore(false)
        smartRefresh.setOnRefreshListener {
            smartRefresh.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }
        et_content.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               var msg =  et_content.text.toString().trim()
                if (msg.isNotEmpty()){
                    btn_send.visibility = View.VISIBLE
                } else{
                    btn_send.visibility = View.GONE
                }
            }
        })
        btn_send.setOnClickListener {
            var content =  et_content.text.toString().trim()
            if (content.isNotEmpty()){
                val connect = socketClientService?.isConnect?:false
                if (connect) {
                    socketClientService?.sendMsg(content)
                    //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
                    val chatMessage = ChatMessage()
                    chatMessage.content = content
                    chatMessage.isMeSend = 1
                    chatMessage.isRead = 1
                    chatMessage.time = System.currentTimeMillis().toString() + ""
                    chatList.add(chatMessage)
                    ChatMessageOpt.insertOrReplace(activity,chatMessage)
                    adapter?.notifyDataSetChanged()
                    et_content.setText("")
                } else {
                    toast("连接已断开，请稍等或重启App哟")
                }
            } else {
                toast("消息不能为空哟")
            }
        }
    }

    /**
     * 数据适配器
     */
    class ChatAdapter(context:Context,data:List<ChatMessage>) : MultiItemTypeAdapter<ChatMessage>(context,data){
        init {
            addItemViewDelegate(ReceiveItemType())
            addItemViewDelegate(SendItemType())
        }
    }
    /**
     * 接收类型
     */
   class ReceiveItemType: ItemViewDelegate<ChatMessage> {
       override fun getItemViewLayoutId(): Int {
           return R.layout.layout_item_chat_receive
       }

       override fun isForViewType(item: ChatMessage?, position: Int): Boolean {
           return item?.isMeSend == 0
       }

       override fun convert(holder: ViewHolder?, bean: ChatMessage?, position: Int) {
            holder!!.setText(R.id.tv_content,bean!!.content)
            holder.setText(R.id.tv_name,"管理員")
       }
   }

    /**
     * 发送类型
     */
    class SendItemType: ItemViewDelegate<ChatMessage> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.layout_item_chat_send
        }

        override fun isForViewType(item: ChatMessage?, position: Int): Boolean {
            return item?.isMeSend == 1
        }

        override fun convert(holder: ViewHolder?, bean: ChatMessage?, position: Int) {
            holder!!.setText(R.id.tv_content,bean!!.content)
            holder.setText(R.id.tv_isRead,"已读")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(chatMessageReceiver)
        unbindService(serviceConnection)
    }
}