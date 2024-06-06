package com.hjq.toast

import android.util.Log
import com.hjq.toast.config.IToastInterceptor
import java.lang.reflect.Modifier

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2020/11/04
 * desc   : 自定义 Toast 拦截器（用于追踪 Toast 调用的位置）
 */
class ToastLogInterceptor : IToastInterceptor {
    override fun intercept(params: ToastParams): Boolean {
        printToast(params.text)
        return false
    }

    protected fun printToast(text: CharSequence?) {
        if (!isLogEnable) {
            return
        }

        // 获取调用的堆栈信息
        val stackTraces = Throwable().stackTrace
        for (stackTrace in stackTraces) {
            // 获取代码行数
            val lineNumber = stackTrace.lineNumber
            if (lineNumber <= 0) {
                continue
            }

            // 获取类的全路径
            val className = stackTrace.className
            try {
                val clazz = Class.forName(className)
                if (!filterClass(clazz)) {
                    printLog("(" + stackTrace.fileName + ":" + lineNumber + ") " + text.toString())
                    // 跳出循环
                    break
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    protected val isLogEnable: Boolean
        get() = Toaster.isDebugMode

    protected fun printLog(msg: String?) {
        // 这里解释一下，为什么不用 Log.d，而用 Log.i，因为 Log.d 在魅族 16th 手机上面无法输出日志
        Log.i("Toaster", msg!!)
    }

    protected fun filterClass(clazz: Class<*>): Boolean {
        // 排查以下几种情况：
        // 1. 排除自身及其子类
        // 2. 排除 Toaster 类
        // 3. 排除接口类
        // 4. 排除抽象类
        return IToastInterceptor::class.java.isAssignableFrom(clazz) || Toaster::class.java == clazz ||
                clazz.isInterface ||
                Modifier.isAbstract(clazz.modifiers)
    }
}