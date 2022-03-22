package com.architectcoders.musictoday.ui.common

import android.app.Application
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionRequester(fragment: Fragment, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onRequest(isGranted)
        }

    suspend fun request(): Boolean = suspendCancellableCoroutine { continuation ->
        onRequest = { continuation.resume(it) }
        launcher.launch(permission)
    }
}


class PermissionChecker(private val application: Application, private val permission: String) {

    fun check(): Boolean = ContextCompat.checkSelfPermission(
        application,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}


