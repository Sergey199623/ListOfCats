package com.belyakov.listofcats.presentation.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.base.BaseFragment
import com.belyakov.listofcats.base.BaseScreen
import com.belyakov.listofcats.databinding.FragmentCatsListBinding
import com.belyakov.listofcats.ext.viewBinding
import com.belyakov.listofcats.factory.screenViewModel
import com.belyakov.listofcats.presentation.adapters.CatAdapter
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewModel
import kotlinx.android.synthetic.main.part_result.view.baseLayoutProgressBar

class CatsListFragment : BaseFragment() {

    class CatsListFragment : BaseScreen

    private val binding by viewBinding { FragmentCatsListBinding.bind(it) }

    private lateinit var adapter: CatAdapter

    override val viewModel by screenViewModel<CatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            // if (база не пуста) { viewOutput.getAllCats(1) } переделать
            viewModel.onViewCreated(1)

            adapter = CatAdapter { cat ->
                val isFavorite = !cat.isFavorite
                viewModel.onChangeFavoriteStatusCat(cat.copy(isFavorite = isFavorite))
                adapter.updateCat(cat)
                viewModel.onSendToast(isFavorite)
            }

            favoriteCatsFab.setOnClickListener { viewModel.onFavoriteListClicked() }
            catsRecyclerView.adapter = adapter

            lifecycleScope.launchWhenStarted {
                viewModel.catsFlow.collect { resultCats ->
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