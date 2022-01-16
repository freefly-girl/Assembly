package com.educational.assemblyapp.presentation.mainMenu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.educational.assemblyapp.R
import com.educational.assemblyapp.databinding.MainMenuScreenBinding
import com.educational.assemblyapp.presentation.common.BaseFragment
import com.educational.assemblyapp.presentation.common.navigate
import com.educational.assemblyapp.presentation.input.InputFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainMenuFragment : BaseFragment(R.layout.main_menu_screen) {
    private val viewBinding by viewBinding(MainMenuScreenBinding::bind)
    private val viewModel by viewModels<MainMenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainMenuAdapter = MainMenuAdapter(viewModel::onVideoClicked)
        with(viewBinding.videosList) {
            adapter = mainMenuAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.mainMenuState.observe(viewLifecycleOwner) { state : MainMenuState ->
            when(state){
                is MainMenuState.Error -> {
                    viewBinding.videosErrorMessage.isVisible = true
                    viewBinding.videosLoadingBar.isVisible = false
                    viewBinding.videosList.isVisible = false
                }
                is MainMenuState.Loading -> {
                    viewBinding.videosErrorMessage.isVisible = false
                    viewBinding.videosLoadingBar.isVisible = true
                    viewBinding.videosList.isVisible = false
                }
                is MainMenuState.Success -> {
                    viewBinding.videosErrorMessage.isVisible = false
                    viewBinding.videosLoadingBar.isVisible = false
                    viewBinding.videosList.isVisible = true
                    mainMenuAdapter.submitList(state.videos.sortedByDescending { it.title })
                    viewBinding.videosList.post { viewBinding.videosList.scrollToPosition(0)}
                }
            }
        }


        viewBinding.itemAddProjectCreate.setOnClickListener {
            openInput()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onFragmentResume()
    }

    private fun openInput() {
        parentFragmentManager.navigate(InputFragment())
    }
}