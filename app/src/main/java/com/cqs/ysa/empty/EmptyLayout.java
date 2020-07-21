package com.cqs.ysa.empty;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cqs.ysa.R;

/**
 * Created by Administrator on 2020/7/21 0021.
 */

public class EmptyLayout extends FrameLayout implements View.OnClickListener{
    private Context context;
    private View contentView;
    private View emptyLayout;

    private ProgressBar progressBar;
    private LinearLayout emptyLl;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Button emptyButton;
    private OnReloadListener onReloadListener;
    int emptyIconNoData ,emptyIconNoNetwork,emptyIconError;
    String noDataText,noNetworkText,errorText,reloadText= "点击重试";
    boolean hideReloadButton;
    boolean hideTips;

    private static Builder builder = new Builder();

    public EmptyLayout(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        int emptyIconNoData = typedArray.getResourceId(R.styleable.EmptyLayout_el_image_no_data,builder.emptyIconNoData);
        int emptyIconNoNetwork = typedArray.getResourceId(R.styleable.EmptyLayout_el_image_no_network,builder.emptyIconNoNetwork);
        int emptyIconError = typedArray.getResourceId(R.styleable.EmptyLayout_el_image_error,builder.emptyIconError);
        String noDataText = typedArray.getString(R.styleable.EmptyLayout_el_text_no_data);
        String noNetworkText = typedArray.getString(R.styleable.EmptyLayout_el_text_no_network);
        String errorText = typedArray.getString(R.styleable.EmptyLayout_el_text_error);
        String reloadText = typedArray.getString(R.styleable.EmptyLayout_el_text_reload);
        boolean hideReloadButton = typedArray.getBoolean(R.styleable.EmptyLayout_el_hide_reload_btn,true);
        boolean hideTips = typedArray.getBoolean(R.styleable.EmptyLayout_el_hide_tips,true);

        builder.setEmptyIconNoData(emptyIconNoData);
        builder.setEmptyIconNoNetwork(emptyIconNoNetwork);
        builder.setEmptyIconError(emptyIconError);
        builder.setNoDataText(TextUtils.isEmpty(noDataText)?builder.noDataText:noDataText);
        builder.setNoNetworkText(TextUtils.isEmpty(noNetworkText)?builder.noNetworkText:noNetworkText);
        builder.setErrorText(TextUtils.isEmpty(errorText)?builder.errorText:errorText);
        builder.setReloadText(TextUtils.isEmpty(reloadText)?builder.reloadText:reloadText);
        builder.setHideReloadButton(hideReloadButton);
        builder.setHideTips(hideTips);
        typedArray.recycle();
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException(getClass().getSimpleName() + " can host only one direct child");
        }
        build();
    }

    private void build() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
            contentView.setVisibility(GONE);
        }
        emptyLayout = LayoutInflater.from(context).inflate(R.layout.layout_empty_layout,null);
        progressBar = emptyLayout.findViewById(R.id.progressBar);
        emptyLl = emptyLayout.findViewById(R.id.ll_empty);
        emptyImageView = emptyLayout.findViewById(R.id.iv_empty_image);
        emptyTextView = emptyLayout.findViewById(R.id.tv_empty_msg);
        emptyButton = emptyLayout.findViewById(R.id.btn_empty_reload);

        setEmptyIconError(builder.emptyIconError);
        setEmptyIconNoData(builder.emptyIconNoData);
        setEmptyIconNoNetwork(builder.emptyIconError);
        setErrorText(builder.errorText);
        setNoDataText(builder.noDataText);
        setNoNetworkText(builder.noNetworkText);
        setReloadText(builder.reloadText);
        setHideReloadButton(builder.hideReloadButton);
        setHideTips(builder.hideTips);

        showContentView();
        addView(emptyLayout);
    }


    public EmptyLayout setEmptyIconNoData(int emptyIconNoData) {
        this.emptyIconNoData = emptyIconNoData;
        return this;
    }

    public EmptyLayout setEmptyIconNoNetwork(int emptyIconNoNetwork) {
        this.emptyIconNoNetwork = emptyIconNoNetwork;
        return this;
    }

    public EmptyLayout setEmptyIconError(int emptyIconError) {
        this.emptyIconError = emptyIconError;
        return this;
    }

    public EmptyLayout setNoDataText(String noDataText) {
        this.noDataText = noDataText;
        return this;
    }

    public EmptyLayout setNoNetworkText(String noNetworkText) {
        this.noNetworkText = noNetworkText;
        return this;
    }

    public EmptyLayout setErrorText(String errorText) {
        this.errorText = errorText;
        return this;
    }

    public EmptyLayout setReloadText(String reloadText) {
        this.reloadText = reloadText;
        emptyButton.setText(reloadText);
        return this;
    }

    public EmptyLayout setHideReloadButton(boolean hideReloadButton) {
        this.hideReloadButton = hideReloadButton;
        emptyButton.setVisibility(hideReloadButton?GONE:VISIBLE);
        return this;
    }

    public EmptyLayout setHideTips(boolean hideTips) {
        this.hideTips = hideTips;
        emptyTextView.setVisibility(hideTips?GONE:VISIBLE);
        return this;
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public void hideAll(){
        emptyLl.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        if (contentView != null) {
            contentView.setVisibility(GONE);
        }
    }
    public void showEmpty(){
        emptyLl.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        if (contentView != null) {
            contentView.setVisibility(GONE);
        }
        emptyImageView.setImageResource(emptyIconNoData);
        emptyTextView.setText(noDataText);
        emptyButton.setText(reloadText);
        emptyTextView.setVisibility(hideTips?GONE:VISIBLE);
        emptyButton.setVisibility(hideReloadButton?GONE:VISIBLE);
    }
    public void showNetworkError(){
        emptyLl.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        if (contentView != null) {
            contentView.setVisibility(GONE);
        }
        emptyImageView.setImageResource(emptyIconNoNetwork);
        emptyTextView.setText(noNetworkText);
        emptyButton.setText(reloadText);
        emptyTextView.setVisibility(hideTips?GONE:VISIBLE);
        emptyButton.setVisibility(hideReloadButton?GONE:VISIBLE);
    }
    public void showLoading(){
        emptyLl.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        if (contentView != null) {
            contentView.setVisibility(GONE);
        }
    }
    public void showContentView(){
        emptyLl.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        if (contentView != null) {
            contentView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_empty_reload) {
            if (onReloadListener != null) {
                onReloadListener.onReload(view);
            }
        }
    }

    /**
     * 点击重试按钮事件
     */
    public interface OnReloadListener {
        void onReload(View v);
    }

    public static class Builder {
         int emptyIconNoData = R.drawable.ic_empty_no_data;
         int emptyIconNoNetwork = R.drawable.ic_empty_no_data;
         int emptyIconError = R.drawable.ic_empty_no_data;
         String noDataText = "暂无数据";
         String noNetworkText = "网络出问题了";
         String errorText = "加载失败，请稍后重试";
         String reloadText= "点击重试";
         boolean hideReloadButton;
         boolean hideTips;
        public Builder setEmptyIconNoData(@DrawableRes int emptyIconNoData) {
            this.emptyIconNoData = emptyIconNoData;
            return this;
        }

        public Builder setEmptyIconNoNetwork(@DrawableRes int emptyIconNoNetwork) {
            this.emptyIconNoNetwork = emptyIconNoNetwork;
            return this;
        }

        public Builder setEmptyIconError(@DrawableRes int emptyIconError) {
            this.emptyIconError = emptyIconError;
            return this;
        }

        public Builder setNoDataText(String noDataText) {
            this.noDataText = noDataText;
            return this;
        }

        public Builder setNoNetworkText(String noNetworkText) {
            this.noNetworkText = noNetworkText;
            return this;
        }

        public Builder setErrorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public Builder setReloadText(String reloadText) {
            this.reloadText = reloadText;
            return this;
        }

        public Builder setHideReloadButton(boolean hideReloadButton) {
            this.hideReloadButton = hideReloadButton;
            return this;
        }

        public Builder setHideTips(boolean hideTips) {
            this.hideTips = hideTips;
            return this;
        }
    }
}
