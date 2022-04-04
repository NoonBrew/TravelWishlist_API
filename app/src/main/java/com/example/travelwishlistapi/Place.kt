package com.example.travelwishlistapi

import java.text.SimpleDateFormat
import java.util.*
// set to a data class for a toString method
data class Place(val name: String, val reason: String? = null,
            var starred: Boolean = false, val id: Int? = null) {

}