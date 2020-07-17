package com.cqs.ysa.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cqs.ysa.adapter.recyclerview.base.ItemViewDelegate;
import com.cqs.ysa.adapter.recyclerview.base.ItemViewDelegateManager;
import com.cqs.ysa.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected Map<Integer,ViewHolder> cacheMap;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        cacheMap = new HashMap<>();
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
        cacheMap.put(position,holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }
    /**
     * 获取对应位置的视图Holder
     * @param position
     * @return
     */
    public ViewHolder getViewHolder(int position) {
        return cacheMap.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public void add(T data){
        if (data != null){
            mDatas.add(mDatas.size(),data);
        }
        notifyDataSetChanged();
    }
    public void add(List<T> data){
        if (data!=null){
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void remove(T data){
        if (data != null){
            mDatas.remove(data);
        }
        notifyDataSetChanged();
    }
    public void remove(int position){
        mDatas.remove(position);
        notifyDataSetChanged();
    }
    public void removeLast(){
        if (mDatas.size()>0){
            mDatas.remove(mDatas.size()-1);
            notifyDataSetChanged();
        }
    }
    /**
     * 数据清除
     */
    public void clear(){
        mDatas.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 数据更新
     * @param list
     */
    public void update(List<T> list){
        if (list!=null){
            mDatas = list;
        }
        this.notifyDataSetChanged();
    }
}
