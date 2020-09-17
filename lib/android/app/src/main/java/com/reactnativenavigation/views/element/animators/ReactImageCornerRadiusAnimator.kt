package com.reactnativenavigation.views.element.animators

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.view.View
import androidx.core.animation.doOnEnd
import com.facebook.react.views.image.ReactImageView
import com.reactnativenavigation.options.SharedElementTransitionOptions
import com.reactnativenavigation.utils.RoundCornersOutlineProvider
import com.reactnativenavigation.utils.RoundingParams
import com.reactnativenavigation.utils.getCornerRadius
import kotlin.math.min

class ReactImageCornerRadiusAnimator(from: View, to: View) : PropertyAnimatorCreator<ReactImageView>(from, to) {
    override fun shouldAnimateProperty(fromChild: ReactImageView, toChild: ReactImageView): Boolean {
        return fromChild.getCornerRadius() != toChild.getCornerRadius()
    }

    override fun create(options: SharedElementTransitionOptions): Animator {
        to as ReactImageView; from as ReactImageView
        val outlineProvider = RoundCornersOutlineProvider(to, from.getCornerRadius())
        setInitialOutline(to, outlineProvider)

        return ObjectAnimator.ofObject(
                CornerRadiusEvaluator { outlineProvider.updateRadius(it) },
                from.getCornerRadius(),
                to.getCornerRadius()
        ).apply {
            setInitialCornerRadiusAfterAnimatorIsCreated(to, from)
            doOnEnd { to.hierarchy.roundingParams = RoundingParams.fromCornersRadii(outlineProvider.radius) }
        }
    }

    private fun setInitialOutline(to: ReactImageView, provider: RoundCornersOutlineProvider) {
        to.outlineProvider = provider
        to.clipToOutline = true
        to.invalidateOutline()
    }

    private fun setInitialCornerRadiusAfterAnimatorIsCreated(to: ReactImageView, from: ReactImageView) {
        to.setFadeDuration(0)
        to.setBorderRadius(min(from.getCornerRadius(), to.getCornerRadius()))
        to.maybeUpdateView()
    }
}
