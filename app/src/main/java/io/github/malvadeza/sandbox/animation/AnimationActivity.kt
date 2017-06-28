package io.github.malvadeza.sandbox.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewAnimationUtils
import io.github.malvadeza.sandbox.R

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val button1 = findViewById(R.id.btn_button1)
        val button2 = findViewById(R.id.btn_button2)
        val button3 = findViewById(R.id.btn_button3)
        val buttons = findViewById(R.id.ll_buttons)

        val clickMe = findViewById(R.id.fab_clickMe)

        var isHidden = true

        clickMe.setOnClickListener { view ->
            val cx = view.x.toInt() + view.width / 2
            val cy = view.y.toInt() - view.height / 2

            val initialRadius = if (isHidden) .0 else Math.hypot(cx.toDouble(), cy.toDouble())
            val finalRadius = if (isHidden) Math.hypot(cx.toDouble(), cy.toDouble()) else .0

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val anim = ViewAnimationUtils.createCircularReveal(buttons, cx, cy, initialRadius.toFloat(), finalRadius.toFloat())

                if (isHidden)
                    buttons.visibility = View.VISIBLE
                else
                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            buttons.visibility = View.INVISIBLE
                        }
                    })

                anim.start()
                isHidden = !isHidden
            }

        }
    }

}