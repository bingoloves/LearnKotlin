package com.cqs.ysa.fragment

import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment

/**
 * Created by bingo on 2020/7/15 0015.
 */
class MineFragment : BaseFragment(){
    override fun getContentView(): Int {
        return R.layout.fragment_mine
    }
    override fun initView(view: View) {
        var title = arguments?.get("key")
        Log.e("tag", title as String?)

    }

    override fun lazyLoad() {
    }



}