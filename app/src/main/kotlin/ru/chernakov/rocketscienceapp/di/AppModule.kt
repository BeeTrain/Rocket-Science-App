package ru.chernakov.rocketscienceapp.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.R

val appModule = module {
    single { FirebaseAuth.getInstance() as FirebaseAuth }

    single {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(androidContext(), gso) as GoogleSignInClient
    }
}