package com.belyakov.listofcats.presentation.favoriteCats

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityFavoriteCatsBinding
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewOutput
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCatsActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteCatAdapter
    private lateinit var binding: ActivityFavoriteCatsBinding

    private val viewOutput: FavoriteCatViewOutput by viewModel<FavoriteCatViewModel>()

    @SuppressLint("NotifyDataSetChanged", "LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteCatAdapter(emptyList()) { cat ->
            viewOutput.removeFromFavoritesCats(cat)
        }

        binding.favoriteCatsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            Log.d("FavoriteCatsActivityAdapter", "getFavoriteCats() called, result: ${adapter.getSize()}")
            viewOutput.favoriteCatsFlow.collect { favoriteCats ->
                adapter.cats = favoriteCats
                adapter.updateList(favoriteCats)
            }
        }
    }
}