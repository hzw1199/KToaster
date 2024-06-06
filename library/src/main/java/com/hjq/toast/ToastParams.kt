package com.hjq.toast

import com.hjq.toast.config.IToastInterceptor
import com.hjq.toast.config.IToastStrategy
import com.hjq.toast.config.IToastStyle

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2022/10/31
 * desc   : Toast 参数类
 */
class ToastParams {
    /** 显示的文本  */
    @JvmField
    var text: CharSequence? = null

    /**
     * Toast 显示时长，有两种值可选
     *
     * 短吐司：[android.widget.Toast.LENGTH_SHORT]
     * 长吐司：[android.widget.Toast.LENGTH_LONG]
     */
    @JvmField
    var duration: Int = -1

    /** 延迟显示时间  */
    @JvmField
    var delayMillis: Long = 0

    /** 是否跨页面展示（如果为 true 则优先用系统 Toast 实现）  */
    @JvmField
    var crossPageShow: Boolean = false

    /** Toast 样式  */
    @JvmField
    var style: IToastStyle<*>? = null

    /** Toast 处理策略  */
    @JvmField
    var strategy: IToastStrategy? = null

    /** Toast 拦截器  */
    @JvmField
    var interceptor: IToastInterceptor? = null
}