# SuperTextView

### 介绍

在开发中常常遇到这样的布局：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0908/195924_749c0bc4_553126.png "屏幕截图.png")

一眼看过去，我们就会想到使用`TextView`或`Button`、`MaterialButton`来实现，但往往会事与愿违，达不到我们想要的效果，有人可能想到ViewGroup布局来包裹`TextView`或`Button`来实现，当然这也是可以达到需求的，但却不是最佳的解决方案，于是就有了 **`SuperTextView`** 的出现了。

同样的布局，同样的系统属性，效果却不同：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0908/200805_b0bbd8a4_553126.png "屏幕截图.png")

文本的左右图标是使用系统提供的`drawableStart`、`drawableEnd`来设置的，但最终效果却不样，`TextView`的效果满足不了我们的需求，`SuperTextView`则可以达到需求。

目前只支持系统属性：
- drawableStart
- drawableEnd
- drawableTop
- drawableBottom


这个是`SuperTextView`功能点之一，还提供了自定义属性来设置TextView的快速实现圆角背景，设置渐变色背景，给控件描边，为控件增加状态图，添加按压时背景变色效果。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0908/203855_7d7653ca_553126.png "屏幕截图.png")

| 属性  |  用途 |  备注 |
|---|---|---|
|  open_reset_switch |  false：恢复 drawableStart等系统属性值的特性 |  默认是true |
|  hzw_bg_gradient_orientation | 背景渐变色的方向  |   |
|  hzw_bg_gradient_start_color | 背景渐变色的开始颜色  |   |
|  hzw_bg_gradient_center_color | 背景渐变色的中间颜色  |  是可选的 |
|  hzw_bg_gradient_end_color |  背景渐变色的结束颜色  |   |
|  hzw_stroke_width |  边框的宽度 |   |
|  hzw_stroke_color |  边框的颜色 |   |
|  hzw_normal_fill_color | 正常状态的背景色  | 与渐变色背景不能同时使用  |
|  hzw_disable_color | 禁止状态的背景色  | 效果属性：android:enabled  |
|  hzw_pressed_color| 按下状态的背景色  |  是可选的 |
|  hzw_radius|  四个圆角值  |   |
|  hzw_radius_left_top|  左上圆角 |   |
|  hzw_radius_right_top|  右上圆角 |   |
|  hzw_radius_right_bottom| 右下圆角  |   |
|  hzw_radius_left_bottom| 左下圆角 |   |


同时支持代码的设置调用，目前支持：

- setFillColor
- setStrokeWidth
- setStrokeColor
- setCornerRadius


### 导入依赖

在项目的根build.grade文件加入：

```
allprojects{
    repositories｛
        maven { url "https://jitpack.io" }
    }
}
```


项目依赖配置：

```
dependencies {
	implementation 'com.gitee.common-apps:SuperTextView:版本号'
}
```

最新版本：

[![](https://jitpack.io/v/com.gitee.common-apps/SuperTextView.svg)](https://jitpack.io/#com.gitee.common-apps/SuperTextView)


