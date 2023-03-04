package com.belyakov.listofcats.presentation.favoriteCats

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityFavoriteCatsBinding
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewOutput
import kotlinx.coroutines.flow.collect
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

        adapter = FavoriteCatAdapter(mutableListOf(),
            { cat -> viewOutput.onRemoveFromFavoriteCats(cat) },
            { cat -> viewOutput.onDownloadFavoriteCat(cat.url) }
        )

        binding.favoriteCatsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewOutput.favoriteCatsFlow.collect { resultCats ->
                adapter.setItems(resultCats)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewOutput.downloadCatsFlow.collect { resultDownload ->
                if (resultDownload) {
                    Toast.makeText(
                        this@FavoriteCatsActivity,
                        "Изображение котика успешно загружено",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}