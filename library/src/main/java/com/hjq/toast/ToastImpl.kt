package com.hjq.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.BadTokenException
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/11/02
 * desc   : 自定义 Toast 实现类
 */
internal class ToastImpl private constructor(
    context: Context,
    /** 当前的吐司对象  */
    private val mToast: CustomToast
) {
    /** WindowManager 辅助类  */
    private var mWindowLifecycle: WindowLifecycle? = null

    /** 当前应用的包名  */
    private val mPackageName: String = context.packageName

    /** 当前是否已经显示  */
    var isShow: Boolean = false

    /** 当前是否全局显示  */
    private var mGlobalShow = false

    constructor(activity: Activity, toast: CustomToast) : this(activity as Context, toast) {
        mGlobalShow = false
        mWindowLifecycle = WindowLifecycle((activity))
    }

    constructor(application: Application, toast: CustomToast) : this(
        application as Context,
        toast
    ) {
        mGlobalShow = true
        mWindowLifecycle = WindowLifecycle(application)
    }

    /***
     * 显示吐司弹窗
     */
    fun show() {
        if (isShow) {
            return
        }
        if (isMainThread) {
            mShowRunnable.run()
        } else {
            HANDLER.removeCallbacks(mShowRunnable)
            HANDLER.post(mShowRunnable)
        }
    }

    /**
     * 取消吐司弹窗
     */
    fun cancel() {
        if (!isShow) {
            return
        }
        HANDLER.removeCallbacks(mShowRunnable)
        if (isMainThread) {
            mCancelRunnable.run()
        } else {
            HANDLER.removeCallbacks(mCancelRunnable)
            HANDLER.post(mCancelRunnable)
        }
    }

    private val isMainThread: Boolean
        /**
         * 判断当前是否在主线程
         */
        get() = Looper.myLooper() == Looper.getMainLooper()

    /**
     * 发送无障碍事件
     */
    private fun trySendAccessibilityEvent(view: View) {
        val context = view.context
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (!accessibilityManager.isEnabled) {
            return
        }
        val eventType = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
        val event: AccessibilityEvent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            event = AccessibilityEvent()
            event.eventType = eventType
        } else {
            event = AccessibilityEvent.obtain(eventType)
        }
        event.className = Toast::class.java.name
        event.packageName = context.packageName
        view.dispatchPopulateAccessibilityEvent(event)
        // 将 Toast 视为通知，因为它们用于向用户宣布短暂的信息
        accessibilityManager.sendAccessibilityEvent(event)
    }

    private val mShowRunnable: Runnable = object : Runnable {
        @SuppressLint("WrongConstant")
        override fun run() {
            val windowManager = mWindowLifecycle!!.windowManager ?: return

            val params = WindowManager.LayoutParams()
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.format = PixelFormat.TRANSLUCENT
            params.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            params.packageName = mPackageName
            params.gravity = mToast.getGravity()
            params.x = mToast.getXOffset()
            params.y = mToast.getYOffset()
            params.verticalMargin = mToast.getVerticalMargin()
            params.horizontalMargin = mToast.getHorizontalMargin()
            params.windowAnimations = mToast.animationsId

            // 如果是全局显示
            if (mGlobalShow) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    // 在 type 等于 TYPE_APPLICATION_OVERLAY 的时候
                    // 不能添加 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE 标记
                    // 否则会导致在 Android 13 上面会出现 Toast 布局被半透明化的效果
                    // Github issue 地址：https://github.com/getActivity/Toaster/issues/108
                    params.flags =
                        params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                } else {
                    params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                }
            }

            try {
                windowManager.addView(mToast.getView(), params)
                // 添加一个移除吐司的任务
                HANDLER.postDelayed(
                    { cancel() },
                    (if (mToast.getDuration() == Toast.LENGTH_LONG) mToast.longDuration else mToast.shortDuration).toLong()
                )
                // 注册生命周期管控
                mWindowLifecycle!!.register(this@ToastImpl)
                // 当前已经显示
                isShow = true
                // 发送无障碍事件
                trySendAccessibilityEvent(mToast.getView()!!)
            } catch (e: IllegalStateException) {
                // 如果这个 View 对象被重复添加到 WindowManager 则会抛出异常
                // java.lang.IllegalStateException: View android.widget.TextView has already been added to the window manager.
                // 如果 WindowManager 绑定的 Activity 已经销毁，则会抛出异常
                // android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@ef1ccb6 is not valid; is your activity running?
                e.printStackTrace()
            } catch (e: BadTokenException) {
                e.printStackTrace()
            }
        }
    }

    private val mCancelRunnable: Runnable = object : Runnable {
        override fun run() {
            try {
                val windowManager = mWindowLifecycle!!.windowManager ?: return

                windowManager.removeViewImmediate(mToast.getView())
            } catch (e: IllegalArgumentException) {
                // 如果当前 WindowManager 没有附加这个 View 则会抛出异常
                // java.lang.IllegalArgumentException: View=android.widget.TextView not attached to window manager
                e.printStackTrace()
            } finally {
                // 反注册生命周期管控
                mWindowLifecycle!!.unregister()
                // 当前没有显示
                isShow = false
            }
        }
    }

    companion object {
        private val HANDLER = Handler(Looper.getMainLooper())
    }
}