package com.example.vkproducts.core

import android.util.Log
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.asFlow(): Flow<String> {
    return callbackFlow {
        val watcher = doAfterTextChanged { trySendBlocking(it.toString())
            Log.d("AsFlow", "Text changed: $it")
        }
        this@asFlow.addTextChangedListener(watcher)
        awaitClose { this@asFlow.removeTextChangedListener(watcher) }
    }
}