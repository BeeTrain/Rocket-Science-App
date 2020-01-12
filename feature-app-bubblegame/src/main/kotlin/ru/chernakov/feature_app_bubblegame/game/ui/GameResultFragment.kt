package ru.chernakov.feature_app_bubblegame.game.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_game_result.*
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.game.game.Game

/**
 * @author mikhail.funikov@e-legion.com on 11/06/2017.
 */

class GameResultFragment : GameFragment() {
    companion object {
        fun newInstance(): GameResultFragment {
            return GameResultFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = Game.getInstance()
        val text = when (game?.status) {
            GameStatus.LOSS -> getString(R.string.game_result_loss_title)
            GameStatus.WIN -> getString(R.string.game_result_winn_title, game.passedTimeMs / 1000f)
            else -> getString(R.string.game_result_not_end)

        }
        tvResult.text = text

        bClose.setOnClickListener {
            Game.resetParams()
            hideFragmentView()
        }

        showFragmentView()
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
                    gameParamsCallback.onParamsReset()
                }
            })
            animator.start()
        } else {
            gameParamsCallback.onParamsReset()
        }
    }
}