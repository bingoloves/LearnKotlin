package com.cqs.ysa

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LevelListDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.bumptech.glide.Glide
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_two.*

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
                var imageView = holder!!.getView<ImageView>(R.id.image)
                var radioGroup = holder.getView<RadioGroup>(R.id.radioGroup)

                holder.setText(R.id.titleTv,"第${position+1}题 "+t!!.question)
                holder.setText(R.id.radio1,t.item1)
                holder.setText(R.id.radio2,t.item2)
                holder.setText(R.id.radio3,t.item3)
                holder.setText(R.id.radio4,t.item4)
                holder.setText(R.id.tv_answer,"本题答案：${t.answer}")
                holder.setText(R.id.tv_explains,"答案解析："+ Html.fromHtml(t.explains))
//                var explainsTv = holder.getView<TextView>(R.id.tv_explains)
//                explainsTv.text = "答案解析："+ Html.fromHtml(t.explains)
//                explainsTv.text = getClickableSpan()//测试自定义可点击富文本
//                explainsTv.setMovementMethod(LinkMovementMethod.getInstance())//设置该句使文本的超连接起作用

                holder.setVisible(R.id.radio1,!t.item1.isNullOrEmpty())
                holder.setVisible(R.id.radio2,!t.item2.isNullOrEmpty())
                holder.setVisible(R.id.radio3,!t.item3.isNullOrEmpty())
                holder.setVisible(R.id.radio4,!t.item4.isNullOrEmpty())
                holder.setVisible(R.id.image,!t.url.isNullOrEmpty())
                Glide.with(activity).load(t.url).apply(RequestOptions.bitmapTransform(RoundedCorners(2))).into(imageView)
                for (i in 0 until radioGroup.childCount) {
                    var radioBtn = radioGroup.getChildAt(i) as RadioButton
                    radioBtn.isChecked = t.answer.toInt() == i+1
                    radioBtn.isEnabled = false
                }
                imageView.setOnClickListener {
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
        //设置缓存，避免数据混乱问题
        contentRv.setItemViewCacheSize(100);
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

    //设置超链接文字
    private fun getClickableSpan(): SpannableString? {
        val spanStr = SpannableString("感谢您使用错题本，当您使用我们的软件时，我们会基于产品服务场景的需求，收集和使用您的部分个人信息。请您仔细阅读《错题本隐私政策》和《用户协议》了解并确定我们对您个人信息的处理原则")
        //设置文字的单击事件
        spanStr.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@TwoActivity, MainActivity::class.java))
            }
        }, 55, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置文字的前景色
        spanStr.setSpan(ForegroundColorSpan(Color.RED), 55, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置下划线文字
        spanStr.setSpan(UnderlineSpan(), 55, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置文字的单击事件
        spanStr.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@TwoActivity, MainActivity::class.java))
            }
        }, 65, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置文字的前景色
        spanStr.setSpan(ForegroundColorSpan(Color.BLUE), 65, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanStr
    }
}
