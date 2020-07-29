package com.cqs.ysa.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.statusbar.StatusBarUtil

/**
 * Created by bingo on 2020/7/23 0023.
 * 功能包装Activity
 */
class FunctionActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)
//        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        com.cqs.ysa.utils.StatusBarUtil.setTranslucent(this,100)
        var className = intent.extras["className"] as String
        if (!className.isNullOrEmpty()){
            val forName = Class.forName(className)
            var targetFragemnt = forName.newInstance() as Fragment
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fl_container,targetFragemnt)
            //transaction.addToBackStack(null)//设置添加到返回栈
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        finish()
    }
}