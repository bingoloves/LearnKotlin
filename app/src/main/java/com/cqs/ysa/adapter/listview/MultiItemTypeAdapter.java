package com.cqs.ysa.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiItemTypeAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected Map<Integer,ViewHolder> cacheMap;
    private ItemViewDelegateManager mItemViewDelegateManager;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.cacheMap = new HashMap<>();
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent,false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        convert(viewHolder, getItem(position), position);
        cacheMap.put(position,viewHolder);
        return viewHolder.getConvertView();
    }

    protected void convert(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取对应位置的视图Holder
     * @param position
     * @return
     */
    public ViewHolder getViewHolder(int position) {
        return cacheMap.get(position);
    }

    public Map<Integer, ViewHolder> getCacheMap() {
        return cacheMap;
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
    public void update(List<T> data){
        if (data!=null){
            mDatas.clear();
            mDatas = data;
        }
        notifyDataSetChanged();
    }
}
