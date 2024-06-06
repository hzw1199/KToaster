package com.hjq.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import kotlin.concurrent.Volatile

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/04/07
 * desc   : Activity 生命周期监控
 */
internal class ActivityStack
/** 私有化构造函数  */
private constructor() : ActivityLifecycleCallbacks {
    /**
     * 注册 Activity 生命周期监听
     */
    fun register(application: Application?) {
        if (application == null) {
            return
        }
        application.registerActivityLifecycleCallbacks(this)
    }

    /** 前台 Activity 对象  */
    var foregroundActivity: Activity? = null
        private set

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        foregroundActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (foregroundActivity !== activity) {
            return
        }
        foregroundActivity = null
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: ActivityStack? = null

        val instance: ActivityStack
            get() {
                if (sInstance == null) {
                    synchronized(ActivityStack::class.java) {
                        if (sInstance == null) {
                            sInstance = ActivityStack()
                        }
                    }
                }
                return sInstance!!
            }
    }
}