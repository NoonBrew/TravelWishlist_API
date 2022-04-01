package com.example.travelwishlistapi

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class PlaceRepository {

    private val TAG = "PLACE_REPO"

    // Base URL of the API server.
    private val baseURL = "https://place-wish-list.herokuapp.com/api/"
    // Does the work of making the requests to the API server
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor()) // AuthorizationHeader will add the header.
        .build()

    // Retrofit will handle the converting to HTTP
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // References PlaceService (Kind of like an API access object) By making a PlaceService Object.
    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun getAllPlaces(): List<Place> {
        try {
            val response = placeService.getAllPlaces()

            if (response.isSuccessful) { // Connected, got data back
                val places = response.body() ?: listOf()
                // Because we might not get a successful return from the API server we need to handle
                // Null data so we use an Elvis Operator to make an empty list so the app does not crash.
                // TODO add closure dialog.
                return places
            } else { // Connected but server sent an error.
                Log.e(TAG, "Error connecting to API Server ${response.errorBody()}")
                return listOf()
            }

        } catch (ex: Exception) { // can't connect to the server - network error.
            Log.e(TAG, "NET Error connecting to API Server", ex)
            return listOf()
        }
    }
}