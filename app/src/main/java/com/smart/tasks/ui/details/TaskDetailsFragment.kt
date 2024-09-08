package com.smart.tasks.ui.details

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.smart.tasks.R
import com.smart.tasks.common.BindingFragment
import com.smart.tasks.common.dpInt
import com.smart.tasks.common.showKeyboard
import com.smart.tasks.databinding.FragmentTaskDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TaskDetailsFragment : BindingFragment<FragmentTaskDetailsBinding>() {

    override val layout: Int
        get() = R.layout.fragment_task_details

    private val taskDetailsViewModel by viewModels<TaskDetailsViewModel>()

    private val id: String
        get() = TaskDetailsFragmentArgs.fromBundle(requireArguments()).id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        binding.viewModel = taskDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.back.setOnClickListener {
            nav.popBackStack()
        }

        taskDetailsViewModel.details.observe(viewLifecycleOwner) {

            startPostponedEnterTransition()
            transitionViews(showComment = it.comment != null)

            if (it.comment != null) lifecycleScope.launch {
                delay(300)
                binding.cardComment.isVisible = true
            }
        }

        taskDetailsViewModel.requestChangeStatus.observe(viewLifecycleOwner) {
            if (binding.cardComment.isVisible) {
                taskDetailsViewModel.changeStatus(it)
            } else {
                AlertDialog.Builder(requireContext()).setTitle(R.string.do_you_want_left_comment)
                    .setPositiveButton(R.string.yes) { d, i ->
                        binding.cardComment.isVisible = true
                        transitionViews(true)

                        lifecycleScope.launch {
                            delay(300)
                            binding.comment.requestFocus()
                            binding.comment.showKeyboard()
                        }
                    }.setNegativeButton(R.string.no) { d, i ->
                        taskDetailsViewModel.changeStatus(it)
                    }.show()
            }
        }

        taskDetailsViewModel.showTask(id)
    }

    private fun transitionViews(showComment: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.rootConstraint)
        if (showComment) {
            constraintSet.clear(binding.cardComment.id, ConstraintSet.BOTTOM)
            constraintSet.connect(
                binding.cardComment.id, ConstraintSet.TOP, binding.card.id, ConstraintSet.BOTTOM
            )
            constraintSet.setMargin(
                binding.cardComment.id, ConstraintLayout.LayoutParams.TOP, 16.dpInt
            )
        }
        extracted(binding.resolved.id, 20, constraintSet)
        extracted(binding.unresolved.id, 20, constraintSet)
        extracted(binding.imageStatus.id, 50, constraintSet)
        val transition: Transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 800
        TransitionManager.beginDelayedTransition(binding.rootConstraint, transition)
        constraintSet.applyTo(binding.rootConstraint)
    }

    private fun extracted(id: Int, marginTopDp: Int, constraintSet: ConstraintSet) {
        constraintSet.clear(id, ConstraintSet.TOP)
        constraintSet.connect(id, ConstraintSet.TOP, binding.cardComment.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(id, ConstraintLayout.LayoutParams.TOP, marginTopDp.dpInt)
    }
}