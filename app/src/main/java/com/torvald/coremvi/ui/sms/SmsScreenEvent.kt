package com.torvald.coremvi.ui.sms

sealed class SmsScreenEvent {
    object ContinueClick : SmsScreenEvent()
    data class CodeFieldChange(val value: String):SmsScreenEvent()
}
