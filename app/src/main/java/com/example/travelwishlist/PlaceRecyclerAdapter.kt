package com.example.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnListItemClickedListener {
    fun onListItemClicked(place: Place)
}

class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener) :
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // ViewHolder Manages one view, one list item in this case. - sets the data in the view
        inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

            fun bind(place: Place) {
                val placeNameTextView: TextView = view.findViewById(R.id.place_name)
                placeNameTextView.text = place.name

                val mapIcon: ImageView = view.findViewById(R.id.map_icon)
                mapIcon.setOnClickListener {
                    onListItemClickedListener.onListItemClicked(place)
                }

                val createdOnText = view.context.getString(R.string.created_on, place.formattedDate())
                val dateCreatedOnTextView: TextView = view.findViewById(R.id.date_place_added)
                dateCreatedOnTextView.text = createdOnText

                val reasonToVisit: TextView = view.findViewById(R.id.reason_why)
                reasonToVisit.text = place.reason
            }

        }

    // how many items in the list?

    // create a ViewHolder for a specific position? (combo view + data)

    // Bind the view holder with data for a specific position

    // call by recycler view to create as many view holders that are needed to display the list.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_list_item, parent, false)

        return ViewHolder(view)
    }

    // Called by the recycler view to set the data fro one list item, by position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return places.size
    }


}