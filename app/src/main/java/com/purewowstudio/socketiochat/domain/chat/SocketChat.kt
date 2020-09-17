package com.purewowstudio.socketiochat.domain.chat

import android.util.Log
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.purewowstudio.socketiochat.data.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONException
import java.net.URISyntaxException

@ExperimentalCoroutinesApi
object SocketChat : ChatService {

    private lateinit var socket: Socket
    private lateinit var flow: MutableStateFlow<Result<String>>

    override fun initSocket(baseURL: String): Flow<Result<String>> {
        try {
            socket = IO.socket(BASE_URL)
            socket.connect()
            Log.v("SocketChat", socket.id())
        } catch (e: URISyntaxException) {
            Log.e("SocketChat", "Incorrect URL")
        } catch (e: Exception) {
            Log.e("SocketChat", "Failed Connection (Possibly no ID)")
        }

        socket.on(EVENT_OUT_CONNECTION, onNewMessage)
        socket.on(EVENT_OUT_SUBSCRIBE, onNewMessage)
        socket.on(EVENT_OUT_UNSUBSCRIBE, onNewMessage)
        socket.on(EVENT_OUT_NEW_MESSAGE, onNewMessage)
        socket.on(EVENT_OUT_DISCONNECT, onNewMessage)

        flow = MutableStateFlow(Result.Loading)
        return flow
    }

    override fun disconnect() {
        socket.disconnect()
        socket.off(EVENT_OUT_CONNECTION, onNewMessage)
        socket.off(EVENT_OUT_SUBSCRIBE, onNewMessage)
        socket.off(EVENT_OUT_UNSUBSCRIBE, onNewMessage)
        socket.off(EVENT_OUT_NEW_MESSAGE, onNewMessage)
        socket.off(EVENT_OUT_DISCONNECT, onNewMessage)
    }

    private val onNewMessage = Emitter.Listener {
        val data = it[0]
        try {
            flow.value = Result.Success(data.toString())
            Log.v("SocketChat", data.toString())
        } catch (e: JSONException) {
            flow.value = Result.Error("SocketChatError ${e.localizedMessage ?: "Unknown Error"}")
        }
    }
}
