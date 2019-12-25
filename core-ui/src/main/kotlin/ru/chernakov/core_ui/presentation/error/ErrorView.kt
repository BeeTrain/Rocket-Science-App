package ru.chernakov.core_ui.presentation.error

import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import ru.chernakov.core_ui.data.model.ErrorBean

interface ErrorView {

    fun showAuthError(httpException: HttpException)

    fun showProtocolError(errorBean: ErrorBean)

    fun showNonProtocolError(httpException: HttpException)

    fun showNetworkError(t: Throwable)

    fun showUnexpectedError(t: Throwable)

    fun showMalformedJsonException(malformedJsonException: MalformedJsonException)
}
