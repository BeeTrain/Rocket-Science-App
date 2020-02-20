package ru.chernakov.feature_profile.presentation

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_profile.R
import ru.chernakov.feature_profile.navigation.ProfileNavigation

class ProfileFragment : BaseFragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private val navigator: ProfileNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivSettings.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotation))
            activity?.let { navigator.fromProfileToSettings() }
        }
        setUserData(profileViewModel.getUser())
        rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_profile

    override fun obtainViewModel(): BaseViewModel = profileViewModel

    private fun setUserData(user: FirebaseUser?) {
        user?.let {
            tvUsername.text = it.displayName
            tvUserNick.text = it.email
            it.photoUrl?.let {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.img_user_avatar)
                    .error(R.drawable.img_user_avatar)
                    .into(ivProfilePhoto)
            }
        }
    }
}