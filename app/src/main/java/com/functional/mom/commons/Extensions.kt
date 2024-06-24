package com.functional.mom.commons

import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar

object Extensions {

    fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }
}