package com.example.travelwishlistapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Interface allows for the function to be used in our PlaceRecyclerAdapter Class and overridden
// In our main activity.
interface OnListItemClickedListener {
    fun onMapRequestButtonClicked(place: Place)
    fun onStarredStatusChanged(place: Place, isStarred: Boolean)
}

class PlaceRecyclerAdapter(var places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener) :
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // ViewHolder Manages one view, one list item in this case. - sets the data in the view
        inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

            // Binds our data and buttons to our listViews.
            fun bind(place: Place) {
                // We hook up all the Widgets and textViews in the bind function since they will be
                // slightly different for each place in our list.
                val placeNameTextView: TextView = view.findViewById(R.id.place_name)

                val starCheck: CheckBox = view.findViewById(R.id.star_check_box)
                // Assigns data to our views from our list.
                placeNameTextView.text = place.name

                val reasonToVisit: TextView = view.findViewById(R.id.reason_why)
                reasonToVisit.text = place.reason

                val mapIcon: ImageView = view.findViewById(R.id.map_icon)
                // Set up a onclick listener and passes data to our interface function so that
                // We use MainActivity to override this function and send an implicit intent
                mapIcon.setOnClickListener {
                    onListItemClickedListener.onMapRequestButtonClicked(place)
                }
                // Initially set the listener to null to avoid a feedback loop.
                // Do we need this  if the logic is in order?
                starCheck.setOnClickListener(null) // remove listener
                starCheck.isChecked = place.starred // Update with value, default true.
                starCheck.setOnClickListener{ // replace listener - avoid endless loop.
                    onListItemClickedListener.onStarredStatusChanged(place, starCheck.isChecked)
                }


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