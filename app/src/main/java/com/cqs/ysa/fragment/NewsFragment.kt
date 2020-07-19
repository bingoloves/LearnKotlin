package com.cqs.ysa.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cqs.ysa.R
import com.cqs.ysa.TwoActivity
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.adapter.recyclerview.utils.GridDivider
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.*
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.cqs.ysa.ui.WebActivity
import com.cqs.ysa.utils.PreviewImageLoader
import com.google.gson.JsonObject
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.ZoomMediaLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_two.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import org.json.JSONObject

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
    //初始化，有add，remove方法的集合
    var list = ArrayList<News>()
    var adapter: CommonAdapter<News>? = null
    var layoutManager: LinearLayoutManager? = null

    override fun initView(view: View) {
        var title = arguments?.get("key")
        Log.e("tag", title as String?)
        ZoomMediaLoader.getInstance().init(PreviewImageLoader())
        initView()
        getRequestData()
    }

    override fun lazyLoad() {
    }

    override fun getContentView(): Int {
        return R.layout.fragment_news
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
                        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(RoundedCorners(8))).into(imageView)
                        imageView.setOnClickListener{
                           var thumbViewInfoList = computeBoundsBackward(gridLayoutManager,images)
                            GPreviewBuilder.from(fragment)
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
    }

    fun getRequestData(){
        RetrofitUtil.get().apiService.news
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