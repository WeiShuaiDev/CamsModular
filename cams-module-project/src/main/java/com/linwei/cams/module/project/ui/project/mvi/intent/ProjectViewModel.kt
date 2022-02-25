package com.linwei.cams.module.project.ui.project.mvi.intent

import androidx.lifecycle.viewModelScope
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.framework.mvi.ext.FetchStatus
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import kotlinx.coroutines.launch

class ProjectViewModel: MviViewModel() {

//    fun fetchProjectTreeData(){
//        _viewStates.setState {
//            copy(fetchStatus = )
//        }
//        viewModelScope.launch {
//            when (val result = repository.getMockApiResponse()) {
//                is PageState.Error -> {
//                    _viewStates.setState {
//                        copy(fetchStatus = FetchStatus.Fetched)
//                    }
//                    _viewEvents.setEvent(MainViewEvent.ShowToast(message = result.message))
//                }
//                is PageState.Success -> {
//                    _viewStates.setState {
//                        copy(fetchStatus = FetchStatus.Fetched, newsList = result.data)
//                    }
//                }
//            }
//        }
//
//    }
}