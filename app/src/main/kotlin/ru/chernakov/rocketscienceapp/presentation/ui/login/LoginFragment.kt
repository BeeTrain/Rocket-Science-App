package ru.chernakov.rocketscienceapp.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.feed.FeedFragment
import ru.chernakov.rocketscienceapp.util.RequestCodeGenerator
import timber.log.Timber


class LoginFragment : BaseFragment() {
    val viewModel: LoginViewModel by viewModel()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        activity?.let {
            googleSignInClient = GoogleSignIn.getClient(it, gso)
        }
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSign.setOnClickListener { signIn() }
    }

    override fun getLayout(): Int = R.layout.fragment_login

    override fun obtainViewModel(): BaseViewModel = viewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                authWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(context, "Google sign in failed", Toast.LENGTH_SHORT).show()
                Timber.tag(TAG).e(e, "Google sign in failed")
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun authWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    startFragment(FeedFragment.newInstance())
                } else {
                    Toast.makeText(context, "running", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName
        private val RC_SIGN_IN = RequestCodeGenerator.next

        fun newInstance() = LoginFragment()
    }
}