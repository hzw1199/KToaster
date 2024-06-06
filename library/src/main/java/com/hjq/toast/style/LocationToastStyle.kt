package com.hjq.toast.style

import android.content.Context
import android.view.View
import com.hjq.toast.config.IToastStyle

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2021/03/09
 * desc   : Toast 位置包装样式实现
 */
@Suppress("unused")
class LocationToastStyle @JvmOverloads constructor(
    private val mStyle: IToastStyle<*>,
    private val mGravity: Int,
    private val mXOffset: Int = 0,
    private val mYOffset: Int = 0,
    private val mHorizontalMargin: Float = 0f,
    private val mVerticalMargin: Float = 0f
) : IToastStyle<View> {
    override fun createView(context: Context): View {
        return mStyle.createView(context)
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