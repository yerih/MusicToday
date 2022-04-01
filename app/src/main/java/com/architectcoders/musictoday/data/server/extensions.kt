package com.architectcoders.musictoday.data.server

import android.content.Context
import arrow.core.left
import arrow.core.right
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.domain.Error
import retrofit2.HttpException
import java.io.IOException


fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}



fun Error.errorToString(context: Context): String = when(this){
    Error.Connectivity -> context.getString(R.string.connectivity_error)
    is Error.Server -> context.getString(R.string.server_error)
    is Error.Unknown -> context.getString(R.string.unknown_error)
}


suspend fun <T> tryCall(action: suspend () -> T): arrow.core.Either<Error, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}

