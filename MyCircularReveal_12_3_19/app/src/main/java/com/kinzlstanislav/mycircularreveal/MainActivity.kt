package com.kinzlstanislav.mycircularreveal

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        space1.setOnClickListener {
            // region ANIMATION 1 - circular with decelerate
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                space1.visibility = View.GONE


                val cx = space1.width / 2
                val cy = space1.height / 2

                val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

                val anim = ViewAnimationUtils.createCircularReveal(space1, cx, cy, 0f, finalRadius)

                space1.visibility = View.VISIBLE
                anim.duration = 660
                anim.interpolator = DecelerateInterpolator()
                anim.start()
            }
            // endregion
        }

        space2.setOnClickListener {
            // region ANIMATION 2 - from right
            val local = Rect()
            space2.getLocalVisibleRect(local)
            val from = Rect(local)
            val to = Rect(local)

            from.right = from.right + local.width() / 2
            from.bottom = from.top + local.height() / 2
            from.top = from.bottom
            from.left = from.right

            val anim = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                ObjectAnimator.ofObject(
                    space2,
                    "clipBounds",
                    RectangleEvaluator(),
                    from, to
                )
            } else {
                TODO("VERSION.SDK_INT < JELLY_BEAN_MR2")
            }

            anim.duration = 660
            anim.interpolator = DecelerateInterpolator()
            anim.start()
            // endregion
        }

        space3.setOnClickListener {
            // region ANIMATION 3 Rectangular reveal
            val local = Rect()
            space3.getLocalVisibleRect(local)
            val from = Rect(local)
            val to = Rect(local)

            from.right = from.left + local.width() / 2
            from.left = from.right

            from.top = from.bottom - local.height() / 2
            from.bottom = from.top

            val anim = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                ObjectAnimator.ofObject(
                    space3,
                    "clipBounds",
                    RectangleEvaluator(),
                    from, to
                )
            } else {
                TODO("VERSION.SDK_INT < JELLY_BEAN_MR2")
            }

            anim.duration = 660
            anim.interpolator = DecelerateInterpolator()
            anim.start()
            // endregion
        }

        space4.setOnClickListener {
            space4.startCircleToRectangleAnim()
        }

    }

}
