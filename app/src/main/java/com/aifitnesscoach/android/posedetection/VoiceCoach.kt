package com.aifitnesscoach.android.posedetection

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

object VoiceCoach {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    @JvmStatic
    fun init(context: Context) {
        if (isInitialized) return

        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                isInitialized = true
            }
        }
    }

    @JvmStatic
    fun speak(message: String) {
        if (!isInitialized) return
        tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, message)
    }

    @JvmStatic
    fun release() {
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
