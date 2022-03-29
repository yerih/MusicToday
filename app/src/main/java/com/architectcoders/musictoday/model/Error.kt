package com.architectcoders.musictoday.model

import android.content.Context
import com.architectcoders.musictoday.R
import retrofit2.HttpException
import java.io.IOException


sealed interface Error {
    class Server(val code: Int) : Error
    object Connectivity : Error
    class Unknown(val message: String) : Error
}

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

inline fun <T> tryCall(action: () -> T): Error? = try {
    action()
    null
} catch (e: Exception) {
    e.toError()
}

fun Context.errorToString(error: Error): String = when(error){
    Error.Connectivity -> getString(R.string.connectivity_error)
    is Error.Server    -> getString(R.string.server_error)
    is Error.Unknown   -> getString(R.string.unknown_error)
}
fun Error.errorToString(context: Context): String = when(this){
    Error.Connectivity -> context.getString(R.string.connectivity_error)
    is Error.Server    -> context.getString(R.string.server_error)
    is Error.Unknown   -> context.getString(R.string.unknown_error)
}

