package com.example.travelwishlist

import java.text.SimpleDateFormat
import java.util.*
// Gave reason a default value so I would not need to change my example data.
class Place(val name: String, val reason: String = "Loves Travel", private val dateAdded: Date = Date()) {
    fun formattedDate(): String {
        return SimpleDateFormat("EEE, d, MMM yyyy", Locale.getDefault()).format(dateAdded)
    }
}