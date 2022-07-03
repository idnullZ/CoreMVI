package com.torvald.coremvi.ui.sms

data class SmsScreenState(
    val fieldCode: String = "",
    val buttonNextEnable: Boolean = true,
    val dialogMessage: String = ""
)