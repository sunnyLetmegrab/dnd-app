package com.example.dnd.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.LocalDateTime

class Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TimePickerDialog(
        showDialog: Boolean,
        initialHour: Int = LocalDateTime.now().hour,
        initialMinute: Int = LocalDateTime.now().minute,
        onDismiss: () -> Unit,
        onTimePick: (time: Pair<Int, Int>) -> Unit,

        ) {
        if (showDialog) {
            val timePickerState = rememberTimePickerState(initialHour, initialMinute)
            Dialog(
                onDismissRequest = onDismiss,
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(8.dp)
                ) {

                    Column {
                        TimePicker(
                            state = timePickerState,
                        )
                        Row {
                            ElevatedButton(
                                onClick = {
                                    onTimePick(Pair(timePickerState.hour, timePickerState.minute))
                                },
                            ) {
                                Text("Save")
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            OutlinedButton(onClick = onDismiss) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }
            }
        }

    }
}