package com.cqs.ysa.fragment

import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment
import com.elvishew.xlog.XLog
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class HomeFragment: BaseFragment() {
    override fun contentView(): Int {
        return R.layout.fragment_home
    }
    override fun initView(view: View?) {
        var title = arguments?.get("key")
        XLog.e(title as String?)
        testBtn.text = " bingo "
        testBtn.setOnClickListener { view ->
            //startActivity<DrivingQuestionsActivity>("key" to "bingo")
        }
    }

    override fun lazyLoad() {

    }



}