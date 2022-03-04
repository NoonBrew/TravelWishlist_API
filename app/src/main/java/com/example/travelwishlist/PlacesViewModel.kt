package com.example.travelwishlist

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PlaceViewModel"
class PlacesViewModel: ViewModel() {

    // Sample data for app testing of a list of Lists
    private val places = mutableListOf<Place>(Place("Eugene"),Place("Seattle"),Place("Tokyo"))

    // function that returns our list of places using smart casting.
    fun getPlaces(): List<Place> {
        return places // smart casting
    }

    // Add a new place to our list, Position is not required but is a needed perimeter for when
    // restoring deleted places.
    fun addNewPlace(place: Place, position: Int? = null): Int {

//        for(placeName in placeNames) {
//            if (placeName.uppercase() == place.uppercase()) {
//                return -1 // -1 with lists tells us that it is invalid list entry
//            }
//        }
        // all function returns true if all of the things in a list meet a condition.
        // any function returns true if any (at least one) meets the condition.

        // This condition checks to see if the place object being passed shares a name with an object
        // already in our list of places.
        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() } ) {
            return -1
        }
        // The addNewPlace is passed a null argument if it is a new entry into the list and
        // adds it to the end of the list. Returns the position.
        return if (position == null) {
            places.add(place)
            return places.lastIndex
        } else { // Otherwise adds it at the designated position.
            places.add(position, place)
            position
        }
    }

    // Function is called if an contained in our recycleListView is reordered.
    // removes the place object from the received location and adds it to the new location.
    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        Log.d(TAG, places.toString())
    }
    // Called when a view is deleted from our recycleListView. Deletes it from our mutable list as well.
    fun deletePlace(position: Int): Place {
        return places.removeAt(position)

    }
}