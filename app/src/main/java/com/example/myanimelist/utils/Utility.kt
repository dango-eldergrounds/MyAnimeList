package com.example.myanimelist.utils

import android.app.Activity
import com.example.myanimelist.ui.MainActivity

fun Activity.toggleLoading(show: Boolean) {
    (this as MainActivity).toggleLoadingOverlay(show)
}