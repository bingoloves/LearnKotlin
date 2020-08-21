package com.cqs.ysa.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import kotlinx.android.synthetic.main.activity_appbar.*

/**
 * Created by Administrator on 2020/8/17 0017.
 */
class AppBarLayoutActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbar)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
//        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.ctbl) as CollapsingToolbarLayout
//        imageView = findViewById<ImageView>(R.id.headerImage) as ImageView
//        val appBarLayout = findViewById<View>(R.id.appBar) as AppBarLayout
        setSupportActionBar(toolbar)
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE) // 也可以在xml中设置属性
        collapsingToolbar.setExpandedTitleColor(Color.BLACK)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)// 显示返回箭头
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var newalpha = 255 + verticalOffset
            newalpha = if (newalpha < 0) 0 else newalpha
            headerImage.setAlpha(newalpha)
        })
    }
}