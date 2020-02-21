package ru.chernakov.rocketscienceapp.presentation.error

import android.content.Context
import android.widget.Toast
import com.google.gson.stream.MalformedJsonException
import org.koin.core.KoinComponent
import retrofit2.HttpException
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.ErrorBean
import timber.log.Timber

class ToastErrorView(private val context: Context) : ErrorView, KoinComponent {

    override fun showAuthError(httpException: HttpException) {
        showToast(httpException.message)
    }

    override fun showProtocolError(errorBean: ErrorBean) = showToast(errorBean.message)

    override fun showNonProtocolError(httpException: HttpException) =
        showToast(httpException.message)

    override fun showNetworkError(t: Throwable) =
        showToast(context.resources.getString(R.string.msg_error_network))

    override fun showUnexpectedError(t: Throwable) = showToast(t.toString())

    override fun showMalformedJsonException(malformedJsonException: MalformedJsonException) {
        // do Nothing
    }

    private fun showToast(message: String?) {
        Timber.e(message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }
}