package io.github.malvadeza.sandbox.customviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import io.github.malvadeza.sandbox.R

class TouchView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val touchPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val touchHaloPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Math.round(2f * resources.displayMetrics.density).toFloat()
    }
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect = RectF()
    private val circles = mutableListOf<PointF>()
    private val pointers = SparseArray<PointF>()
    private val radius = Math.round(35f * resources.displayMetrics.density).toFloat()
    private val radiusHalo = Math.round(45f * resources.displayMetrics.density).toFloat()

    private val invalidRect = RectF()

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TouchView, 0, 0)
        val touchColor = a.getColor(R.styleable.TouchView_tv_touchColor,
                ContextCompat.getColor(context, android.R.color.white))
        val backgroundColor = a.getColor(R.styleable.TouchView_tv_backgroundColor,
                ContextCompat.getColor(context, android.R.color.holo_blue_dark))

        a.recycle()

        touchPaint.color = touchColor
        touchHaloPaint.color = touchColor
        backgroundPaint.color = backgroundColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        backgroundRect.set(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat())
        canvas.drawRect(backgroundRect, backgroundPaint)

        for (circle in circles) {
            canvas.drawCircle(circle.x, circle.y, radius, touchPaint)
            canvas.drawCircle(circle.x, circle.y, radiusHalo, touchHaloPaint)
        }

        for (i in 0..pointers.size() - 1) {
            val key = pointers.keyAt(i)
            val circle = pointers.get(key)

            canvas.drawCircle(circle.x, circle.y, radius, touchPaint)
            canvas.drawCircle(circle.x, circle.y, radiusHalo, touchHaloPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val maskedAction = event.actionMasked

        when (maskedAction) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)

                val point = PointF(event.getX(pointerIndex), event.getY(pointerIndex))
                pointers.put(pointerId, point)
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0..event.pointerCount - 1) {
                    val pointerId = event.getPointerId(i)
                    val point = pointers.get(pointerId)
                    point.x = event.getX(i)
                    point.y = event.getY(i)

                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP -> {
                Log.d("TouchView", "Something is not quite right")
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)
                val pointer = pointers.get(pointerId)
                pointers.remove(pointerId)

                circles.add(pointer)
            }
            else -> {
                Log.d("TouchView", "Else")
                return false
            }
        }

//        Log.d("TouchView", "Pointers -> ${event.pointerCount}")

        invalidate()

        return true
    }
}