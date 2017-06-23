package io.github.malvadeza.sandbox.customviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import io.github.malvadeza.sandbox.R
import io.github.malvadeza.sandbox.customviews.views.TallyCounterView

class TallyCounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val counter_view = findViewById(R.id.counter_view) as TallyCounterView

        val click_me = findViewById(R.id.click_me) as Button
        click_me.setOnClickListener {
            counter_view.increment()
        }
    }

}