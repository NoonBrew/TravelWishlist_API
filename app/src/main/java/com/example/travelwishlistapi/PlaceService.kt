package com.example.travelwishlistapi

import retrofit2.Response
import retrofit2.http.GET

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // todo POST create place

    // todo update place

    // todo delete place
}