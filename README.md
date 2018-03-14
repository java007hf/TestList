#TestList

##just for test

1.RecyclerView

2.Http post get del set

3.RxJava

4.touch dispatch event :[http://blog.csdn.net/xyz_lmn/article/details/12517911]()

5.javassist modify R.java

6.增加pull to refresh

7.增加类似网易新闻页面

8.ImageLoader: [cube-sdk](https://cube-sdk.liaohuqiu.net/cn/imageloader/)

9.集成sliding 类似于微信的下拉功能，也可以通过PTR来实现，自定义header（和PTR的实现原理不一样，PTR通过layout来布局header和内容；而sliding通过设置view的属性来达到设置偏移）

10.增加swipe listview，原理是通多代理adapter修改getview方法，嵌入需要的menu，然后重写listview里面的事件处理(自定义了一个swipeMenuLayout继承于FrameLayout，重写computeScroll修改子view的layout，达到滑动的目的)

11.增加仿QQ拖拽view；实现原理：集成textview，获取activity的rootview，然后拖拽的时候拷贝一个拖拽view，根据event 坐标设置view的坐标；同是update springview 来实现粘性的效果

12.集成fresco demo,能够解决图片load，内存分配回收，在大量图片list的case中会用到，原理及使用方法：
> [https://www.jianshu.com/p/6f13474d36ac]()
>
> [https://www.jianshu.com/p/265c628a0d59]()
> 
> [https://www.fresco-cn.org/docs/index.html]()

> 主要原理：  
> a.基于DraweeView实现真正显示的ImageView  
> b.通过调用setController->mController.onAttach()->submitRequest()->mDataSource.subscribe();  
> c.view的onAttachedToWindow和onDetachedFromWindow也都会调用submitRequest来决定加载资源或者释放资源  
> d.老版本是通过匿名共享内存来存储，新版本是基于java堆存储   
> e.资源分了4级缓存 内存图片->内存未解码->文件存储->网络资源

13.集成照相机拍照和录像功能

14.集成人脸识别功能，使用face++提供的能力，区别于之前的camera实现不同在于：  
>1.使用GLSurfaceView  
>2.camera通过startPreview可以绑定SurfaceTexture  
>3.SurfaceTexture和mTextureID关联  
>4.流程：GLSurfaceView.setRenderer ->onSurfaceCreated->surfaceInit(执行前面的2，3)->照相机预览有数据->onPreviewFrame(获取人脸数据)->onDrawFrame(根据mTextureID和人脸数据来绘制，并执行updateTexImage显示)->onFrameAvailable(执行requestRender)

**可以学习这个**：[http://blog.csdn.net/aa841538513/article/details/52291759]()

15.增加data binding，运动到MVVM模式  
**可以学习这个**：
>[https://www.jianshu.com/p/eb29c691d370]()  
>[https://www.jianshu.com/p/ba4982be30f8]()