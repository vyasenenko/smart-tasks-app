package com.smart.tasks.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class CommonViewModel : ViewModel() {

    protected val progress: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val progressEvent: LiveData<Boolean>
        get() = progress
}