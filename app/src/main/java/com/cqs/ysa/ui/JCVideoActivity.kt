package com.cqs.ysa.ui

import android.os.Bundle
import com.bumptech.glide.Glide
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_jc_video.*

/**
 * Created by Administrator on 2020/7/29 0029.
 */
class JCVideoActivity: BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jc_video)
        initVideo()
    }

    private fun initVideo() {
        var videoUrl = intent.getStringExtra("videoUrl")?:"https://cmgw-hz.lechange.com:8890/LCO/5C08022PBQ85CCE/0/1/20191213T015534/dev_5C08022PBQ85CCE_20191213T015534.m3u8"
        var videoTitle = intent.getStringExtra("videoTitle")?:"视频播放"
        jcVideoPlayer.setUp(videoUrl,JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,videoTitle)
        Glide.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(jcVideoPlayer.thumbImageView)
    }
    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }
}