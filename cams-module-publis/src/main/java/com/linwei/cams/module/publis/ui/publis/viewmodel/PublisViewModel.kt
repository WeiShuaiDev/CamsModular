package com.linwei.cams.module.publis.ui.publis.viewmodel

import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.publis.provider.PublisProviderImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PublisViewModel @Inject constructor(private val projectProvider:PublisProviderImpl): MvvmViewModel() {
}