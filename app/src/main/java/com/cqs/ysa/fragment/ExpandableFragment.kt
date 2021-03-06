package com.cqs.ysa.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.widget.expandable.ExpandableViewHoldersUtil
import kotlinx.android.synthetic.main.fragment_expandable.*

/**
 * Created by bingo on 2020/7/15 0015.
 */
class ExpandableFragment : BaseFragment() {
    override fun contentView(): Int {
        return R.layout.fragment_expandable
    }
    override fun initView(view: View?) {
        ExpandableViewHoldersUtil.getInstance().init().setNeedExplanedOnlyOne(false)
        contentRv.layoutManager = LinearLayoutManager(context)
        contentRv.adapter = MyAdapter(context)
    }

    override fun lazyLoad() {

    }

    class MyAdapter(ctx: Context?) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        var context = ctx
        var keepOne: ExpandableViewHoldersUtil.KeepOneHolder<MyAdapter.ViewHolder>? = null
        override fun getItemCount(): Int {
            return 20
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_expandable_item, viewGroup, false)
            keepOne = ExpandableViewHoldersUtil.getInstance().keepOneHolder as ExpandableViewHoldersUtil.KeepOneHolder<ViewHolder>?
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.tvTitle.text = "中美经贸磋商 po=" + position
            keepOne?.bind(viewHolder, position)
            viewHolder.tvTitle.setOnClickListener {
                //                    if(ExpandableViewHoldersUtil.isExpaned(position)){
                //                        viewHolder.contentTv.setMaxLines(3);
                //                    }else {
                //                        viewHolder.contentTv.setMaxLines(100);
                //                    }
                keepOne?.toggle(viewHolder)
            }
            viewHolder.lvArrorwBtn.setOnClickListener { keepOne?.toggle(viewHolder) }
        }
         class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ExpandableViewHoldersUtil.Expandable {

             override fun getExpandView(): View {
                 return lvLinearlayout
             }
             var tvTitle: TextView
             var arrowImage: ImageView
             var lvArrorwBtn: LinearLayout
             var lvLinearlayout: LinearLayout
             var contentTv: TextView
             init {
                tvTitle = itemView.findViewById(R.id.item_user_concern_title)
                lvLinearlayout = itemView.findViewById(R.id.item_user_concern_link_layout)
                lvArrorwBtn = itemView.findViewById(R.id.item_user_concern_arrow)
                arrowImage = itemView.findViewById(R.id.item_user_concern_arrow_image)
                contentTv = itemView.findViewById(R.id.item_user_concern_link_text)
                lvLinearlayout.visibility = View.GONE
                lvLinearlayout.alpha = 0f
             }

            override fun doCustomAnim(isOpen: Boolean) {
                if (isOpen) {
                    ExpandableViewHoldersUtil.getInstance().rotateExpandIcon(arrowImage, 180f, 0f)
                } else {
                    ExpandableViewHoldersUtil.getInstance().rotateExpandIcon(arrowImage, 0f, 180f)
                }
            }
        }
    }
}

