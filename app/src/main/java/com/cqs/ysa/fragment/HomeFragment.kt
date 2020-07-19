package com.cqs.ysa.fragment

import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.TwoActivity
import com.cqs.ysa.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by bingo on 2020/7/15 0015.
 */
class HomeFragment: BaseFragment() {

    override fun initView(view: View) {
        var title = arguments?.get("key")
        Log.e("tag",title as String?)
        testBtn.text = " bingo "
        testBtn.setOnClickListener { view ->
            startActivity<TwoActivity>("key" to "bingo")
        }
    }

    override fun lazyLoad() {

    }

    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

}