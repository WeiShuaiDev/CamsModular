package com.linwei.cams.framework.mvi.mvi.model

import com.linwei.cams.framework.mvi.mvi.intent.StatusCode

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构  `Model` 模块
 *-----------------------------------------------------------------------
 */
data class MviViewEvent(@StatusCode val code: Int, val content: Any)