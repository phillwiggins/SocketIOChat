package com.purewowstudio.socketiochat.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.purewowstudio.socketiochat.R
import java.net.URISyntaxException

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        private const val BASE_URL = "http://45.76.129.16:4678/"

        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var socket: Socket

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSocket()
    }

    private fun initSocket() {
        try {
            socket = IO.socket(BASE_URL);
        } catch (e: URISyntaxException) {
            Log.e("LOG:", "Shit went wrong")
        }

        socket.connect()
    }
}