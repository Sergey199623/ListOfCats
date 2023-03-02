package com.belyakov.listofcats

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityMainBinding
import com.belyakov.listofcats.presentation.adapters.CatAdapter
import com.belyakov.listofcats.presentation.viewModel.CatViewModel
import com.belyakov.listofcats.presentation.viewModel.CatViewOutput
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CatAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewOutput: CatViewOutput by viewModel<CatViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CatAdapter(emptyList()) { cat ->
            cat.isFavorite = !cat.isFavorite
            if (cat.isFavorite) {
                viewOutput.removeFromFavoritesCats(cat)
            } else {
                viewOutput.addToFavoriteCats(cat)
            }
        }
        binding.catsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewOutput.catsFlow.collect { resultCats ->
                adapter.cats = resultCats
                adapter.notifyDataSetChanged()
            }
        }
        viewOutput.getAllCats(1)
    }
}