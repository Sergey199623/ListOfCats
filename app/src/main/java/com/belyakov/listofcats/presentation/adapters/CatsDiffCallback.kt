package com.belyakov.listofcats.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.belyakov.listofcats.data.database.Cat

class CatsDiffCallback(
    private val oldList: List<Cat>,
    private val newList: List<Cat>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].url == newList[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}