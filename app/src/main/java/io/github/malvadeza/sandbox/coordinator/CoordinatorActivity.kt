package io.github.malvadeza.sandbox.coordinator

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.malvadeza.sandbox.R

class CoordinatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        val collapsingToolbar = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
//        collapsingToolbar.setExpandedTitleColor(android.R.color.transparent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}