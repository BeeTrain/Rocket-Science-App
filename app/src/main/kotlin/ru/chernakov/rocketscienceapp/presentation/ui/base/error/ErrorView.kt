package ru.chernakov.rocketscienceapp.presentation.ui.base.error

import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import ru.chernakov.rocketscienceapp.data.model.ErrorBean

interface ErrorView {

    fun showAuthError(httpException: HttpException)

    fun showProtocolError(errorBean: ErrorBean)

    fun showNonProtocolError(httpException: HttpException)

    fun showNetworkError(t: Throwable)

    fun showUnexpectedError(t: Throwable)

    fun showMalformedJsonException(malformedJsonException: MalformedJsonException)
}
