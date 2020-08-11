package com.cqs.ysa.fragment

import android.view.View
import android.widget.ImageView
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.fragment_video.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.util.ArrayList

/**
 * Created by bingo on 2020/7/23 0023.
 * GSYVideoPlayer 视频播放器
 */

class VideoFragment : BaseFragment(){

    var orientationUtils:OrientationUtils? = null
    var videoOptionBuilder:GSYVideoOptionBuilder? = null
    var testUrl = arrayOf(
            "rtmp://media3.scctv.net/live/scctv_800",//美国2
            "rtmp://202.69.69.180:443/webcast/bshdlive-pc",//香港财经
            "rtmp://58.200.131.2:1935/livetv/hunantv",//湖南卫视
            "http://www.w3school.com.cn/i/movie.mp4", //测试mp4视频
            "https://cmgw-hz.lechange.com:8890/LCO/5C08022PBQ85CCE/0/1/20191213T015534/dev_5C08022PBQ85CCE_20191213T015534.m3u8"
    )
    var videoUrl = testUrl[0]
    var videoTitle = "视频"
    var isReload: Boolean = false//适配HLS,判断是否需要重新加载
    override fun contentView(): Int {
        return R.layout.fragment_video
    }

    override fun initView(view: View?) {
        initVideoConfig()
        play()
    }

    /**
     * 初始化视频相关的参数
     */
    private fun initVideoConfig() {
//        val imageView = ImageView(this)//可以自定义封面
//        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView.setImageResource(R.drawable.loading_img)
        /**此中内容：优化加载速度，降低延迟*/
        var videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp")
        val list = ArrayList<VideoOptionModel>()
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp")
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video") //根据媒体类型来配置
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 20000)
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316)
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1)  // 无限读
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100)
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 10240)
        list.add(videoOptionModel)
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1)
        list.add(videoOptionModel)
        //关闭播放器缓冲，这个必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0)
        list.add(videoOptionModel)
        //禁止拖动进度条 无效
        videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1)
        list.add(videoOptionModel)
        GSYVideoManager.instance().optionModelList = list
        /**此中内容：优化加载速度，降低延迟*/
        //设置旋转
        orientationUtils = OrientationUtils(activity, videoPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false
        videoOptionBuilder = GSYVideoOptionBuilder()
                //.setThumbImageView(imageView)
                .setIsTouchWiget(true)//设置小屏是否触摸滑动
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1f)
                .setUrl(videoUrl)
                .setCacheWithPlay(true)
                .setVideoTitle(videoTitle)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        //开始播放了才能旋转和全屏
                        orientationUtils?.isEnable = true
                        isReload = true
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        if (orientationUtils != null) {
                            orientationUtils?.backToProtVideo()
                        }
                    }
                })
        videoOptionBuilder?.build(videoPlayer)

        videoPlayer.fullscreenButton.setOnClickListener(View.OnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            videoPlayer.startWindowFullscreen(context, true, true)
        })

        videoPlayer.setLockClickListener(LockClickListener { view, lock ->
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }
        })
        //videoPlayer.setUp(videoUrl, true, videoTitle)
        //videoPlayer.titleTextView.visibility =View.VISIBLE
        //设置返回键
        //videoPlayer.backButton.visibility = View.VISIBLE
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        //videoPlayer.fullscreenButton.setOnClickListener(View.OnClickListener { orientationUtils?.resolveByClick() })
        //是否可以滑动调整
        //videoPlayer.setIsTouchWiget(true)
        //设置返回按键功能
        //videoPlayer.backButton.setOnClickListener(View.OnClickListener {activity?.finish()})
    }

    override fun lazyLoad() {}

    private fun play(){
        videoPlayer.startPlayLogic()
    }

    /**
     * 更新视频地址 继续播放
     */
    private fun update(url:String){
        if(videoPlayer.isInPlayingState) videoPlayer.release()
        videoOptionBuilder?.setUrl(url)?.build(videoPlayer)
        videoPlayer.startPlayLogic()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()
    }
}