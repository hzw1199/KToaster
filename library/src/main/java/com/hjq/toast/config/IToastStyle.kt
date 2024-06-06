package com.hjq.toast.config

import android.content.Context
import android.view.Gravity
import android.view.View

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/09/01
 * desc   : 默认样式接口
 */
interface IToastStyle<V : View> {
    /**
     * 创建 Toast 视图
     */
    fun createView(context: Context): V

    val gravity: Int
        /**
         * 获取 Toast 显示重心
         */
        get() = Gravity.CENTER

    val xOffset: Int
        /**
         * 获取 Toast 水平偏移
         */
        get() = 0

    val yOffset: Int
        /**
         * 获取 Toast 垂直偏移
         */
        get() = 0

    val horizontalMargin: Float
        /**
         * 获取 Toast 水平间距
         */
        get() = 0f

    val verticalMargin: Float
        /**
         * 获取 Toast 垂直间距
         */
        get() = 0f
}