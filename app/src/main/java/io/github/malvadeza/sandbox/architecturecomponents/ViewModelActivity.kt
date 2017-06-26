package io.github.malvadeza.sandbox.architecturecomponents

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.github.malvadeza.sandbox.R
import io.github.malvadeza.sandbox.customviews.views.TallyCounterView
import io.github.malvadeza.sandbox.plusAssign
import io.reactivex.disposables.CompositeDisposable

class ViewModelActivity : AppCompatActivity() {

    val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_activity)

        val counterView = findViewById(R.id.counter_view) as TallyCounterView
        val counterViewModel = ViewModelProviders
                .of(this)
                .get(CounterViewModel::class.java)

        subscriptions += counterViewModel.get().subscribe {
            counterView.count = it
        }

        val clickMe = findViewById(R.id.click_me) as Button
        clickMe.setOnClickListener {
            counterViewModel.increase()
        }
    }

    override fun onStop() {
        super.onStop()

        subscriptions.clear()
    }

}