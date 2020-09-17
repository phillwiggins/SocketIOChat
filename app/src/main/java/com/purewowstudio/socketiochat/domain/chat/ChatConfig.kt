package com.purewowstudio.socketiochat.domain.chat

// Our server address
const val BASE_URL = "http://45.76.129.16:3000"

// Incoming
const val EVENT_OUT_CONNECTION = "connection"
const val EVENT_OUT_SUBSCRIBE = "subscribe"
const val EVENT_OUT_UNSUBSCRIBE = "unsubscribe"
const val EVENT_OUT_NEW_MESSAGE = "newMessage"
const val EVENT_OUT_DISCONNECT = "disconnect"

// Outgoing
const val EVENT_ON_NEW_USER = "newUserToChatRoom"
const val EVENT_ON_UPDATE_CHAT = "updateChat"
const val EVENT_ON_USER_LEFT = "userLeftChatRoom"
