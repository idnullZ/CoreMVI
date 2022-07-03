package com.torvald.coremvi.ui.sms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.torvald.coremvi.ui.sms.comon.Result
import com.torvald.coremvi.ui.sms.comon.StateFullViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmsScreenViewModel @Inject constructor(private val sendSms: SendSmsUseCase) :
    StateFullViewModel<SmsScreenState, SmsScreenEvent>() {

    override var state by mutableStateOf(SmsScreenState())
        private set

    override fun obtainEvent(event: SmsScreenEvent) {
        when (event) {
            is SmsScreenEvent.ContinueClick -> checkCode(state.fieldCode)
            is SmsScreenEvent.CodeFieldChange -> changState(SmsScreenState(fieldCode = event.value))
        }
    }

    private fun checkCode(code: String) {
        networkCall {
            when (val result = sendSms(code)) {
                is Result.Success -> showInfoDialog("Success")
                is Result.Failure -> emitError(result)
            }
        }
    }

    private fun changState(value: SmsScreenState) {
        state = state.copy(
            fieldCode = value.fieldCode,
            buttonNextEnable = value.buttonNextEnable,
            dialogMessage = value.dialogMessage,
        )
    }

    private fun showInfoDialog(message: String) {
        changState(SmsScreenState(dialogMessage = message))
    }
}