package io.github.malvadeza.sandbox.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import io.github.malvadeza.sandbox.R
import timber.log.Timber


class AnimationTransitionActivity : AppCompatActivity() {

    val button1 by lazy { findViewById(R.id.btn_button1) }
    val button2 by lazy { findViewById(R.id.btn_button2) }
    val button3 by lazy { findViewById(R.id.btn_button3) }
    val buttons by lazy { findViewById(R.id.ll_buttons) }
    val fab by lazy { findViewById(R.id.fab_clickMe) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_transition_activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.enterTransition.removeListener(this)
                    }

                    fab.visibility = View.INVISIBLE
                    val cx = (buttons.left + buttons.right) / 2
                    val cy = (buttons.bottom - buttons.top) / 2

                    val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble())

                    Timber.d("left ${buttons.left} <> right ${buttons.right}")
                    Timber.d("top ${buttons.top} <> bottom ${buttons.bottom}")

                    Timber.d("Center -> ($cx, $cy)")
                    Timber.d("finalRadius -> $finalRadius")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val anim = ViewAnimationUtils.createCircularReveal(buttons, cx, cy, 0f, finalRadius.toFloat())
//                        anim.duration = 15000
                        anim.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                fab.visibility = View.INVISIBLE

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    TransitionManager.beginDelayedTransition(buttons as ViewGroup)
                                }
                                button1.visibility = View.VISIBLE
                                button2.visibility = View.VISIBLE
                                button3.visibility = View.VISIBLE
                            }
                        })

                        buttons.visibility = View.VISIBLE
                        anim.start()
                    }
                }

                override fun onTransitionResume(transition: Transition?) {

                }

                override fun onTransitionPause(transition: Transition?) {

                }

                override fun onTransitionCancel(transition: Transition?) {

                }

                override fun onTransitionStart(transition: Transition?) {

                }
            })

            window.returnTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                }

                override fun onTransitionStart(transition: Transition?) {
                }
            })
        }
    }

    override fun onBackPressed() {
        Timber.d("onBackPressed")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = (buttons.left + buttons.right) / 2
            val cy = (buttons.bottom - buttons.top) / 2

            val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble())

            val anim = ViewAnimationUtils.createCircularReveal(buttons, cx, cy, initialRadius.toFloat(), 0f)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    fab.visibility = View.VISIBLE
                    buttons.visibility = View.INVISIBLE

                    super@AnimationTransitionActivity.onBackPressed()
                }
            })
            anim.start()
        } else
            super.onBackPressed()
    }

}