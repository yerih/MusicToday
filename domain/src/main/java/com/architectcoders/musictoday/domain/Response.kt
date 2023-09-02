package com.architectcoders.musictoday.domain

import com.architectcoders.musictoday.domain.Error as ErrorDomain


data class Response<T>(
    val value: T?,
    val error: Error? = null,
){
    fun isValid(): Boolean = (value != null)
    fun isError(): Boolean = (error != null)
}