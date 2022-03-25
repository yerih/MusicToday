package com.architectcoders.musictoday.model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.architectcoders.musictoday.App
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun Context.log(label: String = "TGB", value: String = "value?"){
    Log.i(label, value)
}

fun Context.log(value: String = "") {
    this.log("TGB", value)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?){
    url?.let { Glide.with(context).load(url).into(this) }
}


fun CoroutineScope.log(label: String, value: String){
    Log.i(label, value)
}

fun CoroutineScope.log(value: String){
    log("TGB", value)
}

fun ViewModel.log(value: String){
    Log.i("TGB", value)
}

fun Fragment.log(value: String){
    context?.log(value)
}

inline fun <T> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

var View.visible: Boolean get() { return visibility == View.VISIBLE }
    set(value) { visibility = if(value) View.VISIBLE else View.GONE}


fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
){
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state){
            flow.collect(body)
        }
    }
}

val Context.app: App get() = applicationContext as App




