package com.smart.tasks.ui.splash

import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import com.smart.tasks.R
import com.smart.tasks.common.BindingFragment
import com.smart.tasks.common.dpInt
import com.smart.tasks.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BindingFragment<FragmentSplashBinding>() {

    override val layout: Int
        get() = R.layout.fragment_splash

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(100)
            transitionViews()
            delay(1200)
            nav.navigate(SplashFragmentDirections.actionSplashFragmentToTasksFragment())
        }
    }

    private fun transitionViews() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.rootConstraint)
        constraintSet.clear(binding.introImage.id, ConstraintSet.TOP)
        constraintSet.connect(
            binding.introImage.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.setMargin(binding.introImage.id, ConstraintSet.BOTTOM, (-15).dpInt)
        val transition: Transition = AutoTransition()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1000
        TransitionManager.beginDelayedTransition(binding.rootConstraint, transition)
        constraintSet.applyTo(binding.rootConstraint)
    }
}