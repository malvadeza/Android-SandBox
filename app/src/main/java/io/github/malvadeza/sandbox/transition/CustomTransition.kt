package io.github.malvadeza.sandbox.transition

import android.animation.Animator
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Fade
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup

@RequiresApi(Build.VERSION_CODES.KITKAT)
class CustomTransition(context: Context, attrs: AttributeSet) : Fade(context, attrs) {

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
    }

    override fun createAnimator(sceneRoot: ViewGroup?, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        return super.createAnimator(sceneRoot, startValues, endValues)
    }

}