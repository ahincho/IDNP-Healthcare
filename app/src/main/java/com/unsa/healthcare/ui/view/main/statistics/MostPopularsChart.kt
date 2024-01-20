package com.unsa.healthcare.ui.view.main.statistics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class MostPopularsChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    private val arcPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val medications = listOf("Medicine A", "Medicine B", "Medicine C", "Medicine D")
    private val values = listOf(50f, 30f, 10f, 10f)
    init {
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 100f
        arcPaint.strokeCap = Paint.Cap.BUTT
        textPaint.color = Color.GRAY
        textPaint.textSize = 30f
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(width, height) / 2f) * 0.8f
        var startAngle = -90f
        for ((index, value) in values.withIndex()) {
            val sweepAngle = 360 * (value / values.sum())
            // Calculate the midpoint of the arc
            val midAngle = startAngle + sweepAngle / 2
            val textX = centerX + radius * 0.7f * Math.cos(Math.toRadians(midAngle.toDouble())).toFloat()
            val textY = centerY + radius * 0.7f * Math.sin(Math.toRadians(midAngle.toDouble())).toFloat()
            arcPaint.color = getColorForIndex(index)
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, startAngle, sweepAngle, false, arcPaint)
            drawMedicationText(canvas, medications[index], textX, textY)
            startAngle += sweepAngle
        }
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
        val colors = arrayOf(Color.MAGENTA, Color.GREEN, Color.BLUE, Color.YELLOW)
        return colors.getOrElse(index) { Color.GRAY }
    }
}