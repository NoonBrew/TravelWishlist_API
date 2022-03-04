package com.example.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Interface allows for the function to be used in our PlaceRecyclerAdapter Class and overridden
// In our main activity.
interface OnListItemClickedListener {
    fun onListItemClicked(place: Place)
}

class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener) :
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // ViewHolder Manages one view, one list item in this case. - sets the data in the view
        inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

            // Binds our data and buttons to our listViews.
            fun bind(place: Place) {
                // We hook up all the Widgets and textViews in the bind function since they will be
                // slightly different for each place in our list.
                val placeNameTextView: TextView = view.findViewById(R.id.place_name)
                // Assigns data to our views from our list.
                placeNameTextView.text = place.name

                val mapIcon: ImageView = view.findViewById(R.id.map_icon)
                // Set up a onclick listener and passes data to our interface function so that
                // We use MainActivity to override this function and send an implicit intent
                mapIcon.setOnClickListener {
                    onListItemClickedListener.onListItemClicked(place)
                }

                // Sets up the last few views.
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
    // tells our RecyclerView how many items our in our list so it knows how many to display.
    override fun getItemCount(): Int {
        return places.size
    }


}