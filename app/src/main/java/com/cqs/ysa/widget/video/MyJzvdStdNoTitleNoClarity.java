package com.cqs.ysa.widget.video;

import android.content.Context;
import android.util.AttributeSet;

import com.cqs.ysa.R;

public class MyJzvdStdNoTitleNoClarity extends MyJzvdStd {

    public MyJzvdStdNoTitleNoClarity(Context context) {
        super(context);
    }

    public MyJzvdStdNoTitleNoClarity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jzstd_notitle;
    }

}
