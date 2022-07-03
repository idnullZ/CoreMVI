package com.torvald.coremvi.ui.sms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.torvald.coremvi.ui.sms.comon.ButtonNext
import com.torvald.coremvi.ui.sms.comon.HandleNetworkState
import com.torvald.coremvi.ui.sms.comon.TextInput

@Composable
fun SmsScreen(viewModel: SmsScreenViewModel = hiltViewModel()) {

    val isLoading = viewModel.isLoading.collectAsState(false)
    val error = viewModel.error.collectAsState(null)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(Color.LightGray)
    ) {
        TextInput(
            modifier = Modifier.padding(50.dp),
            header = "Введите код",
            textFieldValue = viewModel.state.fieldCode,
            onTextFieldChange = {
                viewModel.obtainEvent(SmsScreenEvent.CodeFieldChange(it))
            }
        )
        ButtonNext { viewModel.obtainEvent(SmsScreenEvent.ContinueClick) }
    }
    HandleNetworkState(error, viewModel.state, isLoading)
}