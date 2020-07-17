package com.cqs.ysa.adapter.recyclerview.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by bingo on 2020/6/11 0011.
 */

public class GridDivider extends RecyclerView.ItemDecoration {
    private Context context;
    private int column = 4;//默认列数
    private int space;  //位移间距
    public GridDivider(Context context, int column, int space) {
        this.space = space;
        this.column = column;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) %column == 0) {
            outRect.left = 0; //第一列左边贴边
        } else {
            if (parent.getChildAdapterPosition(view) %column == 1) {
                outRect.left = space;//第二列移动一个位移间距
            } else {
                outRect.left = space * 2;//由于第二列已经移动了一个间距，所以第三列要移动两个位移间距就能右边贴边，且item间距相等
            }
        }
        if (parent.getChildAdapterPosition(view) >= column) {
            outRect.top = dpToPx(context, 10);
        } else {
            outRect.top = 0;
        }
    }
    private int dpToPx(Context context,int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}