package com.cqs.ysa.base

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.cqs.ysa.statusbar.StatusBarUtil

/**
 * 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    var activity: Activity? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
    }

    /**
     * 设置黑白屏效果
     */
    protected fun setBlackWhiteScreen() {
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        paint.colorFilter = ColorMatrixColorFilter(cm)
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }


    override fun setContentView(layoutResID: Int) {
        //setBlackWhiteScreen();
        /*设置高刷新率(6.0以后才有的) 部分手机支持高刷新率*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.e("进入");
            // 获取系统window支持的模式
            Display.Mode[] supportedModes = getWindow().getWindowManager().getDefaultDisplay().getSupportedModes();
            // 对获取的模式，基于刷新率的大小进行排序，从小到大排序
            float maxRate = 0;Display.Mode maxMode = supportedModes[0];
            for (Display.Mode supportedMode : supportedModes) {
                float refreshRate = supportedMode.getRefreshRate();
                if (refreshRate>maxRate){
                    maxRate = refreshRate;
                    maxMode = supportedMode;
                }
            }
            LogUtils.e("maxRate = "+maxRate);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.preferredDisplayModeId = maxMode.getModeId();
            getWindow().setAttributes(layoutParams);
        }*/
        super.setContentView(layoutResID)
        initStatusBar()
    }

    override fun getResources(): Resources {
        //return AdaptScreenUtils.adaptWidth(super.getResources(), 376);//屏幕适配关键,按照设计图比例来，pt 适配方案
        return super.getResources()
    }

    private fun initStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        //setStatusBarDark();
    }

    /**
     * 是否全屏显示
     */
    protected fun setFullScreen() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
    }

    protected fun setStatusBarDark() {
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }
    }

    /**
     * 简单统一调用自定义 Toast
     */
    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     * @param isFinish 是否关闭当前页
     */
    @JvmOverloads
    fun startActivity(clz: Class<*>, isFinish: Boolean = false) {
        startActivity(Intent(this, clz))
        if (isFinish) finish()
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     * @param isFinish 是否关闭当前页
     */
    @JvmOverloads
    fun startActivity(clz: Class<*>, bundle: Bundle?, isFinish: Boolean = false) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (isFinish) finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
