package io.github.malvadeza.sandbox.customviews

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.widget.Button
import io.github.malvadeza.sandbox.R
import io.github.malvadeza.sandbox.customviews.views.TallyCounterView

class TallyCounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val counterView = findViewById(R.id.counter_view) as TallyCounterView

        val clickMe = findViewById(R.id.click_me) as Button
        clickMe.setOnClickListener {
            counterView.increment()
        }

        val incrementer = Incrementer(counterView)

        clickMe.setOnLongClickListener {
            incrementer.incrementing = true
            true    // Should be true, otherwise it doesn't capture the touch and as consequence
            // the view clickListener would be fired
        }

        clickMe.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                    && incrementer.incrementing) {
                incrementer.incrementing = false
            }

            false
        }
    }

    // Could be inside TallyCounterView
    class Incrementer(val counterView: TallyCounterView) : Runnable {
        private var handler = Handler()

        var incrementing = false
            set(value) {
                if (!value)
                    handler.removeCallbacks(this)
                else if (!field)
                    handler.post(this)

                field = value
            }

        override fun run() {
            if (incrementing) {
                counterView.increment()
                handler.postDelayed(this, 500)
            }
        }


    }

}