package com.example.travelwishlist

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PlaceViewModel"
class PlacesViewModel: ViewModel() {

    private val places = mutableListOf<Place>(Place("Eugene"),Place("Seattle"),Place("Tokyo"))

    fun getPlaces(): List<Place> {
        return places // smart casting
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {

//        for(placeName in placeNames) {
//            if (placeName.uppercase() == place.uppercase()) {
//                return -1 // -1 with lists tells us that it is invalid list entry
//            }
//        }
        // all function returns true if all of the things in a list meet a condition.
        // any function returns true if any (at least one) meets the condition.
        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() } ) {
            return -1
        }

        return if (position == null) {
            places.add(place)
            return places.lastIndex
        } else {
            places.add(position, place)
            position
        }
    }


    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        Log.d(TAG, places.toString())
    }

    fun deletePlace(position: Int): Place {
        return places.removeAt(position)

    }
}