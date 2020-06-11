package com.example.gamereminder.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel: ViewModel() {

    protected val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    protected val _showProgressBar = MutableLiveData<Boolean>(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    protected var job: Job = Job()

    protected fun executeTask(task: suspend () -> Unit) {
        _showProgressBar.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            task.invoke()
        }.apply { invokeOnCompletion { _showProgressBar.postValue(false) } }
    }

    override fun onCleared() {
        if (job.isActive) job.cancel()
        super.onCleared()
    }
}