package com.example.travelwishlistapi

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface OnDataChangedListener {
    // fun onListItemMoved(from: Int, to: Int) // moving up and down.
    fun onListItemDeleted(position: Int)
}

class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
    0, // to reorder
    ItemTouchHelper.RIGHT // to delete
) {
    override fun onMove( // For moving up and down
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
//        val fromPosition = viewHolder.adapterPosition // Where in the list.
//        val toPosition = target.adapterPosition // Where was it moved to?
//        onDataChangedListener.onListItemMoved(fromPosition, toPosition)
        return true // is the move permitted.
    }

    // For left and right
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.RIGHT) {
            onDataChangedListener.onListItemDeleted(viewHolder.adapterPosition)
        }
    }
}