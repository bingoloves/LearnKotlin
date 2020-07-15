package com.cqs.ysa.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.TwoActivity
import com.cqs.ysa.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class HomeFragment: BaseFragment() {

    override fun initView(view: View) {

    }

    /**
     * kotlin需要在此方法中 才能获取控件id 不然报KotlinNullPointerException,这是因为当前的view并没有返回
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var title = arguments?.get("key")
        Log.e("tag",title as String?)
        testBtn.text = " bingo "
        testBtn.setOnClickListener { view ->
//            startActivity<TwoActivity>("key" to "HomeFragment")
        }
    }
    override fun lazyLoad() {

    }

    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

}