package com.cqs.ysa.tiktok;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.cqs.ysa.R;
import com.cqs.ysa.widget.video.JzvdStdTikTok;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;

public class TikTokRecyclerViewAdapter extends RecyclerView.Adapter<TikTokRecyclerViewAdapter.MyViewHolder> {

    public static final String TAG = "AdapterTikTokRecyclerView";
    String[] videoList;
    private Context context;

    public TikTokRecyclerViewAdapter(Context context) {
        this.context = context;
        videoList = context.getResources().getStringArray(R.array.news);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.layout_tiktok_item, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);

        JZDataSource jzDataSource = new JZDataSource(videoList[position],"视频"+position);
        jzDataSource.looping = true;
        holder.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL);
        Glide.with(holder.jzvdStd.getContext()).load("http://jzvd-pic.nathen.cn/jzvd-pic/bd7ffc84-8407-4037-a078-7d922ce0fb0f.jpg").into(holder.jzvdStd.posterImageView);
    }

    @Override
    public int getItemCount() {
        return videoList.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStdTikTok jzvdStd;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.videoplayer);
        }
    }

}
