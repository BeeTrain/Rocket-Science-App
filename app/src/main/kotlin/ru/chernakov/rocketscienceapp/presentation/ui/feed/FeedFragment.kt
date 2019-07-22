package ru.chernakov.rocketscienceapp.presentation.ui.feed

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.chat.ChatFragment


class FeedFragment : BaseFragment() {
    private val viewModel: FeedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btChat.setOnClickListener { startFragment(ChatFragment.newInstance()) }
    }

    override fun getLayout(): Int = R.layout.fragment_feed

    override fun obtainViewModel(): BaseViewModel = viewModel

    companion object {
        fun newInstance() = FeedFragment()
    }
}