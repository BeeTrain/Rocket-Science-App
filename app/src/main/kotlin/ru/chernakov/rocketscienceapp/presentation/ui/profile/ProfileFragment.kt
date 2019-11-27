package ru.chernakov.rocketscienceapp.presentation.ui.profile

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.settings.SettingsFragment

class ProfileFragment : BaseFragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivSettings.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotation))
            activity?.let { SettingsFragment.newInstance(it.supportFragmentManager) }
        }
        setUserData(profileViewModel.getUser())
    }

    override fun getLayout(): Int = R.layout.fragment_profile

    override fun obtainViewModel(): BaseViewModel = profileViewModel

    private fun setUserData(user: FirebaseUser?) {
        user?.let {
            tvUsername.text = it.displayName
            tvUserNick.text = it.email
        }
    }

    companion object {

        fun newInstance() = ProfileFragment()
    }
}