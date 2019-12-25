package ru.chernakov.rocketscienceapp.presentation.ui.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.flow.FlowFragment
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.extension.android.widget.addTextChangedListener
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment

class RegisterFragment : BaseFragment() {
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { fragmentManager?.popBackStack() }
        titEmail.addTextChangedListener {
            afterTextChanged {
                it?.let {
                    if (registerViewModel.isEmailValid(it)) {
                        tilEmail.error = null
                    }
                }
            }
        }
        titPassword.addTextChangedListener {
            afterTextChanged {
                it?.let {
                    if (registerViewModel.isPasswordValid(it)) {
                        tilPassword.error = null
                    }
                }
            }
        }
        titPasswordConfirm.addTextChangedListener {
            afterTextChanged {
                it?.let {
                    if (registerViewModel.isPasswordConfirmValid(titPassword.editableText, it)) {
                        tilPasswordConfirm.error = null
                    }
                }
            }
        }
        btRegister.setOnClickListener { registerUser() }
        registerViewModel.registerSuccessEvent.observe(this, SafeObserver {
            onAuthResult(it)
        })
        registerViewModel.registerErrorEvent.observe(this, SafeObserver {
            val authErrorMessage = when (it) {
                is FirebaseAuthInvalidUserException -> getString(R.string.msg_error_auth_user)
                is FirebaseAuthEmailException -> getString(R.string.msg_error_auth_email)
                is FirebaseAuthInvalidCredentialsException -> getString(R.string.msg_error_auth_credentials)
                else -> it.localizedMessage
            }
            showAuthErrorMessage(authErrorMessage)
        })
    }

    override fun getLayout(): Int = R.layout.fragment_register

    override fun obtainViewModel(): BaseViewModel = registerViewModel

    private fun registerUser() {
        val isEmailValid = registerViewModel.isEmailValid(titEmail.editableText)
        val isPasswordValid = registerViewModel.isPasswordValid(titPassword.editableText)
        val isPassConfirmValid = registerViewModel.isPasswordConfirmValid(
                titPassword.editableText, titPasswordConfirm.editableText
        )
        if (isEmailValid && isPasswordValid && isPassConfirmValid) {
            registerViewModel.registerUser(titEmail.text.toString(), titPassword.text.toString())
        } else {
            if (!isEmailValid) {
                titEmail.requestFocus()
                tilEmail.error = getString(R.string.msg_error_email)
            }
            if (!isPasswordValid) {
                titPassword.requestFocus()
                tilPassword.error = getString(R.string.msg_error_password)
            }
            if (!isPassConfirmValid) {
                titPasswordConfirm.requestFocus()
                tilPasswordConfirm.error = getString(R.string.msg_error_password_confirm)
            }
        }
    }

    private fun onAuthResult(isLogged: Boolean) {
        if (isLogged) goToNextScreen() else showAuthErrorMessage()
    }

    private fun showAuthErrorMessage(message: String? = null) {
        val mes = message ?: getString(R.string.msg_error_auth)
        view?.let { Snackbar.make(it, mes, Snackbar.LENGTH_LONG).show() }
    }

    private fun goToNextScreen() {
        startFragment(FlowFragment.newInstance(), false)
    }

    companion object {
        fun newInstance(fragmentManager: FragmentManager) {
            fragmentManager.replaceFragment(RegisterFragment()).apply {
                addToBackStack = true
                enter = R.anim.slide_up
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.slide_down
                commit()
            }
        }
    }
}