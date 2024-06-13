package com.hjq.toast.style

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.hjq.toast.R
import com.hjq.toast.Toaster
import com.hjq.toast.config.IToastStyle

/**
 * author : hzw1199
 * github : https://github.com/hzw1199
 * time   : 2024/06/06
 * desc   : Toast 高级包装样式实现
 */
class AdvancedToastStyle(
    private val styleConfig: StyleConfig,
) : IToastStyle<View> {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun createView(context: Context): View {
        val toastView = LayoutInflater.from(context).inflate(R.layout.toast_advanced_view, null)
        applyStyleConfig(styleConfig, toastView)
        return toastView
    }

    override val gravity: Int
        get() = styleConfig.gravity

    override val xOffset: Int
        get() = 0

    override val yOffset: Int
        get() = dpToPx(styleConfig.positionOffsetY)

    override val horizontalMargin: Float
        get() = 0F

    override val verticalMargin: Float
        get() = 0F

    // 应用StyleConfig中的配置
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun applyStyleConfig(config: StyleConfig, toastView: View) {
        toastView.findViewById<LinearLayout>(R.id.toast_container).apply {
            // 设置内边距
            setPadding(dpToPx(config.paddingLeft), dpToPx(config.paddingTop), dpToPx(config.paddingRight), dpToPx(config.paddingBottom))

            // 设置最大宽度和最小宽度
//            layoutParams.width = config.maxWidth.takeIf { it > 0 } ?: LinearLayout.LayoutParams.WRAP_CONTENT
            minimumWidth = dpToPx(config.minWidth)

            // 设置背景
            background = ContextCompat.getDrawable(context, R.drawable.shape_toast_background)?.mutate()?.apply {
                // 设置背景颜色和圆角
                (this as GradientDrawable).setColor(Color.parseColor(config.backgroundColor))
                cornerRadius = dpToPx(config.backgroundRadius).toFloat()
            }
        }

        toastView.findViewById<TextView>(android.R.id.message).apply {
            // 设置文本颜色、大小、行数等
            setTextColor(Color.parseColor(config.textColor))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, config.fontSize)
            maxLines = config.maxLines
            config.lineHeight?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lineHeight = dpToPx(it)
                }
            }
            // 设置文本粗细
            config.fontWeight?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // Android P及以上版本支持直接设置fontWeight
                    typeface = Typeface.create(Typeface.DEFAULT, config.fontWeight!!, false)
                } else {
                    // Android P以下版本可以通过选择不同的字体样式来设置粗细
                    typeface = when (config.fontWeight) {
                        in 100..500 -> Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                        in 500..700 -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                        else -> typeface // 使用默认字体样式
                    }
                }
            }

            // 根据需要设置其他文本属性
        }

        toastView.findViewById<ImageView>(R.id.iv_icon).apply {
            // 设置图标大小
            layoutParams = layoutParams.apply {
                width = dpToPx(config.iconSize)
                height = dpToPx(config.iconSize)
            }
        }
    }
}

class StyleConfig {
    var paddingLeft = 0F
    var paddingRight = 0F
    var paddingTop = 0F
    var paddingBottom = 0F

    var maxWidth = 0F
    var minWidth = 0F

    var gravity = Gravity.BOTTOM

    var positionOffsetY = 30F

    var fontSize = 14F
    var fontWeight: Int? = null
    var textColor = "#FFFFFF"
    var lineHeight: Float? = null
    var maxLines = 2
    var backgroundColor = "#BB000000"
    var backgroundRadius = 8F
    var iconSize = 40F

    fun padding(@Dimension(unit = Dimension.DP) padding: Float): StyleConfig {
        paddingLeft = padding
        paddingRight = padding
        paddingTop = padding
        paddingBottom = padding
        return this
    }

    fun padding(@Dimension(unit = Dimension.DP) left: Float, @Dimension(unit = Dimension.DP) top: Float, @Dimension(unit = Dimension.DP) right: Float, @Dimension(unit = Dimension.DP) bottom: Float): StyleConfig {
        paddingLeft = left
        paddingRight = right
        paddingTop = top
        paddingBottom = bottom
        return this
    }

    fun maxWidth(@Dimension(unit = Dimension.DP) maxWidth: Float): StyleConfig {
        this.maxWidth = maxWidth
        return this
    }

    fun minWidth(@Dimension(unit = Dimension.DP) minWidth: Float): StyleConfig {
        this.minWidth = minWidth
        return this
    }

    fun gravity(gravity: Int): StyleConfig {
        this.gravity = gravity
        return this
    }

    fun positionOffsetY(@Dimension(unit = Dimension.DP) offsetY: Float): StyleConfig {
        this.positionOffsetY = offsetY
        return this
    }

    fun fontSize(@Dimension(unit = Dimension.SP) fontSize: Float): StyleConfig {
        this.fontSize = fontSize
        return this
    }

    fun fontWeight(@IntRange(from = 1, to = 1000) fontWeight: Int?): StyleConfig {
        this.fontWeight = fontWeight
        return this
    }

    fun textColor(textColor: String): StyleConfig {
        this.textColor = textColor
        return this
    }

    fun lineHeight(@Dimension(unit = Dimension.DP) @FloatRange(from = 0.0) lineHeight: Float?): StyleConfig {
        this.lineHeight = lineHeight
        return this
    }

    fun maxLines(maxLines: Int): StyleConfig {
        this.maxLines = maxLines
        return this
    }

    fun backgroundColor(backgroundColor: String): StyleConfig {
        this.backgroundColor = backgroundColor
        return this
    }

    fun backgroundRadius(@Dimension(unit = Dimension.DP) backgroundRadius: Float): StyleConfig {
        this.backgroundRadius = backgroundRadius
        return this
    }

    fun iconSize(@Dimension(unit = Dimension.DP) iconSize: Float): StyleConfig {
        this.iconSize = iconSize
        return this
    }

}

fun Context.dpToPx(dp: Float): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun dpToPx(dp: Float): Int {
    return Toaster.sApplication!!.dpToPx(dp)
}