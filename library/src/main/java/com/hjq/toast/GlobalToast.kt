package com.hjq.toast

import android.app.Application

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/11/30
 * desc   : 利用悬浮窗权限弹出全局 Toast
 */
class GlobalToast(application: Application?) : CustomToast() {
    /** Toast 实现类  */
    private val mToastImpl = ToastImpl(application!!, this)

    override fun show() {
        // 替换成 WindowManager 来显示
        mToastImpl.show()
    }

    override fun cancel() {
        // 取消 WindowManager 的显示
        mToastImpl.cancel()
    }
}