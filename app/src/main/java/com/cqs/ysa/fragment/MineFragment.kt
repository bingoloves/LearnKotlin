package com.cqs.ysa.fragment

import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.cqs.ysa.R
import com.cqs.ysa.adapter.listview.CommonAdapter
import com.cqs.ysa.adapter.listview.ViewHolder
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.GridBean
import com.cqs.ysa.tiktok.ActivityTikTok
import com.cqs.ysa.ui.*
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class MineFragment : BaseFragment(){
    var list = ArrayList<GridBean>()
    var list2 = ArrayList<GridBean>()
    override fun contentView(): Int {
        return R.layout.fragment_mine
    }
    override fun initView(view: View?) {
//        var title = arguments?.get("key")
//        Log.e("tag", title as String?)
        initGridList()
        val adapter = object :CommonAdapter<GridBean>(context,R.layout.layout_grid_item,list){
            override fun convert(viewHolder: ViewHolder?, item: GridBean?, position: Int) {
                val titleTv = viewHolder!!.getView<TextView>(R.id.tv_title)
                titleTv.text = item!!.name
                val drawable = resources.getDrawable(item.resId)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                titleTv.setCompoundDrawables(null, drawable, null, null)
            }
        }
        gridView.adapter = adapter
        gridView.setOnItemClickListener { parent, view, position, id ->
            var className = list[position].className
            if (className != null) {
                val intent = Intent(activity,FunctionActivity::class.java)
                intent.putExtra("className",className)
                startActivity(intent)
            } else {
                toast("功能正在开发中...")
            }
        }

        val adapter2 = object :CommonAdapter<GridBean>(context,R.layout.layout_grid_item,list2){
            override fun convert(viewHolder: ViewHolder?, item: GridBean?, position: Int) {
                val titleTv = viewHolder!!.getView<TextView>(R.id.tv_title)
                titleTv.text = item!!.name
                val drawable = resources.getDrawable(item.resId)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                titleTv.setCompoundDrawables(null, drawable, null, null)
            }
        }
        gridView2.adapter = adapter2
        gridView2.setOnItemClickListener { parent, view, position, id ->
            var className = list2[position].className
            if (className != null) {
                val activity = ComponentName(context?.packageName, className)
                val intent = Intent()
                intent.component = activity
                startActivity(intent)
            } else {
                toast("功能正在开发中...")
            }
        }
    }

    private fun initGridList() {
        list.add(GridBean(R.drawable.ic_news,"笑话大全",JokesFragment::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_message,"富文本",RichTextFragment::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_statistic,"可扩展的列表",ExpandableFragment::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_video,"GSYVideo",VideoFragment::class.java.canonicalName))

        list2.add(GridBean(R.drawable.ic_video,"节操视频",JCVideoActivity::class.java.canonicalName))
        list2.add(GridBean(R.drawable.ic_video,"饺子视频", JZVideoActivity::class.java.canonicalName))
        list2.add(GridBean(R.drawable.ic_video,"Vitamio", VitamioActivity::class.java.canonicalName))
        list2.add(GridBean(R.drawable.ic_video,"TikTok", ActivityTikTok::class.java.canonicalName))
        list2.add(GridBean(R.drawable.ic_video,"Yasea", YaseaActivity::class.java.canonicalName))
        list2.add(GridBean(R.drawable.ic_video,"Chat", ChatActivity::class.java.canonicalName))
    }

    override fun lazyLoad() {
    }

}