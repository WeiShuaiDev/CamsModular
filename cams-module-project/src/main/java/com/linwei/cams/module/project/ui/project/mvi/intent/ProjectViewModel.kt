package com.linwei.cams.module.project.ui.project.mvi.intent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.component.network.ktx.commonCatch
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.ktx.setState
import com.linwei.cams.framework.mvi.mvi.ViewModelFactory
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.module.project.provider.ProjectProviderImpl
import com.linwei.cams.module.project.ui.project.mvi.model.MviViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val projectProvider:ProjectProviderImpl) :
    MviViewModel() {

    private val _viewStates: MutableLiveData<MviViewState> = MutableLiveData(MviViewState())
    val viewState = _viewStates.asLiveData()

    /**
     * 获取项目树数据
     */
    fun fetchProjectTreeData() {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }

        viewModelScope.launch {
            when (val result = projectProvider.fetchProjectTreeData()) {
                is PageState.Error -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.NotFetched)
                    }
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, result.message))
                }
                is PageState.Success -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched, projectTreeList = result.data)
                    }
                }
            }
        }
    }

    /**
     * 获取项目树详情数据
     */
    fun fetchProjectTreeDetailsData() {
        viewModelScope.launch {
            flow {
                emit(projectProvider.fetchProjectTreeDetailsData())
            }.onStart {
                _viewStates.setState { copy(fetchStatus = FetchStatus.Fetching) }
            }.onEach {
                _viewStates.setState {
                    copy(
                        projectTreeDetailsList = listOf(it),
                        fetchStatus = FetchStatus.Fetched
                    )
                }
            }.commonCatch {
                _viewStates.setState { copy(fetchStatus = FetchStatus.NotFetched) }
            }.collect()
        }
    }
}