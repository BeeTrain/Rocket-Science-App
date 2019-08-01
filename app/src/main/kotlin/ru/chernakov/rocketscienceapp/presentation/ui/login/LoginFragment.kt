package ru.chernakov.rocketscienceapp.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.feed.FeedFragment
import ru.chernakov.rocketscienceapp.util.RequestCodeGenerator


class LoginFragment : BaseFragment() {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSign.setOnClickListener { signIn() }
    }

    override fun getLayout(): Int = R.layout.fragment_login

    override fun obtainViewModel(): BaseViewModel = loginViewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.let { authWithGoogle(it) }
        }
    }

    private fun signIn() {
        startActivityForResult(loginViewModel.getGoogleSignInIntent(), RC_SIGN_IN)
    }

    private fun authWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        activity?.let {
            loginViewModel.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) startFragment(FeedFragment.newInstance())
                }
        }
    }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName
        private val RC_SIGN_IN = RequestCodeGenerator.next

        fun newInstance() = LoginFragment()
    }
}