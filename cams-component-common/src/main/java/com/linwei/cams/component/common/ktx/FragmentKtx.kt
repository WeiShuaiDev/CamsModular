package com.linwei.cams.component.common.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * 增加Fragment到FrameLayout中
 */
fun FragmentManager.addFragment(
    fragment: Fragment,
    frameId: Int,
    tag: String = ""
) {
    beginTransaction().apply {
        tag.takeIf { it.isNotEmpty() }?.apply {
            add(frameId, fragment, tag)
        } ?: add(frameId, fragment)
    }.commit()
}

/**
 * 替换Fragment到FrameLayout中
 */
fun FragmentManager.replaceFragment(
    fragment: Fragment,
    frameId: Int
) {
    beginTransaction().apply {
        replace(frameId, fragment)
    }.commit()
}

/**
 * 删除Fragment
 */
fun FragmentManager.removeFragment(
    fragment: Fragment,
) {
    beginTransaction().apply {
        remove(fragment)
    }.commit()
}