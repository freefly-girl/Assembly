package com.educational.assemblyapp.presentation

import android.os.Bundle
import com.educational.assemblyapp.presentation.common.BaseActivity
import com.educational.assemblyapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}