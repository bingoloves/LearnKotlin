package com.cqs.ysa

import android.graphics.Rect
import android.os.Bundle
import android.os.Parcel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cqs.ysa.adapter.recyclerview.CommonAdapter
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.bean.DriverQuestion
import com.cqs.ysa.bean.Question
import com.cqs.ysa.bean.ThumbViewInfo
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.cqs.ysa.utils.PreviewImageLoader
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.ZoomMediaLoader
import com.previewlibrary.enitity.IThumbViewInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_two.*
import kotlinx.android.synthetic.main.layout_question_item.*
import org.jetbrains.anko.find

class TwoActivity : BaseActivity() {
    //初始化，有add，remove方法的集合
    var list = ArrayList<Question>()
    var adapter:CommonAdapter<Question>? = null
    var thumbViewInfoList = ArrayList<ThumbViewInfo>()
    var layoutManager:LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        ZoomMediaLoader.getInstance().init(PreviewImageLoader())
        initView()
        getRequestData()
    }
    fun initView(){
        layoutManager = LinearLayoutManager(this)
        contentRv.layoutManager = layoutManager
        adapter = object : CommonAdapter<Question>(this,R.layout.layout_question_item,list){
            override fun convert(holder: ViewHolder?, t: Question?, position: Int) {
                Log.e("TAG",t?.question)
                holder?.setText(R.id.titleTv,t?.question)
                holder?.setText(R.id.radio1,t?.item1)
                //holder?.setVisible(R.id.radio1,t?.item1?:false)
                holder?.setText(R.id.radio2,t?.item2)
                holder?.setText(R.id.radio3,t?.item3)
                holder?.setText(R.id.radio4,t?.item4)
                var imaegeView = holder?.getView<ImageView>(R.id.image)
                Glide.with(activity).load(t?.url).apply(RequestOptions.bitmapTransform(RoundedCorners(2))).into(imaegeView!!)
//                imaegeView.setOnClickListener(object : View.OnClickListener{
//                    override fun onClick(v: View?) {
//
//                    }
//                })
                imaegeView.setOnClickListener {
                    computeBoundsBackward(layoutManager!!.findFirstVisibleItemPosition())
                    GPreviewBuilder.from(activity)
                            .setData(thumbViewInfoList)
                            .setCurrentIndex(position)
                            .setSingleFling(true)//是否在黑屏区域点击返回
                            .setDrag(false)//是否禁用图片拖拽返回
                            .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                            .start()//启动
                }
            }
        }
        contentRv.adapter = adapter
    }

    fun getRequestData(){
        RetrofitUtil.get().apiService.questions
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<DriverQuestion>(){
                    override fun onSuccess(data: DriverQuestion?) {
                        list = data?.result as ArrayList<Question>
                        Log.e("TAG","size = "+list.size)
                        updateThumbView(list)
                        adapter?.update(list)
                    }

                    override fun onFailure(error: String?) {
                        Log.e("TAG",error)
                    }
                })
    }

    /**
     * 更新对应的数据
     */
    fun updateThumbView(datas:ArrayList<Question>){
        for (data in datas){
            val item = ThumbViewInfo(data.url)
            val bounds = Rect()
            item.setBounds(bounds)
            thumbViewInfoList.add(item)
        }
    }

    /**
     * 重新计算当前图片位置
     */
    fun computeBoundsBackward(firstCompletelyVisiblePos:Int){
        for (i in firstCompletelyVisiblePos until thumbViewInfoList.size) {
            val itemView = layoutManager!!.findViewByPosition(i)
            val bounds = Rect()
            if (itemView != null) {
                val thumbView = itemView.findViewById<ImageView>(R.id.image)
                thumbView.getGlobalVisibleRect(bounds)
            }
            thumbViewInfoList.get(i).bounds = bounds
        }
    }
}