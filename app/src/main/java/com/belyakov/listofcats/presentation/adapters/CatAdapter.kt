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
    var cats: MutableList<Cat>,
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
            val newCat = cat.copy(isFavorite = !cat.isFavorite)
            cats[position] = newCat
            holder.setFavoriteIcon(newCat.isFavorite)
        }
    }

    fun updateCat(cat: Cat) {
        val index = cats.indexOfFirst { it.id == cat.id }
        if (index != -1) {
            cats[index] = cat
            notifyDataSetChanged()
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
        setFavoriteIcon(cat.isFavorite)
    }

    fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_not_favorite)
        }
    }
}