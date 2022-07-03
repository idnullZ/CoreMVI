package com.torvald.coremvi.ui.sms.comon

import android.content.Context


sealed class Result<out T> {

    class Success<T>(val data: T) : Result<T>()

    class Failure(val error: Error) : Result<Nothing>()
}


open class BaseResponse {
    val success: Boolean? = null
    val reason: String? = null
}

sealed class Error {
    class Unknown(val text: String) : Error()
    sealed class Predefined : Error() {
        object Timeout : Predefined()
        object NoNetwork : Predefined()
        object Server : Predefined()
        object Illegal : Predefined()
    }
}

fun Error.extractString(): String {
    return when (this) {
        is Error.Unknown -> text
        is Error.Predefined.Timeout -> "Timeout"
        is Error.Predefined.NoNetwork -> "NoNetwork"
        is Error.Predefined.Server -> "Server"
        is Error.Predefined.Illegal -> "Illegal"
    }
}