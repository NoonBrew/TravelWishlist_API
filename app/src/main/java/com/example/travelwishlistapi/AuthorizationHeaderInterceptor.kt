package com.example.travelwishlistapi

import okhttp3.Interceptor
import okhttp3.Response

// Class intercepts HTTP requests and modifies them before they are sent to a server.
class AuthorizationHeaderInterceptor: Interceptor {
    // We add our header to the request.
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeaders = chain.request().newBuilder()
            .addHeader("Authorization", "Token ${BuildConfig.PLACES_TOKEN}").build()
        // Returns our request with the header.
        return chain.proceed(requestWithHeaders)
    }
}