package com.reactnativenavigation.views.element.finder

import android.view.View
import com.facebook.react.uimanager.util.ReactFindViewUtil
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ExistingViewFinder : ViewFinder {
    override suspend fun find(root: ViewController<*>, nativeId: String): View? {
        return ReactFindViewUtil.findView(root.view, nativeId)
    }
}