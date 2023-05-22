package com.belyakov.listofcats.presentation.favoriteCats

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.databinding.ActivityFavoriteCatsBinding
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.cats.CatsActivity
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewOutput
import kotlinx.android.synthetic.main.part_result.view.baseLayoutProgressBar
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

        adapter = FavoriteCatAdapter(
            { cat -> viewOutput.onRemoveFromFavoriteCats(cat) },
            { cat -> viewOutput.onDownloadFavoriteCat(cat.url) }
        )

        binding.favoriteCatsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewOutput.favoriteCatsFlow.collect { resultCats ->
                adapter.setItems(resultCats.toMutableList())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewOutput.progressBarFlow.collect { isVisible ->
                binding.root.baseLayoutProgressBar.isVisible = isVisible
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, CatsActivity::class.java)
        startActivity(intent)
        finish()
    }
}