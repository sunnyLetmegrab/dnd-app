package com.example.dnd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.dnd.R
import com.example.dnd.component.ProfileItem
import com.example.dnd.helper.VolumeHelper
import com.example.dnd.helper.VolumeType
import com.example.dnd.helper.dbHelper.ProfileDatabase
import com.example.dnd.model.dbmodel.ProfileModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    var list: ArrayList<ProfileModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        var view = ComposeView(requireContext()).apply {
            setContent { HomeView(this@HomeFragment) }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfiles()
    }

     fun getProfiles() {
        GlobalScope.launch {
            var list = ProfileDatabase(requireContext()).ProfileDao().getAllProfiles()

            this@HomeFragment.list.clear()
            this@HomeFragment.list.addAll(list)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeView(fragment: HomeFragment) {


        var ringSlider = remember { SliderState() }
        var notiSlider = remember { SliderState() }
        var helper = VolumeHelper.getInstance(fragment.requireContext())
        var maxRing = helper.getMaxVolume(VolumeType.Ring)
        var maxNotification = helper.getMaxVolume(VolumeType.Notification)
        var currentRing = helper.getCurrentVolume(VolumeType.Ring)
        var currentNotification = helper.getCurrentVolume(VolumeType.Notification)

        ringSlider.value = ((currentRing * 100) / maxRing).toFloat() / 100
        notiSlider.value = ((currentNotification * 100) / maxNotification).toFloat() / 100



        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                fragment.requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, AddSoundProfileFragment()).addToBackStack("").commit()
            }) {
                Icon(
                    Icons.Rounded.Add, contentDescription = ""
                )
            }
        }, topBar = {
            TopAppBar(
                title = { Text("Welcome To App") },
            )
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {

                Card(
                    modifier = Modifier.padding(vertical = 15.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                        Text(
                            "Set Ring Volume", fontWeight = FontWeight.Bold, fontSize = 16.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        ) {
                            Slider(value = ringSlider.value, onValueChange = {
                                ringSlider.value = it
                                helper.setRingVolume(it, maxNotification)
                            }, modifier = Modifier.weight(1f))
                            Text(
                                String.format("%.0f", ringSlider.value * 100),
                            )
                        }
                        Divider(modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            "Set Notification Volume",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        ) {
                            Slider(value = notiSlider.value, onValueChange = {
                                notiSlider.value = it
                                helper.setNotificationVolume(it, maxNotification)
                            }, modifier = Modifier.weight(1f))
                            Text(String.format("%.0f", notiSlider.value * 100))
                        }
                    }
                }
                Divider(
                    color = Color.Gray.convert(colorSpace = ColorSpaces.CieXyz),
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Text(
                    text = "Created Profiles",
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline,
                )
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(fragment.list) {
                        ProfileItem(it = it, onDelete = {
                            GlobalScope.launch {
                                ProfileDatabase(requireContext()).ProfileDao().deleteProfile(it)
                                    .run {
                                        fragment.getProfiles()
                                    }
                            }
                        })
                    }
                }
            }

        }

    }
}
