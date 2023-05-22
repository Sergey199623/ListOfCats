package com.belyakov.listofcats.presentation.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewOutput
import kotlinx.android.synthetic.main.part_result.view.baseLayoutProgressBar

class CatsListFragment : BaseFragment() {

    class Screen : BaseScreen

    private val binding by viewBinding { FragmentCatsListBinding.bind(it) }
//    private val fragmentContext: MtsJrPaywallFragmentContext by args(EXTRA_FRAGMENT_CONTEXT)

    private lateinit var adapter: CatAdapter

    private val viewOutput: CatViewOutput by screenViewModel<CatViewModel>()

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
            adapter = CatAdapter { cat ->
                val isFavorite = !cat.isFavorite
                viewOutput.onChangeFavoriteStatusCat(cat.copy(isFavorite = isFavorite))
                adapter.updateCat(cat)
                Toast.makeText(
                    requireContext(),
                    if (isFavorite) "Котик добавлен в избранное" else "Котик удален из избранного",
                    Toast.LENGTH_SHORT
                ).show()
            }

            favoriteCatsFab.setOnClickListener { viewOutput.onFavoriteListClicked() }
            catsRecyclerView.adapter = adapter

            lifecycleScope.launchWhenStarted {
                viewOutput.catsFlow.collect { resultCats ->
                    adapter.setItems(resultCats.toMutableList())
                }
            }

            lifecycleScope.launchWhenStarted {
                viewOutput.progressBarFlow.collect { isVisible ->
                    root.baseLayoutProgressBar.isVisible = isVisible
                }
            }
        }
    }
}