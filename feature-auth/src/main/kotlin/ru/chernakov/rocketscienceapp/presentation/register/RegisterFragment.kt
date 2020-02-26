package ru.chernakov.rocketscienceapp.presentation.register

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.auth.R
import ru.chernakov.rocketscienceapp.data.PasswordStrength
import ru.chernakov.rocketscienceapp.extension.android.widget.addTextChangedListener
import ru.chernakov.rocketscienceapp.navigation.AuthNavigation
import ru.chernakov.rocketscienceapp.presentation.RegisterViewModel
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class RegisterFragment : BaseFragment() {
    private val registerViewModel: RegisterViewModel by viewModel()
    private val navigator: AuthNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
    }

    private fun observeData() {
        registerViewModel.registerSuccessEvent.observe(viewLifecycleOwner, SafeObserver {
            onAuthResult(it)
        })
        registerViewModel.registerErrorEvent.observe(viewLifecycleOwner, SafeObserver {
            val authErrorMessage = when (it) {
                is FirebaseAuthInvalidUserException -> getString(R.string.msg_error_auth_user)
                is FirebaseAuthEmailException -> getString(R.string.msg_error_auth_email)
                is FirebaseAuthInvalidCredentialsException -> getString(R.string.msg_error_auth_credentials)
                else -> it.localizedMessage
            }
            showAuthErrorMessage(authErrorMessage)
        })
    }

    private fun setupListeners() {
        ivClose.setOnClickListener { navigator.fromRegisterToLogin() }
        btRegister.setOnClickListener { registerUser() }
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
                    val passStrength = PasswordStrength.calculate(it.toString())
                    tilPassword.helperText =
                        if (it.isNotEmpty()) getString(passStrength.message) else null
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
    }

    private fun registerUser() {
        val isEmailValid = registerViewModel.isEmailValid(titEmail.editableText)
        val isPasswordValid = registerViewModel.isPasswordValid(titPassword.editableText)
        val isPassConfirmValid = registerViewModel.isPasswordConfirmValid(
            titPassword.editableText, titPasswordConfirm.editableText
        )
        when {
            isEmailValid && isPasswordValid && isPassConfirmValid -> {
                registerViewModel.registerUser(
                    titEmail.text.toString(),
                    titPassword.text.toString()
                )
            }
            !isEmailValid -> {
                titEmail.requestFocus()
                tilEmail.error = getString(R.string.msg_error_email)
            }
            !isPasswordValid -> {
                titPassword.requestFocus()
                tilPassword.error = getString(R.string.msg_error_password)
            }
            !isPassConfirmValid -> {
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
        navigator.fromRegisterToAppFeatures()
    }

    override fun getLayout(): Int = R.layout.fragment_register

    override fun obtainViewModel(): BaseViewModel = registerViewModel
}