package io.github.malvadeza.sandbox.fragmenttransition

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.malvadeza.sandbox.R

class FragmentTransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_transition_activity)

        supportFragmentManager.beginTransaction()
                .add(R.id.fl_content_frame, FragmentTransitionFragment.newInstance())
                .commit()
    }

}