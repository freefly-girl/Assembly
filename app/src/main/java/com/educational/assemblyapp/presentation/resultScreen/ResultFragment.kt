package com.educational.assemblyapp.presentation.resultScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.educational.assemblyapp.R
import com.educational.assemblyapp.databinding.ResultScreenBinding
import com.educational.assemblyapp.presentation.common.BaseFragment
import com.educational.assemblyapp.presentation.resultScreen.ResultState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : BaseFragment(R.layout.result_screen) {

    private val viewBinding by viewBinding(ResultScreenBinding::bind)
    private val viewModel by viewModels<ResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo: подключить адаптер
        val resultAdapter = ResultScreenAdapter(viewModel::onButtonClicked)
        with(viewBinding.resultList) {
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.videoState.observe(viewLifecycleOwner) { state: ResultState ->
            when (state) {
                is Error -> {
                    viewBinding.resultError.isVisible = true
                    viewBinding.resultProgress.isVisible = false
                    viewBinding.resultList.isVisible = false
                }
                is Loading -> {
                    viewBinding.resultError.isVisible = false
                    viewBinding.resultProgress.isVisible = true
                    viewBinding.resultList.isVisible = false
                }
                is Success -> {
                    viewBinding.resultError.isVisible = false
                    viewBinding.resultProgress.isVisible = false
                    viewBinding.resultList.isVisible = true
//                    resultAdapter.submitList(state.video)   // todo: подключить адаптер
                }
            }
        }

        viewBinding.filtersScreenBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewBinding.buttonResult.setOnClickListener {
//            parentFragmentManager.navigate(MainMenuFragment)   // TODO: home_screen
        }

        viewBinding.buttonResult.setBackgroundResource(R.drawable.rounded_corners)
    }
}
