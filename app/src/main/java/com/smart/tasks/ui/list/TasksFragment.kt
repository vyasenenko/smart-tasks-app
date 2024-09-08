package com.smart.tasks.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.smart.tasks.R
import com.smart.tasks.common.BindingFragment
import com.smart.tasks.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class TasksFragment : BindingFragment<FragmentTasksBinding>() {

    private val tasksViewModel by viewModels<TasksViewModel>()

    override val layout: Int
        get() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = tasksViewModel

        tasksViewModel.itemClick.observe(viewLifecycleOwner) { (view, item) ->
            val extras = FragmentNavigatorExtras(
                view to getString(R.string.card_details_transition)
            )
            nav.navigate(
                TasksFragmentDirections.actionTasksFragmentToTaskDetailsFragment(item), extras
            )
        }

        tasksViewModel.showTasks(requireContext())
    }
}