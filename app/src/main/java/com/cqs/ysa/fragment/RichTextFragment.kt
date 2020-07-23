package com.cqs.ysa.fragment

import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_rich_text.*

/**
 * Created by bingo on 2020/7/23 0023.
 */

class RichTextFragment:BaseFragment(){
    val htmlText = "<div class=\"content\" data-a-686478ee><div data-click=\"{&quot;page&quot;:&quot;news&quot;}\"class=\"wa-news-content-container c-response-content c-response-margin-top20\"data-a-03c92b82 data-a-686478ee><div class=\"wa-news-content-container-textc-line-bottom c-response-content-left\" data-a-03c92b82><div class=\"wa-news-content-title\"data-a-03c92b82>\n" +
    "            临阵换帅对上抠门足协,西班牙与葡萄牙一战,场外因素更吸睛\n" +
    "        </div> <div class=\"wa-news-content-baijiahao\" data-a-03c92b82><div class=\"avatar\"style=\"background:url(http://timg01.bdimg.com/timg?pacompress&imgtype=0&sec=1439619614&di=8a54bb99aef8b192debf0b251be173be&quality=90&size=b870_10000&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2Fb767998c68ac1c9aec747491b0f5e1c5.jpeg) center / cover no-repeat;border-radius:50%;\" data-a-03c92b82></div> <div class=\"info\" data-a-03c92b82><div class=\"title\" data-a-03c92b82>篮球快报</div> <div class=\"msg\" data-a-03c92b82><span class=\"seperator\" data-a-03c92b82><span data-a-03c92b82>百家号</span></span><span style=\"margin-left:.08rem;\" data-a-03c92b82>06-15 15:01</span></div></div></div><div class=\"wa-news-content-body\" data-a-03c92b82><div data-a-03c92b82><div class=\"wa-news-content-body-text c-font-big\" data-a-03c92b82>\n" +
    "                    如果问世界杯小组赛期间最劲爆的对决是哪一组？估计十之八九的中立球迷在期待着西班牙与葡萄牙之间的双牙之争。这两支夺冠的热门球队，都是出自伊比利亚半岛，真可谓是同根同源。两支球队也各有特色，西班牙群星云集，而葡萄牙则拥有“绝代双骄”之一的C罗。\n" +
    "                </div></div><div data-a-03c92b82><div class=\"wa-news-content-body-img\"style=\"margin-top:-.08rem;\" data-a-03c92b82><img src=\"http://pic.rmb.bdstatic.com/0bc44ea0566f66f5c269b116f6b3300c.jpeg@wm_2,t_55m+5a625Y+3L+evrueQg+W/q+aKpQ==,fc_ffffff,ff_U2ltSGVp,sz_13,x_9,y_9\"width=\"100%\" data-a-03c92b82></div></div><div data-a-03c92b82><div class=\"wa-news-content-body-text c-font-big\" data-a-03c92b82>\n" +
    "                    西班牙的阵容堪称豪华：德赫亚一夫当关；卡尔哈尔、阿尔巴双翼齐飞；皮克看穿一切；拉莫斯固若金汤；伊涅斯塔老骥伏枥；布斯克斯左右逢源；大卫席尔瓦人球合一；迭戈科斯塔宛如战兽……还有伊斯科、阿森西奥等年轻俊杰，随时能为球队提供活力。\n"+"</div></div><div data-a-03c92b82><div class=\"wa-news-content-body-text c-font-big\"data-a-03c92b82>\n" +
    "而葡萄牙则是C罗一个人的球队。C罗身为当世最好的两位超巨之一，无论何时何地，他都有着摧毁对手防线的魔力。只要给他一点点的机会或者是空隙，他都有办法杀死比赛的悬念。\n"+
    "                </div></div><div data-a-03c92b82><div class=\"wa-news-content-body-img\" style=\"margin-top:-.08rem;\" data-a-03c92b82><img src=\"http://pic.rmb.bdstatic.com/3a334c1a7f9f715ffcf6e5488e0195ac.jpeg@wm_2,t_55m+5a625Y+3L+evrueQg+W/q+aKpQ==,fc_ffffff,ff_U2ltSGVp,sz_14,x_9,y_9\"width=\"100%\" data-a-03c92b82></div></div><div data-a-03c92b82><div class=\"wa-news-content-body-text c-font-big\" data-a-03c92b82>\n" +"                    最佳球队对阵最佳球员，这种比赛放在任何时代都是焦点中的焦点。不过，就是这样的一场堪称火星撞地球的较量，却被双方的场外因素给吸了睛。"
    override fun contentView(): Int {
        return R.layout.fragment_rich_text
    }

    override fun initView(view: View?) {
        //在第一次调用RichText之前先调用RichText.initCacheDir()方法设置缓存目录，不设置会报错
        //RichText.initCacheDir(context)
        RichText.from(htmlText).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(richTv)
    }

    override fun lazyLoad() {}

    override fun onDestroy() {
        super.onDestroy()
        //结束时清空内容
        RichText.clear(this)
    }
}
