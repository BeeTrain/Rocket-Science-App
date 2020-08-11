package ru.chernakov.rocketscienceapp.presentation.result

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_bubble_game_result.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.bubblegame.R
import ru.chernakov.rocketscienceapp.data.GameStatus
import ru.chernakov.rocketscienceapp.navigation.OnBackPressedListener
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameHostFragment
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameViewModel
import ru.chernakov.rocketscienceapp.presentation.widget.BubbleGameStateListener
import kotlin.math.hypot

class BubbleGameResultFragment : BaseFragment() {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()

    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var gameStateListener: BubbleGameStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = when (bubbleGameViewModel.gameInteractor.status) {
            GameStatus.LOSS -> getString(R.string.game_result_lose)
            GameStatus.WIN -> getString(
                R.string.game_result_won, bubbleGameViewModel.gameInteractor.passedTimeMs / MS_TO_SECONDS
            )
            else -> getString(R.string.game_result_not_end)
        }
        tvResult.text = text

        bClose.setOnClickListener {
            hideFragmentView()
        }

        showFragmentView()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideFragmentView()
            }
        })
    }

    private fun showFragmentView() {
        view?.let {
            it.post {
                val cx = it.width / 2
                val cy = requireView().height / 2
                val radius = hypot(cx.toDouble(), cy.toDouble())
                val animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, radius.toFloat())
                animator.duration = ANIMATION_DURATION
                it.visibility = View.VISIBLE
                animator.start()
            }
        }
    }

    private fun hideFragmentView() {
        view?.let {
            val cx = it.width / 2
            val cy = it.height / 2
            val radius = hypot(cx.toDouble(), cy.toDouble())
            val animator = ViewAnimationUtils.createCircularReveal(it, cx, cy, radius.toFloat(), 0f)
            animator.duration = ANIMATION_DURATION
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    it.visibility = View.INVISIBLE
                    gameStateListener.onSettingsReset()
                }
            })
            animator.start()
        }
    }

    override fun getLayout() = R.layout.fragment_bubble_game_result

    override fun obtainViewModel() = bubbleGameViewModel

    companion object {
        private const val ANIMATION_DURATION = 5_00L
        private const val MS_TO_SECONDS = 1000f

        fun newInstance(hostFragment: BubbleGameHostFragment): BubbleGameResultFragment {
            return BubbleGameResultFragment().apply {
                gameStateListener = hostFragment
                onBackPressedListener = hostFragment
            }
        }
    }
}