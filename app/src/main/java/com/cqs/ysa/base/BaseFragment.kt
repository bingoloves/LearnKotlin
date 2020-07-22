package com.cqs.ysa.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

abstract class BaseFragment : Fragment() {

    private var mRoot: View? = null
    protected var fragment: Fragment? = null
    /**
     * 是否执行了lazyLoad方法
     */
    private var isLazyLoaded = false
    /**
     * 是否创建了View
     */
    private var isCreateView: Boolean = false
    private var inflater: LayoutInflater? = null
    private var container: ViewGroup? = null
    private var savedInstanceState: Bundle? = null
    /**
     * 当从另一个activity回到fragment所在的activity
     * 当fragment回调onResume方法的时候，可以通过这个变量判断fragment是否可见，来决定是否要刷新数据
     */
    var isVisibleForUser: Boolean = false

    /**
     * 用于设置当前页面布局
     * @return
     */
    protected abstract fun contentView(): Int

    //====================================针对ViewPager嵌套 ↓=======================================
    /*
     * 此方法在viewpager嵌套fragment时会回调
     * 查看FragmentPagerAdapter源码中instantiateItem和setPrimaryItem会调用此方法
     * 在所有生命周期方法前调用
     * 这个基类适用于在viewpager嵌套少量的fragment页面
     * 该方法是第一个回调，可以将数据放在这里处理（viewpager默认会预加载一个页面）
     * 只在fragment可见时加载数据，加快响应速度
     * */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            onVisible()
        } else {
            onInvisible()
        }
    }
    //====================================针对ViewPager嵌套 ↑=======================================
    /**
     * 在Fragment第一次可见加载以后，每次Fragment滑动/切换当前可见的时候会回调这个方法，
     * 子类可以重写这个方法做数据刷新操作
     */
    private fun refreshLoad() {}

    /**
     * 因为Fragment是缓存在内存中，所以可以保存mRoot ，防止view的重复加载
     * 与FragmentPagerAdapter 中destroyItem方法取消调用父类的效果是一样的，可以任选一种做法 推荐第二种
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRoot == null) {
            mRoot = inflater.inflate(contentView(), container, false)
            this.inflater = inflater
            this.container = container
            this.savedInstanceState = savedInstanceState
            isCreateView = true
            fragment = this
            //initView(mRoot);
            initLazyLoad()
        }
        return mRoot
    }

    /**
     * kotlin需要在此方法中 才能获取控件id 不然报KotlinNullPointerException,这是因为当前的view并没有返回
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(mRoot)
    }

    /**
     * 初始化懒加载 数据
     * getUserVisibleHint() 是否对用户可见 对通过 show/hide 方式 始终为true
     */
    private fun initLazyLoad() {
        if (!isLazyLoaded && isCreateView && userVisibleHint) {
            isLazyLoaded = true
            lazyLoad()
        }
    }

    protected fun onVisible() {
        isVisibleForUser = true
        if (isLazyLoaded) {
            refreshLoad()
        }
    }

    protected fun onInvisible() {
        isVisibleForUser = false
    }

    /**
     * 通过hide 和 show 显示或者隐藏Fragment时会触发此方法
     * @param hidden
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isVisibleForUser = !hidden
        if (!hidden && isLazyLoaded) {
            refreshLoad()
        }
    }

    /**
     * 用于初始化相关组件
     * @param view
     */
    protected abstract fun initView(view: View?)

    protected fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * fragment第一次创建的时候并且页面不可见的时候 会调此方法
     */
    protected abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()
        container = null
        savedInstanceState = null
        mRoot = null
    }
}