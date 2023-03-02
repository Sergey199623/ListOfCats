package com.belyakov.listofcats.presentation.favoriteCats

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.databinding.ActivityFavoriteCatsBinding
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewOutput
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCatsActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteCatAdapter
    private lateinit var binding: ActivityFavoriteCatsBinding

    private val viewOutput: FavoriteCatViewOutput by viewModel<FavoriteCatViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_cats)

        adapter = FavoriteCatAdapter(emptyList()) { cat ->
            viewOutput.removeFromFavoritesCats(cat)
        }

        binding.favoriteCatsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewOutput.favoriteCatsFlow.collect { favoriteCats ->
                adapter.cats = favoriteCats
                adapter.notifyDataSetChanged()
            }
        }
    }
}