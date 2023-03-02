package com.belyakov.listofcats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityFavoriteCatsBinding
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.viewModel.CatViewModel
import com.belyakov.listofcats.presentation.viewModel.CatViewOutput
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCatsActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteCatAdapter
    private lateinit var binding: ActivityFavoriteCatsBinding

    private val viewOutput: CatViewOutput by viewModel<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_cats)

        adapter = FavoriteCatAdapter(emptyList()) { cat ->
            viewOutput.removeFromFavoritesCats(cat)
        }
        binding.favoriteCatsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewOutput.favoriteCatsFlow.collect { resultCats ->
                adapter.cats = resultCats
                adapter.notifyDataSetChanged()
            }
        }

    }
}