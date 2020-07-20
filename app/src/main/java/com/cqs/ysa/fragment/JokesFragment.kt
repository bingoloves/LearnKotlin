package com.cqs.ysa.fragment

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.Jokes
import com.cqs.ysa.bean.Stock
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jokes.*

/**
 * Created by bingo on 2020/7/15 0015.
 * 笑话大全
 */
class JokesFragment : BaseFragment(){
    var list = ArrayList<Jokes.ResultBean.DataBean>()
    var adapter: CommonAdapter<Jokes.ResultBean.DataBean>? = null
    var layoutManager: LinearLayoutManager? = null
    override fun getContentView(): Int {
        return R.layout.fragment_jokes
    }
    override fun initView(view: View) {
        initView()
        getStock()
    }

    override fun lazyLoad() {
    }


    fun initView(){
        layoutManager = LinearLayoutManager(context)
        contentRv.layoutManager = layoutManager
        adapter = object : CommonAdapter<Jokes.ResultBean.DataBean>(context,R.layout.layout_jokes_item,list){
            override fun convert(holder: ViewHolder?, t: Jokes.ResultBean.DataBean?, position: Int) {
                holder!!.setText(R.id.tv_time,t!!.updatetime)
                holder.setText(R.id.tv_content,t.content)
            }
        }
        //设置缓存，避免数据混乱问题
        contentRv.setItemViewCacheSize(10)
        contentRv.adapter = adapter
    }
    fun getStock(){
        RetrofitUtil.get().apiService.getJokeList(1,10,System.currentTimeMillis()/1000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<Jokes>(){
                    override fun onSuccess(data: Jokes?) {
                        if ( data!!.error_code == 0){
                            list = data.result.data as ArrayList<Jokes.ResultBean.DataBean>
                            adapter?.update(list)
                        } else{
                            toast(data.reason)
                        }
                    }

                    override fun onFailure(error: String?) {
                        Log.e("TAG",error)
                    }
                })
    }

}