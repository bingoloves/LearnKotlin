package com.cqs.ysa.fragment

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cqs.ysa.R
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.adapter.recyclerview.utils.GridDivider
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.News
import com.cqs.ysa.bean.ThumbViewInfo
import com.cqs.ysa.bean.TopNews
import com.cqs.ysa.retrofit.ApiService
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.cqs.ysa.retrofit.RetrofitUtil2
import com.cqs.ysa.ui.WebActivity
import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.previewlibrary.GPreviewBuilder
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class NewsFragment : BaseFragment(){
    //初始化，有add，remove方法的集合
    var list = ArrayList<News>()
    var adapter: CommonAdapter<News>? = null
    var layoutManager: LinearLayoutManager? = null

    override fun contentView(): Int {
        return R.layout.fragment_news
    }

    override fun initView(view: View?) {
        var title = arguments?.get("key")
        XLog.e(title as String?)
        initView()
        getNews()
    }

    override fun lazyLoad() {
    }


    fun initView(){
        layoutManager = LinearLayoutManager(context)
        contentRv.layoutManager = layoutManager
        adapter = object : CommonAdapter<News>(context,R.layout.layout_news_item,list){
            override fun convert(holder: ViewHolder?, t: News?, position: Int) {
                val gridView = holder!!.getView<RecyclerView>(R.id.rv_images)
                holder.setText(R.id.tv_title,t!!.title)
                holder.setText(R.id.tv_date,t.date)
                holder.setText(R.id.tv_category,t.category)
                val gridLayoutManager = GridLayoutManager(context,3)
                gridView.layoutManager = gridLayoutManager
                val images = ArrayList<String>()
                if (!t.thumbnail_pic_s.isNullOrEmpty()) images.add(t.thumbnail_pic_s)
                if (!t.thumbnail_pic_s02.isNullOrEmpty()) images.add(t.thumbnail_pic_s02)
                if (!t.thumbnail_pic_s03.isNullOrEmpty()) images.add(t.thumbnail_pic_s03)
                val gridAdapter = object :CommonAdapter<String>(context,R.layout.layout_news_image_item,images){
                    override fun convert(viewHolder: ViewHolder?, path: String?, pos: Int) {
                        var imageView = viewHolder!!.getView<ImageView>(R.id.iv_image)
                        if(path != imageView.getTag(R.id.iv_image)){
                            imageView.setTag(R.id.iv_image,path)
                            Glide.with(context!!).load(path).apply(RequestOptions.bitmapTransform(RoundedCorners(8)).skipMemoryCache(false)).into(imageView)
                        }
                        imageView.setOnClickListener{
                           var thumbViewInfoList = computeBoundsBackward(gridLayoutManager,images)
                            GPreviewBuilder.from(fragment!!)
                                    .setData(thumbViewInfoList)
                                    .setCurrentIndex(pos)
                                    .setSingleFling(true)//是否在黑屏区域点击返回
                                    .setDrag(false)//是否禁用图片拖拽返回
                                    .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                                    .start()//启动
                        }
                    }
                }
                gridView.addItemDecoration(GridDivider(context,3,10))
                gridView.adapter = gridAdapter
                holder.itemView.setOnClickListener {
                    startActivity<WebActivity>("url" to  t.url)
                }
            }
        }
        //设置缓存，避免数据混乱问题
        contentRv.setItemViewCacheSize(100)
        contentRv.adapter = adapter

//        smartRefresh.setRefreshHeader(ClassicsHeader(context).setLastUpdateTime(Date()))
//        smartRefresh.setRefreshFooter(ClassicsFooter(context))
        smartRefresh.setOnRefreshListener {
            smartRefresh.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }
        smartRefresh.setOnLoadMoreListener {
            smartRefresh.finishLoadMore(2000/*,false*/)//传入false表示刷新失败
        }
    }

    fun getNews(){
        /*RetrofitUtil.get().apiService.getNews("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<TopNews>(){
                    override fun onSuccess(data: TopNews?) {
                       if ( data!!.error_code == 0){
                           list = data.result.data as ArrayList<News>
                           adapter?.update(list)
                       } else{
                           toast(data.reason)
                       }
                    }

                    override fun onFailure(error: String?) {
                        Log.e("TAG",error)
                    }
                })*/
        RetrofitUtil2.getService(ApiService.BASE_URL,ApiService::class.java).getNews("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<TopNews>(){
                    override fun onSuccess(data: TopNews?) {
                        if ( data!!.error_code == 0){
                            list = data.result.data as ArrayList<News>
                            XLog.e(Gson().toJson(list))
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

    /**
     * 重新计算当前图片位置
     */
    fun computeBoundsBackward(layoutManager: GridLayoutManager,images:ArrayList<String>):ArrayList<ThumbViewInfo>{
        var thumbViewInfoList = ArrayList<ThumbViewInfo>()
        for (data in images){
            val item = ThumbViewInfo(data)
            thumbViewInfoList.add(item)
        }
        var firstCompletelyVisiblePos = layoutManager.findFirstVisibleItemPosition()
        for (i in firstCompletelyVisiblePos until thumbViewInfoList.size) {
            val itemView = layoutManager.findViewByPosition(i)
            val bounds = Rect()
            if (itemView != null) {
                val thumbView = itemView.findViewById<ImageView>(R.id.iv_image)
                thumbView.getGlobalVisibleRect(bounds)
            }
            thumbViewInfoList.get(i).bounds = bounds
        }
        return thumbViewInfoList
    }
}