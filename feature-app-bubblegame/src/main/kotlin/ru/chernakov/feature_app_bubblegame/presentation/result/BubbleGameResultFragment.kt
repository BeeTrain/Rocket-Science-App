package ru.chernakov.feature_app_bubblegame.presentation.result

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_bubble_game_result.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.navigation.OnBackPressedListener
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameHostFragment
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameViewModel
import ru.chernakov.feature_app_bubblegame.presentation.widget.BubbleGameStateListener

class BubbleGameResultFragment : BaseFragment() {
    private val resultViewModel: BubbleGameViewModel by viewModel()

    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var gameStateListener: BubbleGameStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = when (resultViewModel.gameInteractor.status) {
            GameStatus.LOSS -> getString(R.string.game_result_lose)
            GameStatus.WIN -> getString(R.string.game_result_won, resultViewModel.gameInteractor.passedTimeMs / 1000f)
            else -> getString(R.string.game_result_not_end)

        }
        tvResult.text = text

        bClose.setOnClickListener {
            hideFragmentView()
        }

        showFragmentView()

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideFragmentView()
            }
        })
    }

    private fun showFragmentView() {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view!!.post {
                val cx = view!!.width / 2
                val cy = view!!.height / 2
                val radius = Math.hypot(cx.toDouble(), cy.toDouble())
                val animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, radius.toFloat())
                animator.duration = 6_00
                view!!.visibility = View.VISIBLE
                animator.start()
            }
        } else {
            view!!.visibility = View.VISIBLE
        }
    }

    private fun hideFragmentView() {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = view!!.width / 2
            val cy = view!!.height / 2
            val radius = Math.hypot(cx.toDouble(), cy.toDouble())
            val animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius.toFloat(), 0f)
            animator.duration = 5_00
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (view != null) {
                        view!!.visibility = View.INVISIBLE
                    }
                    gameStateListener.onSettingsReset()
                }
            })
            animator.start()
        } else {
            gameStateListener.onSettingsReset()
        }
    }

    override fun getLayout() = R.layout.fragment_bubble_game_result

    override fun obtainViewModel() = resultViewModel

    companion object {
        fun newInstance(hostFragment: BubbleGameHostFragment): BubbleGameResultFragment {
            return BubbleGameResultFragment().apply {
                gameStateListener = hostFragment
                onBackPressedListener = hostFragment
            }
        }
    }
}