package com.hjq.toast.config

import android.app.Application
import com.hjq.toast.ToastParams

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2019/05/19
 * desc   : Toast 处理策略
 */
interface IToastStrategy {
    /**
     * 注册策略
     */
    fun registerStrategy(application: Application?)

    /**
     * 创建 Toast
     */
    fun createToast(params: ToastParams): IToast

    /**
     * 显示 Toast
     */
    fun showToast(params: ToastParams)

    /**
     * 取消 Toast
     */
    fun cancelToast()
}