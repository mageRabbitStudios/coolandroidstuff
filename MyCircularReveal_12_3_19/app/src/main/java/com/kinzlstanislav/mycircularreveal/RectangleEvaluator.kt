package com.kinzlstanislav.mycircularreveal

import android.animation.TypeEvaluator
import android.graphics.Rect

class RectangleEvaluator : TypeEvaluator<Rect> {

    override fun evaluate(fraction: Float, startValue: Rect, endValue: Rect): Rect {

        val left = startValue.left + ((endValue.left - startValue.left) * fraction).toInt()
        val top = startValue.top + ((endValue.top - startValue.top) * fraction).toInt()
        val right = startValue.right + ((endValue.right - startValue.right) * fraction).toInt()
        val bottom = startValue.bottom + ((endValue.bottom - startValue.bottom) * fraction).toInt()

        return Rect(left, top, right, bottom)
    }
}
