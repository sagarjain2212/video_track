package com.youtubetrack.state

sealed class ValidationEvent {
    data object Success : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}