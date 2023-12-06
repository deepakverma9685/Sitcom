package com.deepak.sitcom.data.remote

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}