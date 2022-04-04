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

    suspend fun getAllPlaces(): ApiResult<List<Place>> {
        try {
            val response = placeService.getAllPlaces()

            if (response.isSuccessful) { // Connected, got data back
                val places = response.body() ?: listOf()
                Log.d(TAG, "List of place $places")
                return ApiResult(ApiStatus.SUCCESS, response.body(), null)
            } else { // Connected but server sent an error.
                Log.e(TAG, "Error fetching place from API server ${response.errorBody()}")
                return ApiResult(ApiStatus.SERVER_ERROR, null, "Error fetching places")
            }

        } catch (ex: Exception) { // can't connect to the server - network error.
            Log.e(TAG, "Error connecting to API Server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
        }
    }
    // Use try and catch blocks for all the other functions.
    suspend fun addPlace(place: Place) : ApiResult<Place> {
        try {
            val response = placeService.addPlace(place) // Store the response
            if (response.isSuccessful) {
                Log.d(TAG, "Created new place for $place")
                Log.d(TAG, "Server created new place ${response.body()} ")
                return ApiResult(ApiStatus.SUCCESS, null, "Place added!" )
            } else {
                Log.e(TAG, "Error creating new place ${response.errorBody()}")
                return ApiResult(ApiStatus.SERVER_ERROR, null, "Error adding place - is name unique")
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error connecting to API Server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
        }
    }
    // All these functions check the status of the result and pass back different messages
    // to provide the user with some Dialog of action.
    suspend fun updatePlace(place: Place): ApiResult<Place> {
        if (place.id == null) {
            Log.e(TAG, "Error - trying to update place with no ID")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error - updating place with no ID?")
        }else {
            try {
               val response = placeService.updatePlace(place, place.id)
                if (response.isSuccessful){
                    return ApiResult(ApiStatus.SUCCESS, null, "Place Updated")
                } else {
                    return ApiResult(ApiStatus.SERVER_ERROR, null, "Error updating place.")
                }
            } catch (ex: Exception){
                return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
            }
        }
    }

    suspend fun deletePlace(place: Place): ApiResult<Nothing>{
        if (place.id == null) {
            Log.e(TAG, "Error - trying to delete place with no ID")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error - deleting place with no ID?")
        }else {
            try {
                val response = placeService.deletePlace(place.id)
                if (response.isSuccessful){
                    return ApiResult(ApiStatus.SUCCESS, null, "Place Deleted")
                } else {
                    return ApiResult(ApiStatus.SERVER_ERROR, null, "Error Deleting place.")
                }
            } catch (ex: Exception){
                return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
            }

        }
    }
}