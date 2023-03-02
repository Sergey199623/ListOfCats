package com.belyakov.listofcats.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belyakov.listofcats.R
import com.belyakov.listofcats.data.database.Cat
import com.bumptech.glide.Glide

class CatAdapter(
    var cats: List<Cat>,
    var onFavoriteClick: (Cat) -> Unit
) : RecyclerView.Adapter<CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)
        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(cat)
            holder.favoriteButton(cat.isFavorite)
        }
    }

    override fun getItemCount() = cats.size
}

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.catImageView)
    val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)

    fun bind(cat: Cat) {
        Glide.with(itemView)
            .load(cat.url)
            .into(imageView)
        if (cat.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        }
    }

    fun favoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        }
    }
}