package ru.chernakov.feature_app_bubblegame.presentation.result

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.fragment_bubble_game_result.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.BubbleGameViewModel
import kotlin.math.hypot

class BubbleGameResultFragment : BaseFragment() {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()
    private val navigator: BubbleGameNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = when (bubbleGameViewModel.gameInteractor.status.value) {
            GameStatus.LOSS -> getString(R.string.game_result_lose)
            GameStatus.WIN -> getString(
                R.string.game_result_won,
                (bubbleGameViewModel.gameInteractor.passedTimeMs / MS_TO_SECONDS)
            )
            else -> getString(R.string.game_result_not_end)
        }
        tvResult.text = text

        bClose.setOnClickListener {
            bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.STOPPED)
            hideFragmentView()
        }

        showFragmentView()

        bubbleGameViewModel.gameInteractor.status.observe(viewLifecycleOwner, SafeObserver {
            when (it) {
                GameStatus.STOPPED -> {
                    navigator.openBubbleGameMenu()
                }
                else -> {
                }
            }
        })
    }

    private fun showFragmentView() {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view!!.post {
                val cx = view!!.width / 2
                val cy = view!!.height / 2
                val radius = hypot(cx.toDouble(), cy.toDouble())
                val animator =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, radius.toFloat())
                animator.duration = ANIMATION_DURATION
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
            val radius = hypot(cx.toDouble(), cy.toDouble())
            val animator =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, radius.toFloat(), 0f)
            animator.duration = ANIMATION_DURATION
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (view != null) {
                        view!!.visibility = View.INVISIBLE
                    }
                    bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.STOPPED)
                }
            })
            animator.start()
        } else {
            bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.STOPPED)
        }
    }

    override fun getLayout() = R.layout.fragment_bubble_game_result

    override fun obtainViewModel() = bubbleGameViewModel

    companion object {
        private const val ANIMATION_DURATION = 5_00L
        private const val MS_TO_SECONDS = 1000f
    }
}