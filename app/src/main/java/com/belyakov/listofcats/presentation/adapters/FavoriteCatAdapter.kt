package com.belyakov.listofcats.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belyakov.listofcats.R
import com.belyakov.listofcats.data.database.Cat
import com.bumptech.glide.Glide

class FavoriteCatAdapter(
    private val onFavoriteClick: (Cat) -> Unit,
    private val onDownloadClick: (Cat) -> Unit,
) : RecyclerView.Adapter<FavoriteCatViewHolder>() {

    private var cats: MutableList<Cat> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_cat, parent, false)
        return FavoriteCatViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavoriteCatViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)

        holder.removeButton.setOnClickListener {
            onFavoriteClick(cat)
            // Добавить обработку клика на кнопку "Удалить"
            val updatedList = cats.toMutableList()
            updatedList.removeAt(position)
            cats = updatedList
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount - position)
        }
        holder.downloadButton.setOnClickListener {
            onDownloadClick(cat)
        }
    }

    fun setItems(items: MutableList<Cat>) {
        val diffResult = DiffUtil.calculateDiff(FavoriteCatsDiffCallback(this.cats, cats))
        this.cats = items
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = cats.size
}

class FavoriteCatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.catImageViewFavoriteCats)
    val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)
    val downloadButton: ImageButton = itemView.findViewById(R.id.downloadButton)

    fun bind(cat: Cat) {
        Glide.with(itemView)
            .load(cat.url)
            .into(imageView)

        removeButton.setImageResource(R.drawable.ic_delete)
    }
}