package com.cqs.ysa.fragment

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.cqs.ysa.R
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.Jokes
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jokes.*
import java.util.*

/**
 * Created by bingo on 2020/7/15 0015.
 * 笑话大全
 */
class JokesFragment : BaseFragment(){
    var list = ArrayList<Jokes.ResultBean.DataBean>()
    var adapter: CommonAdapter<Jokes.ResultBean.DataBean>? = null
    var layoutManager: LinearLayoutManager? = null
    //分页查询的参数设置
    var page = 1       //当前页
    var pageSize = 10  //分页大小
    var nextPage = true//是否有下一页
    var loadEnd = true //是否加载完成
    var isLoadMore = false//是否加载更多
    override fun contentView(): Int {
        return R.layout.fragment_jokes
    }
    override fun initView(view: View?) {
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
//        contentRv.itemAnimator =
        smartRefresh.setRefreshHeader(ClassicsHeader(context).setLastUpdateTime(Date()))
        smartRefresh.setRefreshFooter(ClassicsFooter(context))
        smartRefresh.setOnRefreshListener {
            page =1
            isLoadMore = false
            getStock()
        }
        smartRefresh.setOnLoadMoreListener {
            if (nextPage && loadEnd){
                page++
                isLoadMore = true
                getStock()
            } else{
                toast("没有更多数据！")
            }
        }
    }
    fun getStock(){
        if (!loadEnd) return //上次请求未完成不再重复请求
        loadEnd = false
        RetrofitUtil.get().apiService.getJokeList(page,pageSize,System.currentTimeMillis()/1000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<Jokes>(){
                    override fun onSuccess(data: Jokes?) {
                        if (data!!.error_code == 0){
                            val temp = data.result?.data as ArrayList<Jokes.ResultBean.DataBean>
                            if(!isLoadMore)list.clear()
                            list.addAll(list.size,temp)
                            adapter!!.notifyDataSetChanged()
//                            swipe_target.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
//                            swipe_target.scheduleLayoutAnimation()
                            nextPage = temp.size <= pageSize
                        } else{
                            var msg = data.reason?:return
                            toast(msg)
                        }
                    }

                    override fun onFailure(error: String?) {
                        Log.e("TAG",error)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        loadEnd = true
                        if(list.size>0 ) emptyLayout.showContentView() else emptyLayout.showLoading()
                        smartRefresh?.finishLoadMore(true)
                        smartRefresh?.finishRefresh(true)
                    }
                })
    }

}