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
import android.widget.TextView
import io.github.malvadeza.sandbox.R
import timber.log.Timber

class TallyCounterView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var count = 0
        set(value) {
            field = Math.min(value, 9999)
            invalidate()
        }

    val backgroundRect = RectF()
    var cornerRadius = 0f

    var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var baselinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var ascDescLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var topBotLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    var fontMetrics: Paint.FontMetrics

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TallyCounterView, 0, 0)

        val lineWidth = a.getDimensionPixelSize(R.styleable.TallyCounterView_tcv_lineWidth,
                Math.round(1f * resources.displayMetrics.density)).toFloat()

        backgroundPaint.color = a.getColor(R.styleable.TallyCounterView_tcv_backgroundColor,
                ContextCompat.getColor(context, R.color.colorPrimary))
        cornerRadius = a.getDimensionPixelSize(R.styleable.TallyCounterView_tcv_borderRadius,
                Math.round(4f * resources.displayMetrics.density)).toFloat()

        baselinePaint.color = a.getColor(R.styleable.TallyCounterView_tcv_baselineColor,
                ContextCompat.getColor(context, R.color.colorAccent))
        baselinePaint.strokeWidth = lineWidth

        ascDescLinePaint.color = a.getColor(R.styleable.TallyCounterView_tcv_ascDescLineColor,
                ContextCompat.getColor(context, android.R.color.holo_blue_bright))
        ascDescLinePaint.strokeWidth = lineWidth

        topBotLinePaint.color = a.getColor(R.styleable.TallyCounterView_tcv_ascDescLineColor,
                ContextCompat.getColor(context, android.R.color.holo_green_light))
        topBotLinePaint.strokeWidth = lineWidth

        textPaint.color = a.getColor(R.styleable.TallyCounterView_tcv_textColor,
                ContextCompat.getColor(context, android.R.color.white))
        textPaint.textSize = a.getDimensionPixelSize(R.styleable.TallyCounterView_tcv_textSize,
                Math.round(64f * resources.displayMetrics.scaledDensity)).toFloat()

        fontMetrics = textPaint.fontMetrics



        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("TallyCounterView", "onDraw ${canvas.width} <> ${canvas.height}")

        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        val centerX = canvasWidth * 0.5

        backgroundRect.set(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat())
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint)

        val baselineY = Math.round(canvasHeight * 0.6f).toFloat()

        val textWidth = textPaint.measureText("%04d".format(count))
        val textX = Math.round(centerX - textWidth * 0.5f).toFloat()

        canvas.drawText("%04d".format(count), textX, baselineY, textPaint)
        canvas.drawLine(0f, baselineY, canvasWidth.toFloat(), baselineY, baselinePaint)
        canvas.drawLine(0f, baselineY + fontMetrics.ascent, canvasWidth.toFloat(), baselineY + fontMetrics.ascent, ascDescLinePaint)
        canvas.drawLine(0f, baselineY + fontMetrics.descent, canvasWidth.toFloat(), baselineY + fontMetrics.descent, ascDescLinePaint)
        canvas.drawLine(0f, baselineY + fontMetrics.top, canvasWidth.toFloat(), baselineY + fontMetrics.top, topBotLinePaint)
        canvas.drawLine(0f, baselineY + fontMetrics.bottom, canvasWidth.toFloat(), baselineY + fontMetrics.bottom, topBotLinePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Timber.d("onMeasure width -> ${MeasureSpec.toString(widthMeasureSpec)}")
        Timber.d("onMeasure height -> ${MeasureSpec.toString(heightMeasureSpec)}")

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    fun reset() {
        count = 0
    }

    fun increment() {
        count += 1
    }
}
