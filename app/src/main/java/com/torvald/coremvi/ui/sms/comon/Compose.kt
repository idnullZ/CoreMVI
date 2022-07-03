package com.torvald.coremvi.ui.sms.comon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.torvald.coremvi.ui.sms.SmsScreenState

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    header: String,
    textFieldValue: String,
    secureText: Boolean = false,
    enabled: Boolean = true,
    onTextFieldChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = header,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )

        DTextField(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            value = textFieldValue,
            enabled = enabled,
            placeholder = header,
            secureText = secureText,
            onValueChange = onTextFieldChange
        )
    }
}

@Composable
fun DTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    enabled: Boolean = true,
    secureText: Boolean = false,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        placeholder = {
            Text(
                modifier = Modifier.padding(start = 9.22.dp),
                text = placeholder,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 11.93.sp
                )
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Black,
            textColor = Color.Green,
            disabledIndicatorColor = Color.White,
            errorIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        visualTransformation = if (secureText) PasswordVisualTransformation() else
            VisualTransformation.None,
        onValueChange = onValueChange
    )
}

@Composable
fun DefaultAlertDialog(
    showingDialog: Boolean,
    message: String,
    title: String? = null,
    onDismissRequest: () -> Unit
) {

    if (showingDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            text = { Text(text = message) },
            title = { title?.let { Text(text = title) } },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "ок",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(onClick = { onDismissRequest() })
                    )
                }
            },
        )
    }
}


@Composable
fun ErrorDialog(error: Error?) {
    if (error == null) return
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = error) {
        showDialog = true
    }
    DefaultAlertDialog(
        showingDialog = showDialog,
        message = error.extractString()
    ) {
        showDialog = false
    }
}

@Composable
fun InfoDialog(message: String?) {
    if (message.isNullOrEmpty()) return
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = message) {
        showDialog = true
    }
    DefaultAlertDialog(
        showingDialog = showDialog,
        message = message
    ) {
        showDialog = false
    }
}

@Composable
fun HandleNetworkState(
    error: State<Error?>,
    state: SmsScreenState,
    isLoading: State<Boolean>
) {
    Box(modifier = Modifier.fillMaxHeight()) {
        ErrorDialog(error.value)
        InfoDialog(message = state.dialogMessage)
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center), color = Color.Green
            )
        }
    }
}

@Composable
fun ButtonNext(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .height(40.dp)
    ) { Text(text = "Продолжить") }
}







