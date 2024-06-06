package com.hjq.toast

import android.app.Application
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.hjq.toast.config.IToast

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/11/03
 * desc   : 系统 Toast
 */
@Suppress("deprecation")
open class SystemToast(application: Application?) : Toast(application), IToast {
    /** 吐司消息 View  */
    private var mMessageView: TextView? = null

    override fun setView(view: View) {
        super.setView(view)
        if (view == null) {
            mMessageView = null
            return
        }
        mMessageView = findMessageView(view)
    }

    override fun setText(text: CharSequence) {
        super.setText(text)
        if (mMessageView == null) {
            return
        }
        mMessageView!!.text = text
    }

}