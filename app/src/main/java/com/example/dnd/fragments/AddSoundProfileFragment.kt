package com.example.dnd.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.dnd.component.AppEditText
import com.example.dnd.helper.dbHelper.ProfileDatabase
import com.example.dnd.helper.startReminder
import com.example.dnd.model.dbmodel.ProfileModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AddSoundProfileFragment : Fragment() {
    var profileName = mutableStateOf(TextFieldValue(""))
    var startTime = mutableStateOf(TextFieldValue(""))
    var endTime = mutableStateOf(TextFieldValue(""))
    var showPicker = mutableStateOf(0);

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = ComposeView(requireContext()).apply {
            setContent {
                AddSoundProfile(this@AddSoundProfileFragment)
            }
        }
        return view;
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSoundProfile(ff: AddSoundProfileFragment) {
    val state = rememberScrollState()
    var validate by remember { mutableStateOf(false) }

    if (ff.showPicker.value > 0) {
        Utils().TimePickerDialog(showDialog = ff.showPicker.value > 0,
            onDismiss = { ff.showPicker.value = 0 }) {
            if (ff.showPicker.value == 1) {

                ff.startTime.value = ff.startTime.value.copy(
                    text = "${it.first}:${it.second}"
                )
            }
            if (ff.showPicker.value == 2) {
                ff.endTime.value = ff.endTime.value.copy(
                    text = "${it.first}:${it.second}"
                )
            }
            ff.showPicker.value = 0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Add profile")
            }, navigationIcon = {
                IconButton(onClick = {
                    ff.requireActivity().supportFragmentManager.popBackStack()
                }) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = "")
                }

            })
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                item {
                    Column {
                        Spacer(modifier = Modifier.padding(top = 24.dp))
                        AppEditText(
                            value = ff.profileName.value,
                            onValueChange = {
                                ff.profileName.value = it
                            },
                            validate = validate,
                            errorMessage = "Please enter profile name",
                            placeHolder = { Text("Profile name") },
//                            isError = ff.profileName.value.text.isEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                        )


                        Surface(onClick = {
                            ff.showPicker.value = 1;
                        }, modifier = Modifier.padding(0.dp)) {
                            AppEditText(
                                enabled = false,
                                value = ff.startTime.value,
                                onValueChange = {
                                    ff.startTime.value = it
                                },
                                validate = validate,
                                errorMessage = "Please enter Start time",
                                placeHolder = { Text("Start time") },
//                            isError = ff.profileName.value.text.isEmpty(),
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                        Surface(onClick = {
                            ff.showPicker.value = 2;
                        }) {
                            AppEditText(
                                value = ff.endTime.value,
                                onValueChange = {
                                    ff.endTime.value = it
                                },
                                enabled = false,
                                validate = validate,
                                errorMessage = "Please enter end time",
                                placeHolder = { Text("End time") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding()
                    .padding(bottom = 10.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        if (ff.profileName.value.text.isEmpty() || ff.startTime.value.text.isEmpty() || ff.endTime.value.text.isEmpty()) {
                            validate = true;
                            return@ElevatedButton
                        }
                        validate = false;
                        GlobalScope.launch {
                            ff.context?.let {
                                val profile =
                                    ProfileModel(
                                        profileName = ff.profileName.value.text,
                                        startTime = ff.startTime.value.text,
                                        endTime = ff.endTime.value.text,

                                        )
                                ProfileDatabase(ff.requireContext()).ProfileDao()
                                    .insertProfile(profile);
                            }
                        }

                    }, modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Save profile")
                }
            }
        }
    }
}

