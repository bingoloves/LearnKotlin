package com.cqs.ysa.fragment

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.Stock
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.elvishew.xlog.XLog
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_stock.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class StockFragment : BaseFragment(){
    //初始化，有add，remove方法的集合
    var list = ArrayList<Stock.ResultBean.DataBean>()
    var adapter: CommonAdapter<Stock.ResultBean.DataBean>? = null
    var layoutManager: LinearLayoutManager? = null

    override fun contentView(): Int {
        return R.layout.fragment_stock
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
        adapter = object : CommonAdapter<Stock.ResultBean.DataBean>(context,R.layout.layout_stock_item,list){
            override fun convert(holder: ViewHolder?, t: Stock.ResultBean.DataBean?, position: Int) {
                holder!!.setText(R.id.tv_title,t!!.name)
                holder.setText(R.id.tv_code,t.symbol)
                holder.setText(R.id.tv_ticktime,t.ticktime)
                holder.setText(R.id.tv_trade,"最新价：${t.trade}")
                holder.setText(R.id.tv_pricechange,"涨跌额：${t.pricechange}")
                holder.setText(R.id.tv_changepercent,"涨跌幅：${t.changepercent}")
                holder.setText(R.id.tv_buy,"买入：${t.buy}")
                holder.setText(R.id.tv_sell,"卖出：${t.sell}")
                holder.setText(R.id.tv_settlement,"昨收：${t.settlement}")
                holder.setText(R.id.tv_open,"今开：${t.open}")
                holder.setText(R.id.tv_high,"最高：${t.high}")
                holder.setText(R.id.tv_low,"最低：${t.low}")
                holder.setText(R.id.tv_volume,"成交额：${t.volume}")
                holder.setText(R.id.tv_amount,"成交量：${t.amount}")
            }
        }
        //设置缓存，避免数据混乱问题
        contentRv.setItemViewCacheSize(100)
        contentRv.adapter = adapter
//        smartRefresh.setRefreshHeader(ClassicsHeader(context))
//        smartRefresh.setRefreshFooter(ClassicsFooter(context))
        smartRefresh.setOnRefreshListener {
            emptyLayout.postDelayed({
                smartRefresh.finishRefresh(true)
            },2000)
        }
        smartRefresh.setOnLoadMoreListener {
            smartRefresh.finishLoadMore(2000)
        }
    }
    fun getStock(){
        RetrofitUtil.get().apiService.getStock(1,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<Stock>(){
                    override fun onSuccess(data: Stock?) {
                        if ( data!!.error_code == 0){
                            list = data.result.data as ArrayList<Stock.ResultBean.DataBean>
                            adapter?.update(list)
                        } else{
                            toast(data.reason)
                        }
                    }

                    override fun onFailure(error: String?) {
                        XLog.e(error)
                    }
                })
    }
}