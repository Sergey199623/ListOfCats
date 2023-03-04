package com.belyakov.listofcats.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belyakov.listofcats.R
import com.belyakov.listofcats.data.database.Cat
import com.bumptech.glide.Glide

class CatAdapter(
    private val cats: MutableList<Cat>,
    private val onFavoriteClick: (Cat) -> Unit
) : PagingDataAdapter<Cat, CatAdapter.CatViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)
        holder.favoriteButton.setOnClickListener {
            if (cat != null) {
                onFavoriteClick(cat)
            }
        }
    }

    fun updateCat(cat: Cat) {
        val index = cats.indexOfFirst { it.id == cat.id }
        if (index != -1) {
            cats[index] = cat
            notifyDataSetChanged()
        }
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.catImageView)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)

        fun bind(cat: Cat?) {
            if (cat != null) {
                Glide.with(itemView)
                    .load(cat.url)
                    .into(imageView)
                setFavoriteIcon(cat.isFavorite)
            }
        }

        private fun setFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_favorite)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_not_favorite)
            }
        }
    }
}