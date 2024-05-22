package com.example.dnd.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppEditText(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    borderRadius: Dp = 10.dp,
    disableindicatorColor: Color = Color.White,
    focusIndicatorColor: Color = Color.White,
    errorMessage: String? = null,
    validate: Boolean = false,
    unFocusIndicatorColor: Color = Color.White,
    placeHolder: @Composable() () -> Unit = { Text(text = "") }
) {
    Column {
        TextField(
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            placeholder = placeHolder,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            singleLine = singleLine,
            shape = RoundedCornerShape(borderRadius),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = disableindicatorColor,
                focusedIndicatorColor = focusIndicatorColor,
                unfocusedIndicatorColor = unFocusIndicatorColor,
            ),
        )
        if (validate && value.text.isEmpty()) Text(
            text = "$errorMessage",
            color = Color.Red,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}