package com.hjq.toast.style

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.hjq.toast.config.IToastStyle

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/03/09
 * desc   : Toast 自定义 View 包装样式实现
 */
class CustomToastStyle @JvmOverloads constructor(
    private val mLayoutId: Int,
    private val mGravity: Int = Gravity.CENTER,
    private val mXOffset: Int = 0,
    private val mYOffset: Int = 0,
    private val mHorizontalMargin: Float = 0f,
    private val mVerticalMargin: Float = 0f
) : IToastStyle<View> {
    override fun createView(context: Context): View {
        return LayoutInflater.from(context).inflate(mLayoutId, null)
    }

    override val gravity: Int
        get() = mGravity

    override val xOffset: Int
        get() = mXOffset

    override val yOffset: Int
        get() = mYOffset

    override val horizontalMargin: Float
        get() = mHorizontalMargin

    override val verticalMargin: Float
        get() = mVerticalMargin
}