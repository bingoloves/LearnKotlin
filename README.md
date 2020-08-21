### Kotlin


RichText

在第一次调用之前：
* 先调用RichText.initCacheDir()方法设置缓存目录.
* ImageFixCallback的回调方法不一定是在主线程回调
* 注意不要进行UI操作
* 本地图片由根路径\开头，Assets目录图片由file:///android_asset/开头
* Gif图片播放不支持硬件加速，若要使用Gif图片请先关闭TextView的硬件加速 
  textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

