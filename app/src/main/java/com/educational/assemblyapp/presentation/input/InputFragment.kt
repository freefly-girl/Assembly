package com.educational.assemblyapp.presentation.input

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.educational.assemblyapp.R
import com.educational.assemblyapp.databinding.InputScreenBinding
import com.educational.assemblyapp.presentation.common.BaseFragment
import com.educational.assemblyapp.presentation.common.navigate
import com.educational.assemblyapp.presentation.filtersScreen.FiltersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputFragment: BaseFragment(R.layout.input_screen) {

    private val viewBinding by viewBinding(InputScreenBinding::bind)
    private val viewModel by viewModels<InputViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.homeScreenBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewBinding.buttonInput.setOnClickListener {
            viewModel.onGoToFiltersClicked(
                title = viewBinding.titleField.editText?.text.toString(),
                text = viewBinding.textField.editText?.text.toString()
            )
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state: InputScreenState ->
            when (state) {
                is InputScreenState.Error -> {
                    viewBinding.inputErrorMessage.isVisible = true
                    viewBinding.inputScroll.isVisible = false
                    viewBinding.inputLoadingBar.isVisible = false
                }
                is InputScreenState.Initial -> {
                    viewBinding.inputErrorMessage.isVisible = false
                    viewBinding.inputScroll.isVisible = true
                    viewBinding.inputLoadingBar.isVisible = false
                }
                is InputScreenState.Loading -> {
                    viewBinding.inputErrorMessage.isVisible = false
                    viewBinding.inputScroll.isVisible = false
                    viewBinding.inputLoadingBar.isVisible = true
                }
                is InputScreenState.Success -> {

                    parentFragmentManager.navigate(FiltersFragment(state.videoProject))
                }
            }
        }


    }
}