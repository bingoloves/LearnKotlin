package com.cqs.ysa.ui

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.fragment.*
import com.cqs.ysa.statusbar.StatusBarUtil
import com.cqs.ysa.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

@SuppressWarnings("unchecked")
class MainActivity : BaseActivity() {
    val mFragments = arrayOfNulls<Fragment>(5)
    var curIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex")
        }
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        mFragments[0] = getFragment("驾校宝典",DrivingQuestionsFragment())
        mFragments[1] = getFragment("股票", StockFragment())
        mFragments[2] = getFragment("新闻",NewsFragment())
        mFragments[3] = getFragment("天气预报", WeatherFragment())
        mFragments[4] = getFragment("个人中心",MineFragment())
        FragmentUtils.add(supportFragmentManager, mFragments as Array<Fragment>, R.id.container, curIndex)
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE),200)
    }

    /**
     * 获取fragment
     */
    private fun getFragment(title:String,fragment: Fragment):Fragment{
        val args = Bundle()
        args.putString("key",title)
        fragment.arguments = args
        return fragment
    }

    /**
     * 底部切换监听
     */
    private var navigationListener = BottomNavigationView.OnNavigationItemSelectedListener{ menuItem ->
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

    /**
     * 异常退出时 记录缓存
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
//        outState?.putInt("curIndex", curIndex)
        outState!!.putInt("curIndex",curIndex)
    }

}