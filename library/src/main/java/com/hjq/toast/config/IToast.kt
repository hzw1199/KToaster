package com.hjq.toast.config

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hjq.toast.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/04/06
 * desc   : Toast 接口
 */
@Suppress("unused")
interface IToast {
    /**
     * 显示
     */
    fun show()

    /**
     * 取消
     */
    fun cancel()

    /**
     * 设置文本
     */
    fun setText(id: Int)

    fun setText(text: CharSequence)

    fun setIcon(id: Int)

    fun setImage(path: String)

    /**
     * 设置布局
     */
    fun setView(view: View)

    /**
     * 获取布局
     */
    fun getView(): View?

    /**
     * 设置显示时长
     */
    fun setDuration(duration: Int)

    /**
     * 获取显示时长
     */
    fun getDuration(): Int

    fun setDurationMs(durationMs: Int?)

    fun getDurationMs(): Int?

    /**
     * 设置重心偏移
     */
    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int)

    /**
     * 获取显示重心
     */
    fun getGravity(): Int

    /**
     * 获取水平偏移
     */
    fun getXOffset(): Int

    /**
     * 获取垂直偏移
     */
    fun getYOffset(): Int

    /**
     * 设置屏幕间距
     */
    fun setMargin(horizontalMargin: Float, verticalMargin: Float)

    /**
     * 设置水平间距
     */
    fun getHorizontalMargin(): Float

    /**
     * 设置垂直间距
     */
    fun getVerticalMargin(): Float

    /**
     * 智能获取用于显示消息的 TextView
     */
    fun findMessageView(view: View): TextView? {
        if (view is TextView) {
            if (view.getId() == View.NO_ID) {
                view.setId(android.R.id.message)
            } else require(view.getId() == android.R.id.message) {
                // 必须将 TextView 的 id 值设置成 android.R.id.message
                // 否则 Android 11 手机上在后台 toast.setText 的时候会出现报错
                // java.lang.RuntimeException: This Toast was not created with Toast.makeText()
                "You must set the ID value of TextView to android.R.id.message"
            }
            return view
        }

        val messageView = view.findViewById<View>(android.R.id.message)
        if (messageView is TextView) {
            return messageView
        }

        // 如果设置的布局没有包含一个 TextView 则抛出异常，必须要包含一个 id 值为 message 的 TextView（xml 代码 android:id="@android:id/message"，java 代码 view.setId(android.R.id.message)）
        throw IllegalArgumentException("You must include a TextView with an ID value of message (xml code: android:id=\"@android:id/message\", java code: view.setId(android.R.id.message))")
    }

    /**
     * 获取用于显示图标的 ImageView
     */
    fun findIconView(view: View): ImageView? {
        val iconView = view.findViewById<View>(R.id.iv_icon)
        if (iconView is ImageView) {
            return iconView
        }
        return null
    }
}