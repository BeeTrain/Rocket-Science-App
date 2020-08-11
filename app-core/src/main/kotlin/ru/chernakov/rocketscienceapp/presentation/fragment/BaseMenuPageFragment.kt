package ru.chernakov.rocketscienceapp.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import ru.chernakov.rocketscienceapp.core.R

abstract class BaseMenuPageFragment : BaseFragment() {
    private var isBackPressedOnce = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isBackPressedOnce) {
                    requireActivity().finish()
                } else {
                    onBackPressedOnce()
                }
            }
        })
    }

    private fun onBackPressedOnce() {
        isBackPressedOnce = true
        Handler().postDelayed({ isBackPressedOnce = false }, BACK_PRESSED_DELAY)
        Toast.makeText(requireContext(), R.string.msg_click_back_to_exit, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val BACK_PRESSED_DELAY = 2000L
    }
}