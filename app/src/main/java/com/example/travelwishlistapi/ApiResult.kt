package com.example.travelwishlistapi


// status of request - success, server error, network error
// data, if any
// closure dialog for user.

enum class ApiStatus { // enum class is a collection of constants.
    SUCCESS,
    SERVER_ERROR,
    NETWORK_ERROR
}

// T is a placeholder for a Kotlin type.
data class ApiResult<out T>(val status: ApiStatus, val data: T?, val message: String?)