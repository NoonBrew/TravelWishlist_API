package com.example.travelwishlistapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var newPlaceEditText: EditText
    private lateinit var reasonToTravel: EditText
    private lateinit var addNewPlaceButton: Button
    private lateinit var placeListRecyclerView: RecyclerView

    private lateinit var placeRecyclerAdapter: PlaceRecyclerAdapter

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this)[PlacesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        newPlaceEditText = findViewById(R.id.new_place_name)
        reasonToTravel = findViewById(R.id.reason_to_travel)

//        val places = placesViewModel.getPlaces() // list of place objects

        // Sets up our adapter and RecyclerView to be displayed in mainActivity.
        placeRecyclerAdapter = PlaceRecyclerAdapter(listOf(), this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placeRecyclerAdapter

//        ItemTouchHelper(OnListItemSwipeListener(this)).attachToRecyclerView(placeListRecyclerView)

        // This initiates our itemSwipeListener on the mainActivity and attaches it to the RecyclerView
        // telling our activity what views we want to be swipeable.
        val itemSwipeListener = OnListItemSwipeListener(this)
        ItemTouchHelper(itemSwipeListener).attachToRecyclerView(placeListRecyclerView)
        // calls our addNewPlace function when the add button is clicked.
        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }

        placesViewModel.allPlaces.observe(this) { places ->
            placeRecyclerAdapter.places = places
            placeRecyclerAdapter.notifyDataSetChanged()
        }
    }

    private fun addNewPlace() {
        // Stores String values of the name and reason to visit a place. Trim removes leading
        // and trailing white spaces.
        val name = newPlaceEditText.text.toString().trim()
        val reason = reasonToTravel.text.toString().trim()
        // If the name is empty we display a warning to enter a name.
        if (name.isEmpty()){
            Toast.makeText(this, "Enter a place name", Toast.LENGTH_SHORT).show()
            // Than if the reason is empty we display a warning to enter a reason.
        } else if (reason.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_reason_toast, name), Toast.LENGTH_SHORT).show()
        } else {
            // If there is a valid name and reason we store the place as a new place Object with the name
                // and reason.
//            val newPlace = Place(name, reason)
            // We then pass that object to our placesViewModel to add it and store the position returned
            // by the view model.
//            val positionAdded = placesViewModel.addNewPlace(newPlace)
            // If that place is already in our list of Places we return a -1 and do not actually add it.
//            if (positionAdded == -1) {
//                // warning to update the user it already exists.
//                Toast.makeText(this, "You already added that place.", Toast.LENGTH_SHORT).show()
//            }else { TODO
                // Notifies the adapater a new object was added to the list of places and to update.
//                placeRecyclerAdapter.notifyItemInserted(positionAdded)
                // calls our clearForm and hideKeyboard functions.
                clearForm()
                hideKeyboard()
//            } TODO+
        }

    }

    private fun hideKeyboard() {
        // checks to see if there is a keyboard on screen and attempts to hide it if there is.
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)}
    }

    private fun clearForm() {
        // clears the edit text fields after a place is added to the list.
        newPlaceEditText.text.clear()
        reasonToTravel.text.clear()
    }

    override fun onMapRequestButtonClicked(place: Place) {
        // uses implicit intents to tell the Device to use an app that takes location URIs and display
        // a location that matches the name of the place.
        Toast.makeText(this, "${place.name} map icon was clicked", Toast.LENGTH_SHORT).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        // starts the activity with the implicit intent.
        startActivity(mapIntent)
    }

    override fun onStarredStatusChanged(place: Place, isStarred: Boolean) {
        place.starred = isStarred
        placesViewModel.updatePlace(place)
    }
    // Last two Override our interface functions of the OnListItemSwipeListener class.
//    override fun onListItemMoved(from: Int, to: Int) {
//        // Calls the function of the places view model that two views were moved.
//        placesViewModel.movePlace(from, to)
//        // Lets the adapter know so that the positions can be altered on screen.
//        placeRecyclerAdapter.notifyItemMoved(from, to)
//    }

    override fun onListItemDeleted(position: Int) {
        // stores a Place Object at a given position determined by the location of the view on screen.
//        val deletedPlace = placesViewModel.deletePlace(position) TODO
        // Tells the adaptor a view was deleted and to update the views on screen.
        placeRecyclerAdapter.notifyItemRemoved(position)

        // SnackBar displays a delete message and offers an undo button to restore the deleted item.
//        Snackbar.make(findViewById(R.id.wishlist_container),
//            getString(R.string.place_deleted, deletedPlace.name),
//            Snackbar.LENGTH_LONG)
//            // sets the text color of the message and the background of the snack bar.
//            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
//            .setBackgroundTint(ContextCompat.getColor(this, R.color.black))
//            // Sets the action of the undo string. If it is clicked it tells the viewModel to add
//            // a new place wit the deleted items.
//            .setAction(getString(R.string.undo)) {
//                placesViewModel.addNewPlace(deletedPlace, position)
//                placeRecyclerAdapter.notifyItemInserted(position)
//            }.show()
    }
}