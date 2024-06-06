package com.hjq.toast

import android.R
import android.view.View
import android.widget.TextView
import com.hjq.toast.config.IToast

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/11/02
 * desc   : 自定义 Toast 基类
 */
abstract class CustomToast : IToast {
    /** Toast 布局  */
    private var mView: View? = null

    /** Toast 消息 View  */
    private var mMessageView: TextView? = null

    /** Toast 显示重心  */
    private var mGravity = 0

    /** Toast 显示时长  */
    private var mDuration = 0

    /** 水平偏移  */
    private var mXOffset = 0

    /** 垂直偏移  */
    private var mYOffset = 0

    /** 水平间距  */
    private var mHorizontalMargin = 0f

    /** 垂直间距  */
    private var mVerticalMargin = 0f

    /** Toast 动画  */
    var animationsId: Int = R.style.Animation_Toast

    /** 短吐司显示的时长，参考至 NotificationManagerService.SHORT_DELAY  */
    var shortDuration: Int = 2000

    /** 长吐司显示的时长，参考至 NotificationManagerService.LONG_DELAY  */
    var longDuration: Int = 3500

    override fun setText(id: Int) {
        if (mView == null) {
            return
        }
        setText(mView!!.resources.getString(id))
    }

    override fun setText(text: CharSequence) {
        if (mMessageView == null) {
            return
        }
        mMessageView!!.text = text
    }

    override fun setView(view: View) {
        mView = view
        if (mView == null) {
            mMessageView = null
            return
        }
        mMessageView = findMessageView(view)
    }

    override fun getView(): View? {
        return mView
    }

    override fun setDuration(duration: Int) {
        mDuration = duration
    }

    override fun getDuration(): Int {
        return mDuration
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        mGravity = gravity
        mXOffset = xOffset
        mYOffset = yOffset
    }

    override fun getGravity(): Int {
        return mGravity
    }

    override fun getXOffset(): Int {
        return mXOffset
    }

    override fun getYOffset(): Int {
        return mYOffset
    }

    override fun setMargin(horizontalMargin: Float, verticalMargin: Float) {
        mHorizontalMargin = horizontalMargin
        mVerticalMargin = verticalMargin
    }

    override fun getHorizontalMargin(): Float {
        return mHorizontalMargin
    }

    override fun getVerticalMargin(): Float {
        return mVerticalMargin
    }

}