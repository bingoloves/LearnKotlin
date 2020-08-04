package com.cqs.ysa.ui

import android.os.Bundle
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.statusbar.StatusBarUtil
import kotlinx.android.synthetic.main.layout_jzstd_notitle.*
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 * Created by Administrator on 2020/8/4 0004.
 */
class SplashActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setTranslucentStatus(this)
        verticalLayout {
            imageView {
                setImageResource(R.drawable.splash)
            }.lparams(height = matchParent, width = matchParent).postDelayed({ startActivity(MainActivity::class.java,true) },800)
        }
    }
}