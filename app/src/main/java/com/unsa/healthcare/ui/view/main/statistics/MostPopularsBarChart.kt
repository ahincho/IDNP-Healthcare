package com.unsa.healthcare.ui.view.main.statistics

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.unsa.healthcare.R

class MostPopularsBarChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    data class Bar(val height: Float, val color: Int)
    private val paint = Paint()
    private var barWidth = 0f
    private var barSpacing = 0f
    private var animator: ValueAnimator? = null
    private var currentBarHeight = 0f
    private var maxBarHeight = 0f
    private var maxHeightCanvas = 0f
    private var BAR_CHART_ANIMATION_DURATION = 650L
    private var barChartList = mutableListOf<Bar>()
    private var barLabels = mutableListOf<String>()
    private val labelTextSize = resources.getDimensionPixelSize(R.dimen.bar_chart_label_text_size).toFloat()
    init {
        paint.isAntiAlias = true
        paint.textSize = labelTextSize
        computeBars()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBarChart(canvas)
    }
    private fun drawBarChart(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        var left = barSpacing
        for ((index, bar) in barChartList.withIndex()) {
            paint.color = bar.color
            val scaledBarHeight = bar.height * (maxHeightCanvas / maxBarHeight)
            val barHeight = (scaledBarHeight / maxBarHeight * currentBarHeight)
            val labelX = left + barWidth / 2 - 50
            val labelY = height.toFloat() - 15 // Espaciado para las etiquetas
            val valueReview = MostPopularsChart.reviews[index].split(" ")[0]
            canvas.drawRect(left, height - barHeight, left + barWidth, height.toFloat(), paint)
            paint.color = Color.BLACK
            canvas.drawText(barLabels[index], labelX, labelY-50, paint)
            canvas.drawText(if (valueReview == "Poor") "   Poor" else valueReview, labelX-25, labelY, paint)
            left += barWidth + barSpacing
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        computeBars()
    }
    fun updateChart() {
        this.barChartList = generateBars(MostPopularsChart.values,3)
        computeBars()
    }
    private fun computeBars() {
        if (barChartList.isEmpty()) return
        maxBarHeight = barChartList.maxByOrNull { it.height }?.height ?: 0f
        // Altura máxima permitida en el canvas (ajustada a la altura de la vista)
        maxHeightCanvas = height.toFloat() * 80 / 100
        // Factor de escala basado en la altura máxima permitida en el canvas
        val scale = if (maxBarHeight > maxHeightCanvas) maxHeightCanvas / maxBarHeight else 1f
        val totalBars = barChartList.size
        barWidth = (width - (totalBars + 1) * barSpacing) / totalBars
        // Aplicamos el factor de escala a la altura máxima actual de las barras
        currentBarHeight = maxBarHeight * scale
        barLabels.clear()
        barChartList.forEach { bar ->
            val labelText = String.format("%.2f", bar.height)
            barLabels.add(labelText)
        }
        startBarChartAnimation()
    }
    private fun getColorForIndex(index: Int): Int {
        val colors = arrayOf(Color.MAGENTA, Color.GREEN, Color.CYAN, Color.YELLOW)
        return colors.getOrElse(index) { Color.GRAY }
    }
    private fun startBarChartAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = BAR_CHART_ANIMATION_DURATION
            interpolator = DecelerateInterpolator()
            addUpdateListener { valueAnimator ->
                currentBarHeight = valueAnimator.animatedValue as Float * maxBarHeight
                invalidate()
            }
        }
        animator?.start()
    }
    private fun generateBars(values: List<Float>, numBars: Int): ArrayList<Bar> {
        val barList = ArrayList<Bar>()
        for (i in 0 until numBars) {
            barList.add(Bar(values[i], getColorForIndex(i)))
        }
        return barList
    }
}