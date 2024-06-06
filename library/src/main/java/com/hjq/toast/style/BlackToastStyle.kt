package com.hjq.toast.style

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hjq.toast.config.IToastStyle

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/09/01
 * desc   : 默认黑色样式实现
 */
@Suppress("unused", "deprecation")
open class BlackToastStyle : IToastStyle<View> {
    override fun createView(context: Context): View {
        val textView = TextView(context)
        textView.id = R.id.message
        textView.gravity = getTextGravity(context)
        textView.setTextColor(getTextColor(context))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize(context))

        val horizontalPadding = getHorizontalPadding(context)
        val verticalPadding = getVerticalPadding(context)

        // 适配布局反方向特性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setPaddingRelative(
                horizontalPadding,
                verticalPadding,
                horizontalPadding,
                verticalPadding
            )
        } else {
            textView.setPadding(
                horizontalPadding,
                verticalPadding,
                horizontalPadding,
                verticalPadding
            )
        }

        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val backgroundDrawable = getBackgroundDrawable(context)
        // 设置背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.background = backgroundDrawable
        } else {
            textView.setBackgroundDrawable(backgroundDrawable)
        }

        // 设置 Z 轴阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.z = getTranslationZ(context)
        }

        return textView
    }

    protected fun getTextGravity(context: Context?): Int {
        return Gravity.CENTER
    }

    protected open fun getTextColor(context: Context?): Int {
        return -0x11000001
    }

    protected fun getTextSize(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            14f, context.resources.displayMetrics
        )
    }

    protected fun getHorizontalPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            24f, context.resources.displayMetrics
        ).toInt()
    }

    protected fun getVerticalPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            16f, context.resources.displayMetrics
        ).toInt()
    }

    protected open fun getBackgroundDrawable(context: Context): Drawable {
        val drawable = GradientDrawable()
        // 设置颜色
        drawable.setColor(-0x4d000000)
        // 设置圆角
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f, context.resources.displayMetrics
        )
        return drawable
    }

    protected fun getTranslationZ(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            3f,
            context.resources.displayMetrics
        )
    }
}