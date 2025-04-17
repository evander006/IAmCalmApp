package com.example.iamcalmapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.iamcalmapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ForegroundService : Service() {
    companion object {
        const val ACTION_PLAY_PAUSE = "play_pause"
        const val ACTION_STOP = "stop"
        const val NOTIFICATION_CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 101
    }

    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlayingState: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): ForegroundService = this@ForegroundService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initializeMediaPlayer()
        startForegroundService()
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.songmeditate).apply {
            setOnCompletionListener {
                _isPlaying.value = false
                updateNotification()
            }
            setOnErrorListener { _, what, extra ->
                Log.e("ForegroundService", "MediaPlayer error: $what, $extra")
                false
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> togglePlayPause()
            ACTION_STOP -> stopSelf()
            else -> startForegroundService()
        }
        return START_STICKY
    }

    fun togglePlayPause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _isPlaying.value = false
            } else {
                player.start()
                _isPlaying.value = true
            }
            updateNotification()
        } ?: run {
            initializeMediaPlayer()
            togglePlayPause()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Meditation Music",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background music playback controls"
                setSound(null, null) // Disable notification sound
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun startForegroundService() {
        updateNotification()
    }

    private fun updateNotification() {
        val playPauseAction = NotificationCompat.Action(
            if (_isPlaying.value) R.drawable.paused else R.drawable.playing,
            if (_isPlaying.value) "Pause" else "Play",
            getPendingIntent(ACTION_PLAY_PAUSE)
        )

        val stopAction = NotificationCompat.Action(
            R.drawable.paused,
            "Stop",
            getPendingIntent(ACTION_STOP)
        )

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Meditation Music")
            .setContentText(if (_isPlaying.value) "Playing" else "Paused")
            .setSmallIcon(R.drawable.musicicon)
            .addAction(playPauseAction)
            .addAction(stopAction)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setSilent(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun getPendingIntent(action: String): PendingIntent {
        return PendingIntent.getService(
            this,
            0,
            Intent(this, ForegroundService::class.java).apply {
                this.action = action
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        stopForeground(true)
        super.onDestroy()
    }
}