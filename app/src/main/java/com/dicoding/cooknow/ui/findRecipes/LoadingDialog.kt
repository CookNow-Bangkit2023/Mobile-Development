package com.dicoding.cooknow.ui.findRecipes

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.dicoding.cooknow.R

class LoadingDialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    fun showLoadingDialog() {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }
}