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

    init {
        getPlaces()
    }

    // function that returns our list of places using smart casting.
    fun getPlaces() {
        // Suspend functions run in the background so to view the data we can use
        // Coroutines to move them from suspend.
        viewModelScope.launch {
            val places = placesRepository.getAllPlaces()
            allPlaces.postValue(places)
        }
    }

    // Add a new place to our list, Position is not required but is a needed perimeter for when
    // restoring deleted places.
    fun addNewPlace(place: Place) {

        // TODO

    }


    // Called when a view is deleted from our recycleListView. Deletes it from our mutable list as well.
    fun deletePlace(place: Place) {
        // TODO

    }

    fun updatePlace(place: Place) {
        // todo
    }
}