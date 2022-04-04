package com.example.travelwishlistapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val TAG = "PlaceViewModel"

class PlacesViewModel: ViewModel() {

    // Sample data for app testing of a list of Lists
//    private val places = mutableListOf<Place>(Place("Eugene"),Place("Seattle"),Place("Tokyo"))

    private val placesRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>>(listOf())
    val userMessage = MutableLiveData<String>(null)
    init {
        getPlaces()
    }

    // function that returns our list of places using smart casting.
    fun getPlaces() {
        // Suspend functions run in the background so to view the data we can use
        // Coroutines to move them from suspend.
        viewModelScope.launch {
            val apiResult = placesRepository.getAllPlaces()
            if (apiResult.status == ApiStatus.SUCCESS) {
                allPlaces.postValue(apiResult.data)
            } else {
                userMessage.postValue(apiResult.message)
            }
        }
    }

    // Add a new place to our list, Position is not required but is a needed perimeter for when
    // restoring deleted places.
    fun addNewPlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placesRepository.addPlace(place)
            closureDialogRefresh(apiResult)
        }

    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placesRepository.updatePlace(place)
            closureDialogRefresh(apiResult)
        }
    }


    fun deletePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placesRepository.deletePlace(place)
            closureDialogRefresh(apiResult)
        }
    }
    // Handles setting the message value from the Repository and will Update the screen.
    private fun closureDialogRefresh (result: ApiResult<Any>){
        if (result.status == ApiStatus.SUCCESS) {
            getPlaces()
        }
        userMessage.postValue(result.message)
    }
}