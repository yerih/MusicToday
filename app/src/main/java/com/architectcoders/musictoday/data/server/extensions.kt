package com.architectcoders.musictoday.data.server

import android.content.Context
import arrow.core.left
import arrow.core.right
import com.architectcoders.musictoday.R
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import com.architectcoders.musictoday.domain.Error as ErrorDomain


fun Throwable.toError(): ErrorDomain = when (this) {
    is IOException -> ErrorDomain.Connectivity
    is HttpException -> ErrorDomain.Server(code())
    else -> ErrorDomain.Unknown(message ?: "")
}



fun ErrorDomain.errorToString(context: Context): String = when(this){
    ErrorDomain.Connectivity -> context.getString(R.string.connectivity_error)
    is ErrorDomain.Server -> context.getString(R.string.server_error)
    is ErrorDomain.Unknown -> context.getString(R.string.unknown_error)
}


suspend fun <T> tryCall(action: suspend () -> T): arrow.core.Either<ErrorDomain, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}



