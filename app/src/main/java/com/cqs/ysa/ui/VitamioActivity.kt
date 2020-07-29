package com.cqs.ysa.ui

import android.net.Uri
import android.os.Bundle
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import io.vov.vitamio.LibsChecker
import io.vov.vitamio.MediaPlayer
import io.vov.vitamio.Vitamio
import io.vov.vitamio.widget.MediaController
import kotlinx.android.synthetic.main.activity_vitamio.*
import java.util.*

/**
 * Created by bingo on 2020/7/29 0029.
 */
class VitamioActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if (!LibsChecker.checkVitamioLibs(this))return
        setContentView(R.layout.activity_vitamio)
        Vitamio.initialize(this)
        initVideo()
    }

    private fun initVideo() {
        var videoUrl = "rtmp://58.200.131.2:1935/livetv/hunantv" //intent.getStringExtra("videoUrl")?:"https://cmgw-hz.lechange.com:8890/LCO/5C08022PBQ85CCE/0/1/20191213T015534/dev_5C08022PBQ85CCE_20191213T015534.m3u8"
        val options = HashMap<String, String>()
        options.put("rtmp_live", "1")
        vitamioVideoView.setVideoURI(Uri.parse(videoUrl), options)
        vitamioVideoView.setMediaController(MediaController(this))
        vitamioVideoView.requestFocus()
        vitamioVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.setPlaybackSpeed(1.0f) })
    }

}