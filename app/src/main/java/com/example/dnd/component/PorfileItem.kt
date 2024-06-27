package com.example.dnd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnd.model.dbmodel.ProfileModel
import kotlinx.coroutines.Job


@Composable
fun ProfileItem(it: ProfileModel, onDelete: (() -> Job)? = null) {

    var showConfermation by remember { mutableStateOf(false) }
    if (showConfermation) {
        AlertDialog(
            onDismissRequest = { showConfermation = false },
            text = { Text(text = "Are you sure you want to delete this profile?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp), Arrangement.End
                ) {
                    ElevatedButton(onClick = {
                        showConfermation = false
                        onDelete?.let { it1 -> it1() }
                    }) {
                        Text(text = "Yes")
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    OutlinedButton(onClick = {
                        showConfermation = false
                    }) {
                        Text(text = "No")
                    }
                }
            },
        )
    }


    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            disabledElevation = 3.dp,
            draggedElevation = 3.dp,
            focusedElevation = 3.dp,
            hoveredElevation = 3.dp,
            pressedElevation = 3.dp
        ),
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = it.profileName, style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                    ), modifier = Modifier.padding(
                        horizontal = 16.dp, vertical = 5.dp
                    )
                )
                Text(
                    text = "Starts At ${it.startTime} & Ends at ${it.endTime}", style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp,
                    ), modifier = Modifier.padding(
                        horizontal = 16.dp, vertical = 5.dp
                    )
                )

            }
            IconButton(onClick = {
                showConfermation = true
            }) {
                Icon(Icons.Rounded.Delete, "")
            }
        }
    }
}