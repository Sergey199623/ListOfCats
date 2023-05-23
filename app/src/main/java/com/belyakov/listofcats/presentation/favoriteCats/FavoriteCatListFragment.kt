package com.belyakov.listofcats.presentation.favoriteCats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.base.BaseFragment
import com.belyakov.listofcats.base.BaseScreen
import com.belyakov.listofcats.databinding.FragmentFavoriteCatsListBinding
import com.belyakov.listofcats.ext.viewBinding
import com.belyakov.listofcats.factory.screenViewModel
import com.belyakov.listofcats.presentation.adapters.FavoriteCatAdapter
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import kotlinx.android.synthetic.main.part_result.view.baseLayoutProgressBar

class FavoriteCatListFragment : BaseFragment() {

    class FavoriteScreen : BaseScreen

    private val binding by viewBinding { FragmentFavoriteCatsListBinding.bind(it) }

    private lateinit var adapter: FavoriteCatAdapter

    override val viewModel by screenViewModel<FavoriteCatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_cats_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            adapter = FavoriteCatAdapter(
                { cat -> viewModel.onRemoveFromFavoriteCats(cat) },
                { cat -> viewModel.onDownloadFavoriteCat(cat.url) }
            )

            favoriteCatsRecyclerView.adapter = adapter

            lifecycleScope.launchWhenStarted {
                viewModel.favoriteCatsFlow.collect { resultCats ->
                    adapter.setItems(resultCats.toMutableList())
                }
            }

            lifecycleScope.launchWhenStarted {
                viewModel.progressBarFlow.collect { isVisible ->
                    root.baseLayoutProgressBar.isVisible = isVisible
                }
            }
        }
    }
}