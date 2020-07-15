package com.cqs.ysa.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment

/**
 * Created by bingo on 2020/7/15 0015.
 */
class NewsFragment : BaseFragment(){
    fun newInstance(title:String): NewsFragment {
        val args = Bundle()
        args.putString("key",title)
        val fragment = NewsFragment()
        fragment.arguments = args
        return fragment
    }
    override fun initView(view: View) {
        var title = arguments?.get("key")
        Log.e("tag", title as String?)
        //testBtn.text = title
    }

    override fun lazyLoad() {
    }

    override fun getContentView(): Int {
        return R.layout.fragment_news
    }

}