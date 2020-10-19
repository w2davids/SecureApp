package com.example.secureapp.lockscreen

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.WindowManager
import com.example.secureapp.R

object LockScreenUtil {

    private var overlayDialog: OverlayDialog? = null

    fun lock(activity: Activity?) {
        activity.let {
            overlayDialog = OverlayDialog(it)
            overlayDialog?.show()
        }
    }

    fun unlock() {
        if (overlayDialog != null) {
            overlayDialog?.dismiss()
        }
    }

    private class OverlayDialog(activity: Activity?) :
        AlertDialog(activity, R.style.OverlayDialog) {

        init {
            val params: WindowManager.LayoutParams? = window?.attributes
            params?.width = WindowManager.LayoutParams.MATCH_PARENT
            params?.height = WindowManager.LayoutParams.MATCH_PARENT
            params?.gravity = Gravity.TOP or Gravity.LEFT
            window?.attributes = params
            window?.setFlags(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY or
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                0xffffff
            )
            activity?.let { setOwnerActivity(it) }
            setCancelable(false)
        }
    }
}