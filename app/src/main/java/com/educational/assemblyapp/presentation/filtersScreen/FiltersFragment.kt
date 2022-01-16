package com.educational.assemblyapp.presentation.filtersScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.educational.assemblyapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.educational.assemblyapp.databinding.FiltersScreenBinding
import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum.*
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum.*
import com.educational.assemblyapp.presentation.common.BaseFragment
import com.educational.assemblyapp.presentation.common.navigate
import com.educational.assemblyapp.presentation.resultScreen.ResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersFragment(
    sendedVideoProject: Video
) : BaseFragment(R.layout.filters_screen) {

    private val viewBinding by viewBinding(FiltersScreenBinding::bind)
    private val viewModel by viewModels<FiltersViewModel>()

    private var typeVideoQuality: String = ALL.name
    private var frameRates: String = FPS_25.name

    private val videoProject: Video = sendedVideoProject

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.inputScreenBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.videoQualityState.observe(viewLifecycleOwner) { state ->
            typeVideoQuality = when (state) {
                HD -> HD.name
                ULTRA_HD -> ULTRA_HD.name
                ALL -> ALL.name
            }
        }

        viewModel.frameRates.observe(viewLifecycleOwner) { state ->
            frameRates = when (state) {
                FPS_24 -> FPS_24.name
                FPS_25 -> FPS_25.name
                FPS_30 -> FPS_30.name
                FPS_50 -> FPS_50.name
                FPS_60 -> FPS_60.name
            }
        }

        viewBinding.buttonFilters.setOnClickListener {
            viewModel.onClickedGoToResults(
                keywords = viewBinding.textFieldKey.editText?.text.toString(),
                quality = viewBinding.spinner1.selectedItem.toString(),
                fps = viewBinding.spinner2.selectedItem.toString()
            )
//            nextScreen()
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state: FiltersState ->
            when (state) {
                is FiltersState.Initial -> {
                    viewBinding.filtersError.isVisible = false
                    viewBinding.filtersProgress.isVisible = false
                }
                is FiltersState.Error -> {
                    viewBinding.filtersError.isVisible = true
                    viewBinding.filtersProgress.isVisible = false
                }
                is FiltersState.Loading -> {
                    viewBinding.filtersError.isVisible = false
                    viewBinding.filtersProgress.isVisible = true
                }
                is FiltersState.Success -> {
                    parentFragmentManager.navigate(ResultFragment(videoProject, state.result))
                }
            }
        }

        viewBinding.buttonFilters.setBackgroundResource(R.drawable.rounded_corners)

    }

}