package com.hjq.toast

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import java.lang.reflect.Proxy

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/11/24
 * desc   : 处理 Toast 关闭通知栏权限之后无法弹出的问题
 */
open class NotificationToast(application: Application?) : SystemToast(application) {
    override fun show() {
        hookNotificationService()
        super.show()
    }

    companion object {
        /** 是否已经 Hook 了一次通知服务  */
        private var sHookService = false

        @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
        private fun hookNotificationService() {
            if (sHookService) {
                return
            }
            sHookService = true
            try {
                // 获取到 Toast 中的 getService 静态方法
                val getService = Toast::class.java.getDeclaredMethod("getService")
                getService.isAccessible = true
                // 执行方法，会返回一个 INotificationManager$Stub$Proxy 类型的对象
                val iNotificationManager = getService.invoke(null) ?: return
                // 如果这个对象已经被动态代理过了，并且已经 Hook 过了，则不需要重复 Hook
                if (Proxy.isProxyClass(iNotificationManager.javaClass) &&
                    Proxy.getInvocationHandler(iNotificationManager) is NotificationServiceProxy
                ) {
                    return
                }
                val iNotificationManagerProxy = Proxy.newProxyInstance(
                    Thread.currentThread().contextClassLoader,
                    arrayOf(Class.forName("android.app.INotificationManager")),
                    NotificationServiceProxy(iNotificationManager)
                )
                // 将原来的 INotificationManager$Stub$Proxy 替换掉
                val sService = Toast::class.java.getDeclaredField("sService")
                sService.isAccessible = true
                sService[null] = iNotificationManagerProxy
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}