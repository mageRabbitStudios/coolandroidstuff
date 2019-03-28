package com.kinzlstanislav.mycircularreveal

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.graphics.toRectF

@Suppress("IMPLICIT_CAST_TO_ANY")
class MaskedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr), ValueAnimator.AnimatorUpdateListener {

    // Can't be private it is accessed by animators
    var radius: Float = 0f
    lateinit var bounds: Rect

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        if (::bounds.isInitialized) {
            val clipPath = Path()

            clipPath.addRoundRect(bounds.toRectF(), radius, radius, Path.Direction.CW)
            canvas.clipPath(clipPath)
        }

        super.onDraw(canvas)
    }

    fun startCircleToRectangleAnim() {

        // starting radius, would be good if converted from to dp to have similar results on different devices
        radius = 300f
        val cornerRadiusAnim =
            ObjectAnimator.ofFloat(
                this,
                "radius",
                0f
            )
        cornerRadiusAnim.addUpdateListener(this)

        val rectangleBoundsAnim = ObjectAnimator.ofObject(
            this,
            "bounds",
            // Evaluator decides what happens to animated objects during each update
            RectangleEvaluator(),
            // Rectangle in center with 0 width and 0 height
            Rect(width / 2, height / 2, width / 2, height / 2),
            // Final rectangle
            Rect(0, 0, width, height)
        )

        val circleToRectangleRevealAnimSet = AnimatorSet()
        circleToRectangleRevealAnimSet.playTogether(rectangleBoundsAnim, cornerRadiusAnim)
        circleToRectangleRevealAnimSet.duration = 550
        circleToRectangleRevealAnimSet.interpolator = DecelerateInterpolator()
        circleToRectangleRevealAnimSet.doOnEnd {
            cornerRadiusAnim.removeAllListeners()
        }
        circleToRectangleRevealAnimSet.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        // calls draw on every update of circleToRectangleRevealAnimSet
        invalidate()
    }

}