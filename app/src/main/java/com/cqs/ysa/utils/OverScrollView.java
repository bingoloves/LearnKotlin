package com.cqs.ysa.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * OverScrollView主要实现两大效果。
 * 1、监听下滑距离，用来更改标题状态；
 * 2、顶部回弹和底部回弹效果，并能监听顶部回弹、底部回弹（本项目顶部回弹监听可以实现界面切换，底部回弹可以实现动态加载）
 */

public class OverScrollView extends ScrollView {

    private OnScrollListener onScrollListener;//下滑距离监听用到

    private boolean mEnableTopRebound = true;
    private boolean mEnableBottomRebound = true;
    private OnReboundEndListener mOnReboundEndListener;
    private View mContentView;
    private Rect mRect = new Rect();

    public OverScrollView(Context context) {
        super(context);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** after inflating view, we can get the width and height of view */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mContentView == null) return;
        // to remember the location of mContentView
        mRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
    }

    public OverScrollView setOnReboundEndListener(OnReboundEndListener onReboundEndListener){
        this.mOnReboundEndListener = onReboundEndListener;
        return this;

    }

    public OverScrollView setEnableTopRebound(boolean enableTopRebound){
        this.mEnableTopRebound = enableTopRebound;
        return this;
    }

    public OverScrollView setEnableBottomRebound(boolean mEnableBottomRebound){
        this.mEnableBottomRebound = mEnableBottomRebound;
        return this;
    }

    private int lastY;
    private boolean rebound = false;
    private int reboundDirection = 0; //<0 表示下部回弹 >0 表示上部回弹 0表示不回弹

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mContentView == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isScrollToTop() && !isScrollToBottom()){
                    lastY = (int) ev.getY();
                    break;
                }
                //处于顶部或者底部
                int deltaY = (int) (ev.getY() - lastY);
                //deltaY > 0 下拉 deltaY < 0 上拉


                //disable top or bottom rebound
                if ((!mEnableTopRebound && deltaY > 0) || (!mEnableBottomRebound && deltaY < 0)){
                    break;
                }

                int offset = (int) (deltaY * 0.48);
                mContentView.layout(mRect.left, mRect.top + offset, mRect.right, mRect.bottom + offset);
                rebound = true;
                break;

            case MotionEvent.ACTION_UP:
                if (!rebound) break;
                reboundDirection = mContentView.getTop() - mRect.top;
                TranslateAnimation animation = new TranslateAnimation(0, 0, mContentView.getTop(), mRect.top);
                animation.setDuration(300);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mOnReboundEndListener != null){
                            if (reboundDirection > 200){//200表示往下滑200个单位时才能触发onReboundTopComplete
                                mOnReboundEndListener.onReboundTopComplete();
                            }
                            if (reboundDirection < 0){
                                mOnReboundEndListener.onReboundBottomComplete();
                            }
                            reboundDirection = 0;
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mContentView.startAnimation(animation);
                mContentView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
                rebound = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setFillViewport(boolean fillViewport) {
        super.setFillViewport(true); //默认是填充ScrollView 或者再XML布局文件中设置fillViewport属性
    }

    /**
     * 判断当前ScrollView是否处于顶部
     */
    private boolean isScrollToTop(){
        return getScrollY() == 0;
    }

    /**
     * 判断当前ScrollView是否已滑到底部
     */
    private boolean isScrollToBottom(){
        return mContentView.getHeight() <= getHeight() + getScrollY();
    }

    /**
     * listener for top and bottom rebound
     * do your implement in the following methods
     */
    public interface OnReboundEndListener{

        void onReboundTopComplete();

        void onReboundBottomComplete();
    }


    //以下是下滑距离监听
    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 接口对外公开
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回 YSAScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        void onScroll(int scrollY);
    }

}
