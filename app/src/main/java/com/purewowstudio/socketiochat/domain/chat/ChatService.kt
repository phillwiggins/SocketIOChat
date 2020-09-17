package com.purewowstudio.socketiochat.domain.chat

import com.purewowstudio.socketiochat.data.Result
import kotlinx.coroutines.flow.Flow

interface ChatService {
    fun initSocket(baseURL: String = BASE_URL): Flow<Result<String>>
    fun disconnect()
}