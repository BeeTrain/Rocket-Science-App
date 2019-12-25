package ru.chernakov.core_base.extension.kotlinx.coroutines.experimental

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Suppress("NOTHING_TO_INLINE")
suspend inline fun <T> Call<T>.await(): T {
    return suspendCancellableCoroutine {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful)
                    it.resume(response.body()!!)
                else
                    it.resumeWithException(IOException(response.message()))
            }
        })
    }
}