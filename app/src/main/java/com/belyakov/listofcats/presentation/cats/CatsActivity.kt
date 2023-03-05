package com.belyakov.listofcats.presentation.cats

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityCatsBinding
import com.belyakov.listofcats.presentation.adapters.CatAdapter
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewModel
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewOutput
import com.belyakov.listofcats.presentation.favoriteCats.FavoriteCatsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatsActivity : AppCompatActivity() {

    private lateinit var adapter: CatAdapter
    private lateinit var binding: ActivityCatsBinding

    private val viewOutput: CatViewOutput by viewModel<CatViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CatAdapter(mutableListOf()) { cat ->
            val isFavorite = !cat.isFavorite
            viewOutput.onAddToFavoriteCats(cat.copy(isFavorite = isFavorite))
            adapter.updateCat(cat)
            Toast.makeText(
                this,
                if (isFavorite) "Котик добавлен в избранное" else "Котик удален из избранного",
                Toast.LENGTH_SHORT
            ).show()
        }

        with(binding) {
            favoriteCatsButton.setOnClickListener { showFavoriteScreen() }
            catsRecyclerView.adapter = adapter
        }

        lifecycleScope.launchWhenStarted {
            viewOutput.catsPagingFlow.collect { data ->
                adapter.submitData(data)
            }
        }
    }

    private fun showFavoriteScreen() {
        val intent = Intent(this, FavoriteCatsActivity::class.java)
        startActivity(intent)
    }
}