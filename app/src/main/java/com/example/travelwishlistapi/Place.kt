package com.example.travelwishlistapi

import java.text.SimpleDateFormat
import java.util.*
// Gave reason a default value so I would not need to change my example data.
class Place(val name: String, val reason: String? = "Loves Travel",
            var starred: Boolean = false, val id: Int? = null) {

}