package com.cqs.ysa.ui

import android.os.Bundle
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import kotlinx.android.synthetic.main.activity_jz_video.*

/**
 * Created by Administrator on 2020/7/29 0029.
 */
class JZVideoActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jz_video)
        initVideo()
    }

    private fun initVideo() {
        val data = intent.data;
        if (data == null){
            //获取传递的数据
            var videoUrl = intent.getStringExtra("videoUrl")?:"https://v-cdn.zjol.com.cn/280443.mp4"
            var videoTitle = intent.getStringExtra("videoTitle")?:"视频播放"
            //jzVideoPlayer.setMediaInterface(JZMediaIjk::class.java)
            jzVideoPlayer.setUp(videoUrl,videoTitle)
            Glide.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(jzVideoPlayer.posterImageView)
        }else{
            if (data.toString().startsWith("http:")){
                //外部网络视频
                jzVideoPlayer.setUp(data.toString(), data.toString())
            }else{
                //从应用外响应
                jzVideoPlayer.setUp(data.path, data.toString())
            }
        }
    }
    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}