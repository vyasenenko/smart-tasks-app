package com.smart.tasks.common

import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

@BindingAdapter("setVisibility")
fun View.setVisibility(bool: Boolean) {
    this.isVisible = bool
}

@BindingAdapter("setImage")
fun ImageView.setImage(@DrawableRes image: Int) {
    this.setImageResource(image)
}

val Int.dpInt: Int
    get() = dp.toInt()

private var dpValue: Float? = null

val Int.dp: Float
    get() = (this * (dpValue ?: run {
        dpValue = Resources.getSystem().displayMetrics.density
        dpValue!!
    }))


fun <A, B> zipLiveDataNullable(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A?, B?>> =
    MediatorLiveData<Pair<A?, B?>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            this.value = Pair(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }

infix fun <A, B> LiveData<A>.zipNullable(b: LiveData<B>): LiveData<Pair<A?, B?>> =
    zipLiveDataNullable(this, b)

fun TextView.showKeyboard() {
    getSystemService(context, InputMethodManager::class.java)
        ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun TextView.hideKeyboard() {
    getSystemService(context, InputMethodManager::class.java)
        ?.hideSoftInputFromWindow(windowToken, 0)
}