package com.purewowstudio.socketiochat.data

import android.util.Log
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

private const val BASE_URL = "http://45.76.129.16:4678"
private const val MESSAGE_USERNAME = "username"
private const val MESSAGE_IS_ONLINE = "is_online"
private const val MESSAGE_DISCONNECT = "disconnect"
private const val MESSAGE_CHAT_MESSAGE = "chat_message"

interface ChatService {
    fun initSocket(baseURL: String = BASE_URL): Flow<Resource<String>>
    fun disconnect()
}

@ExperimentalCoroutinesApi
object SocketChat : ChatService {

    private lateinit var socket: Socket
    private lateinit var flow: MutableStateFlow<Resource<String>>

    override fun initSocket(baseURL: String): Flow<Resource<String>> {
        try {
            socket = IO.socket(BASE_URL)
            Log.v("SocketChat", socket.id())
        } catch (e: URISyntaxException) {
            Log.e("SocketChat", "Shit went wrong")
        } catch (e: Exception) {
            Log.e("SocketChat", "No ID")
        }

        socket.on(MESSAGE_USERNAME, onNewMessage)
        socket.on(MESSAGE_IS_ONLINE, onNewMessage)
        socket.on(MESSAGE_DISCONNECT, onNewMessage)
        socket.on(MESSAGE_CHAT_MESSAGE, onNewMessage)

        socket.connect()

        flow = MutableStateFlow(Resource.loading())
        return flow
    }

    override fun disconnect() {
        socket.disconnect()
        socket.off(MESSAGE_USERNAME, onNewMessage)
        socket.off(MESSAGE_IS_ONLINE, onNewMessage)
        socket.off(MESSAGE_DISCONNECT, onNewMessage)
        socket.off(MESSAGE_CHAT_MESSAGE, onNewMessage)
    }

    private val onNewMessage = Emitter.Listener {
        val data = it[0]
        try {
            flow.value = Resource.success(data.toString())
            Log.v("SocketChat", data.toString())
        } catch (e: JSONException) {
            flow.value = Resource.error("SocketChatError", e.localizedMessage ?: "Unknown Error")
        }
    }
}
