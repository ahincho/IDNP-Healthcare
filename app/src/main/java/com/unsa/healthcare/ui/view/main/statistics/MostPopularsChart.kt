package com.unsa.healthcare.ui.view.main.statistics

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import java.util.Random

class MostPopularsChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    private val arcPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animator: ValueAnimator? = null
    private var currentSweepAngle = 0f
    private val PIE_CHART_ANIMATION_DURATION = 650L
    companion object {
        var values = listOf(50f, 30f, 20f)
        val reviews = listOf("Excellent Review", "Average Review", "Poor Review")
    }
    init {
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 100f
        arcPaint.strokeCap = Paint.Cap.BUTT
        textPaint.color = Color.GRAY
        textPaint.textSize = 30f
        startPieChartAnimation()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(width, height) / 2f) * 0.8f
        var startAngle = -90f
        for ((index, value) in values.withIndex()) {
            if (currentSweepAngle >= startAngle) {
                // Calculate the midpoint of the arc
                val valueSweepAngle = currentSweepAngle * (value / values.sum())
                val midAngle = startAngle + valueSweepAngle / 2
                val textX = centerX + radius * 0.7f * Math.cos(Math.toRadians(midAngle.toDouble())).toFloat()
                val textY = centerY + radius * 0.7f * Math.sin(Math.toRadians(midAngle.toDouble())).toFloat()
                arcPaint.color = getColorForIndex(index)
                canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, startAngle, valueSweepAngle, false, arcPaint)
                drawMedicationText(canvas, reviews[index] + " " + value + "%", textX, textY)
                startAngle += valueSweepAngle
            }
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        startPieChartAnimation()
    }
    fun updateList(){
        values = generateRandomValues()
    }
    fun updateChart() {
        startPieChartAnimation()
    }
    private fun drawMedicationText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float
    ) {
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(
            text,
            x - textBounds.exactCenterX(),
            y - textBounds.exactCenterY(),
            textPaint
        )
    }
    private fun getColorForIndex(index: Int): Int {
        val colors = arrayOf(Color.MAGENTA, Color.GREEN, Color.YELLOW, Color.BLUE)
        return colors.getOrElse(index) { Color.GRAY }
    }
    private fun startPieChartAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = PIE_CHART_ANIMATION_DURATION
            interpolator = DecelerateInterpolator()
            addUpdateListener { valueAnimator ->
                currentSweepAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
        }
        animator?.start()
    }
    private fun generateRandomValues(): ArrayList<Float> {
        val random = Random()
        val totalPercentage = 100f
        val valueList = ArrayList<Float>()
        valueList.add(1f + random.nextFloat()*39)
        valueList.add(1f + random.nextFloat() * (totalPercentage - valueList.sumOf { it.toDouble() }).toFloat()*0.6f)
        valueList.add(totalPercentage - valueList.sumOf { it.toDouble() }.toFloat())
        return valueList
    }
}