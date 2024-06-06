package com.hjq.toast.style

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/09/01
 * desc   : 默认白色样式实现
 */
class WhiteToastStyle : BlackToastStyle() {
    override fun getTextColor(context: Context?): Int {
        return -0x45000000
    }

    override fun getBackgroundDrawable(context: Context): Drawable {
        val drawable = GradientDrawable()
        // 设置颜色
        drawable.setColor(-0x151516)
        // 设置圆角
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f, context.resources.displayMetrics
        )
        return drawable
    }
}