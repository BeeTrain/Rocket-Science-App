package ru.chernakov.rocketscienceapp.presentation.ui.base.error

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.gson.stream.MalformedJsonException
import org.koin.core.KoinComponent
import retrofit2.HttpException
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.ErrorBean

class SnackbarErrorView : ErrorView, KoinComponent {
    private var activity: Activity? = null
    private var view: View? = null

    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(view: View) {
        this.view = view
    }

    override fun showAuthError(httpException: HttpException) {
        showSnackBar(httpException.message)
    }

    override fun showProtocolError(errorBean: ErrorBean) = showSnackBar(errorBean.message)

    override fun showNonProtocolError(httpException: HttpException) = showSnackBar(httpException.message)

    override fun showNetworkError(t: Throwable) = showSnackBar(getString(R.string.msg_error_network))

    override fun showUnexpectedError(t: Throwable) = showSnackBar(getString(R.string.msg_error_unexpected))

    override fun showMalformedJsonException(malformedJsonException: MalformedJsonException) {
        showSnackBar(getString(R.string.msg_error_unexpected))
    }

    private fun showSnackBar(message: String?) {
        showActivitySnackbar(message)
        showFragmentSnackbar(message)
    }

    private fun showActivitySnackbar(message: String?) = activity?.let {
        val focusView = it.window.decorView.findFocus()
        if (focusView != null && focusView.context != null) {
            Snackbar.make(focusView, message ?: getString(R.string.msg_error_unexpected), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showFragmentSnackbar(message: String?) = view?.let {
        if (it.context != null) {
            Snackbar.make(it, message ?: getString(R.string.msg_error_unexpected), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getString(@StringRes resId: Int) = when {
        activity != null -> activity!!.resources.getString(resId)
        view != null -> view!!.resources.getString(resId)
        else -> ""
    }
}
