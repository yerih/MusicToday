package com.architectcoders.musictoday.model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide

fun Context.log(label: String = "TGB", value: String = "value?"){
    Log.i(label, value)
}

fun Context.log(value: String = "") {
    this.log("TGB", value)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String){
    Glide.with(context).load(url).into(this)
}



