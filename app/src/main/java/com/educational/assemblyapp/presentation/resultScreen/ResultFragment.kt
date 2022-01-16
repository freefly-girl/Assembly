package com.educational.assemblyapp.presentation.resultScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.educational.assemblyapp.R
import com.educational.assemblyapp.databinding.ResultScreenBinding
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.domain.entity.VideoSearch
import com.educational.assemblyapp.presentation.common.BaseFragment
import com.educational.assemblyapp.presentation.common.navigate
import com.educational.assemblyapp.presentation.mainMenu.MainMenuFragment
import com.educational.assemblyapp.presentation.resultScreen.ResultState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment(
    sendedVideoProject: Video,
    result: List<VideoSearch>
) : BaseFragment(R.layout.result_screen) {

    private val viewBinding by viewBinding(ResultScreenBinding::bind)
    private val viewModel by viewModels<ResultViewModel>()

    private val videoProject = sendedVideoProject

    private val resultVideo = result

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultAdapter = ResultScreenAdapter(viewModel::onButtonClicked)
        with(viewBinding.resultList) {
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewBinding.searchQuery.text = videoProject.searchReq

        resultAdapter.submitList(resultVideo)

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
                is AllIsOk -> {
                    viewBinding.resultError.isVisible = false
                    viewBinding.resultProgress.isVisible = false
                    viewBinding.resultList.isVisible = true
                }
                is Success -> {
                    parentFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
                }
            }
        }

        viewBinding.filtersScreenBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewBinding.buttonResult.setOnClickListener {
            viewModel.onFinishingButtonClicked(videoProject)

        }

        viewBinding.buttonResult.setBackgroundResource(R.drawable.rounded_corners)
    }
}
