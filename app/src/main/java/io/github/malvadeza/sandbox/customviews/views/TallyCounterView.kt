package io.github.malvadeza.sandbox.customviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import io.github.malvadeza.sandbox.R

class TallyCounterView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var count = 0
        set(value) {
            field = Math.min(value, 9999)
            displayedCount = "%04d".format(field)
            invalidate()
        }

    val backgroundRect = RectF()
    val cornerRadius = Math.round(2f * resources.displayMetrics.density).toFloat()

    var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var numberPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private var displayedCount = "0000"

    init {
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        linePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        linePaint.strokeWidth = 1f

        numberPaint.color = ContextCompat.getColor(context, android.R.color.white)
        numberPaint.textSize = Math.round(64f * resources.displayMetrics.scaledDensity).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("TallyCounterView", "onDraw ${canvas.width} <> ${canvas.height}")

        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        val centerX = canvasWidth * 0.5

        backgroundRect.set(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat())
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint)

        val baselineY = Math.round(canvasHeight * 0.6f).toFloat()
        canvas.drawLine(0f, baselineY, canvasWidth.toFloat(), baselineY, linePaint)

        val textWidth = numberPaint.measureText(displayedCount)
        val textX = Math.round(centerX - textWidth * 0.5f).toFloat()

        canvas.drawText(displayedCount, textX, baselineY, numberPaint)
    }

    fun reset() {
        count = 0
    }

    fun increment() {
        count += 1
    }
}
