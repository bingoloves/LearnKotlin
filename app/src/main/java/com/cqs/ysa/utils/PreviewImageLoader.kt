package com.cqs.ysa.utils

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.previewlibrary.loader.IZoomMediaLoader
import com.previewlibrary.loader.MySimpleTarget

/**
 * Created by Administrator on 2020/7/17 0017.
 */
class PreviewImageLoader:IZoomMediaLoader{
    override fun displayGifImage(context: Fragment, path: String, imageView: ImageView?, simpleTarget: MySimpleTarget) {
        Glide.with(context)
                .asGif()
                .load(path)
                //可以解决gif比较几种时 ，加载过慢 //DiskCacheStrategy.NONE
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).dontAnimate())
                .listener(object :RequestListener<GifDrawable>{
                    override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        simpleTarget.onResourceReady()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                        simpleTarget.onLoadFailed(null)
                        return false
                    }
                })
                .into(imageView!!)
    }

    override fun clearMemory(c: Context) {
        Glide.get(c).clearMemory()
    }

    override fun displayImage(context: Fragment, path: String, imageView: ImageView?, simpleTarget: MySimpleTarget) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(RequestOptions().fitCenter())
                .into(object : SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        simpleTarget.onResourceReady()
                        imageView!!.setImageBitmap(resource)
                    }
                })
    }

    override fun onStop(context: Fragment) {
        Glide.with(context).onStop()
    }
}