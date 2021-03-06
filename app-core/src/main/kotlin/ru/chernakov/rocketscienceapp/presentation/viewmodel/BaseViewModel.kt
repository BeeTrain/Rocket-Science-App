package ru.chernakov.rocketscienceapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    private var jobs = mutableListOf<Job>()

    open val error = SingleLiveEvent<Throwable>()
    open val loading = MutableLiveData<Boolean>()

    protected fun addCancellableJob(job: Job?) {
        if (job != null) {
            jobs.filter { it.isCompleted }
                .forEach { jobs.remove(it) }
            jobs.add(job)
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    @Suppress("TooGenericExceptionCaught")
    protected fun launchLoadingErrorJob(
        context: CoroutineContext = Dispatchers.Main,
        block: suspend () -> Unit
    ): Job {
        return GlobalScope.launch(context) {
            try {
                error.postValue(null)
                loading.postValue(true)
                block.invoke()
            } catch (t: Throwable) {
                error.postValue(t)
            } finally {
                loading.postValue(false)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    protected fun launchLoadingErrorJob(
        context: CoroutineContext = Dispatchers.Main,
        exceptionBlock: (suspend () -> Unit)? = null,
        block: suspend () -> Unit
    ): Job {
        return GlobalScope.launch(context) {
            try {
                error.postValue(null)
                loading.postValue(true)
                block.invoke()
            } catch (t: Throwable) {
                exceptionBlock?.invoke()
                error.postValue(t)
            } finally {
                loading.postValue(false)
            }
        }
    }
}