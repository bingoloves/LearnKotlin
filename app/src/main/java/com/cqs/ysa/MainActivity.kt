package com.cqs.ysa

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.fragment.*
import com.cqs.ysa.statusbar.StatusBarUtil
import com.cqs.ysa.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    val mFragments = arrayOfNulls<Fragment>(5)
    var curIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex")
        }
//        verticalLayout {
//            val name = editText()
//            button("Say Hello") {
//                onClick {
//                    toast("Hello, ${name.text}!")
//                }
//            }
//        }
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        mFragments[0] = HomeFragment().newInstance("首页")
        mFragments[1] = VideoFragment().newInstance("视频")
        mFragments[2] = NewsFragment().newInstance("新闻")
        mFragments[3] = StatisticFragment().newInstance("统计")
        mFragments[4] = MineFragment().newInstance("个人中心")
        Log.e("TAG","onCreate curIndex = "+curIndex)
        FragmentUtils.add(supportFragmentManager, mFragments as Array<Fragment>, R.id.container, curIndex)
        StatusBarUtil.setStatusBarColor(this, resources.getColor(R.color.colorPrimaryDark))
    }
    var navigationListener = BottomNavigationView.OnNavigationItemSelectedListener{ menuItem ->
        when(menuItem.itemId){
            R.id.nav_home ->{
                curIndex = 0
                FragmentUtils.showHide(curIndex, mFragments as Array<Fragment>)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_video ->{
                curIndex = 1
                FragmentUtils.showHide(curIndex, mFragments as Array<Fragment>)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_news ->{
                curIndex = 2
                FragmentUtils.showHide(curIndex, mFragments as Array<Fragment>)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_statistic ->{
                curIndex = 3
                FragmentUtils.showHide(curIndex, mFragments as Array<Fragment>)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_mine ->{
                curIndex = 4
                FragmentUtils.showHide(curIndex, mFragments as Array<Fragment>)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.e("TAG","curIndex = "+curIndex)
        outState?.putInt("curIndex", curIndex)
    }

}