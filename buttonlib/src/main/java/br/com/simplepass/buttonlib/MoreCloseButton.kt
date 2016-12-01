package br.com.simplepass.buttonlib

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton

/**
 * Created by hinovamobile on 30/11/16.
 */
class MoreCloseButton : ImageButton {
    private var mGradientDrawable: GradientDrawable? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(23)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        if (attrs == null) {
            mGradientDrawable = ResourcesCompat.getDrawable(resources, R.drawable.button_shape, null) as GradientDrawable?
        } else {
            val attrsArray = intArrayOf(android.R.attr.background)// 0

            val typedArrayBG = context.obtainStyledAttributes(attrs, attrsArray)

            try {
                mGradientDrawable = typedArrayBG.getDrawable(0) as GradientDrawable

            } catch (e: ClassCastException) {
                val drawable = typedArrayBG.getDrawable(0)

                if (drawable is ColorDrawable) {
                    mGradientDrawable = GradientDrawable()
                    mGradientDrawable!!.setColor(drawable.color)
                } else if (drawable is StateListDrawable) {

                    try {
                        mGradientDrawable = drawable.current as GradientDrawable
                    } catch (e1: ClassCastException) {
                        throw RuntimeException("Error reading background... Use a shape or a color in xml!", e1.cause)
                    }

                }
            }


            typedArrayBG.recycle()
        }

        background = mGradientDrawable
    }

    private fun setupAnimations(){

    }

    fun morthToCloseButton() {
        mGradientDrawable?.setStroke(7, Color.BLACK)
        background = mGradientDrawable

        val rotateAnimation = ValueAnimator.ofFloat(0f, 135f)
        rotateAnimation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            rotation = value
        }

        val cornerAnimation = ObjectAnimator.ofFloat(mGradientDrawable,
                "cornerRadius",
                0f,
                2000f)

        val widthAnimation = ValueAnimator.ofInt(width, 180)
        widthAnimation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = value
            setLayoutParams(layoutParams)
        }

        val heightAnimation = ValueAnimator.ofInt(height, 180)
        heightAnimation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.height = value
            setLayoutParams(layoutParams)
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = 500
        animatorSet.playTogether(rotateAnimation, cornerAnimation, widthAnimation, heightAnimation)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }


}
