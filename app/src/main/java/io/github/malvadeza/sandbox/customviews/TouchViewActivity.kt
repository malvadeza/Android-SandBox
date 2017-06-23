package io.github.malvadeza.sandbox.customviews

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.malvadeza.sandbox.R

class TouchViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_view_activity)
    }

    override fun onBackPressed() {
        setResultAndFinish()
    }

    fun setResultAndFinish() {
        val position = intent.extras.getInt("POSITION_ON_RV")

        val resultData = Intent()
        resultData.putExtra("POSITION_ON_RV", position)
        setResult(Activity.RESULT_OK, resultData)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        }
    }

}