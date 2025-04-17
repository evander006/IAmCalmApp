package com.example.iamcalmapp.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.iamcalmapp.R
import com.example.iamcalmapp.databinding.FragmentTimerBinding
import com.example.iamcalmapp.service.ForegroundService
import kotlinx.coroutines.launch


class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding
    private lateinit var serviceIntent: Intent
    private var isBound = false
    private lateinit var musicService: ForegroundService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val musicBinder = binder as ForegroundService.MusicBinder
            musicService = musicBinder.getService()
            isBound = true

            lifecycleScope.launch {
                musicService.isPlayingState.collect { playing ->
                    updatePlayPauseButton(playing)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceIntent = Intent(requireContext(), ForegroundService::class.java)
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        // Start the service before binding
        ContextCompat.startForegroundService(requireContext(), serviceIntent)
        requireContext().bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            requireContext().unbindService(connection)
            isBound = false
        }
    }

    private fun setupListeners() {
        binding.playPause.setOnClickListener {
            if (isBound) {
                musicService.togglePlayPause()
            } else {
                Toast.makeText(requireContext(), "Service not ready", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        val icon = if (isPlaying) R.drawable.playing else R.drawable.paused
        binding.playPause.setImageResource(icon)
    }
}