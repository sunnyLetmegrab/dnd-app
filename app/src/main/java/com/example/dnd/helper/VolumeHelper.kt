package com.example.dnd.helper

import android.content.Context
import android.media.AudioManager
import android.util.Log
import kotlin.math.log

enum class VolumeType {
    Ring, Notification
}

class VolumeHelper() {

    fun getMaxVolume(volumeType: VolumeType): Int {
        return when (volumeType) {
            VolumeType.Ring -> audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            VolumeType.Notification -> audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        }
    }

    fun getCurrentVolume(volumeType: VolumeType): Int {
        Log.e("TAG", "getCurrentVolume: " + audioManager.getStreamVolume(AudioManager.STREAM_RING))
        return when (volumeType) {
            VolumeType.Ring -> audioManager.getStreamVolume(AudioManager.STREAM_RING);
            VolumeType.Notification -> audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        }
    }

    fun setNotificationVolume(currentValue: Float=2f, maxValue: Int) {
        var setVolume = currentValue * maxValue;
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, setVolume.toInt(), 0)

    }

    fun setRingVolume(currentValue: Float, maxValue: Int) {
        var setVolume = currentValue * maxValue;
        audioManager.setStreamVolume(AudioManager.STREAM_RING, setVolume.toInt(), 0)

    }

    companion object {
        private var instance: VolumeHelper? = null
        lateinit var context: Context
        lateinit var audioManager: AudioManager


        fun getInstance(context: Context): VolumeHelper {
            if (instance == null)  // NOT thread safe!
                instance = VolumeHelper();
            this.context = context;
            audioManager =
                Companion.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager;
            return instance!!
        }
    }
}