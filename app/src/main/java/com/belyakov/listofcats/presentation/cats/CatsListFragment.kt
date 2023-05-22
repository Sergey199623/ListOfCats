package com.belyakov.listofcats.presentation.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.databinding.FragmentCatsListBinding
import com.belyakov.listofcats.ext.viewBinding
import com.belyakov.listofcats.presentation.adapters.CatAdapter
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewModel
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewOutput
import kotlinx.android.synthetic.main.part_result.view.baseLayoutProgressBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CatsListFragment : Fragment() {

    private val binding by viewBinding { FragmentCatsListBinding.bind(it) }
//    private val fragmentContext: MtsJrPaywallFragmentContext by args(EXTRA_FRAGMENT_CONTEXT)

    private lateinit var adapter: CatAdapter

    private val viewOutput: CatViewOutput by viewModel<CatViewModel> { parametersOf(fragmentContext) }

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

            favoriteCatsFab.setOnClickListener { showFavoriteScreen() }
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

    companion object {
        private const val EXTRA_FRAGMENT_CONTEXT = "EXTRA_MTS_JR_PAYWALL_FRAGMENT_CONTEXT"

        fun show(activity: FragmentActivity, fragmentContext: MtsJrPaywallFragmentContext) {
            val fragmentManager = activity.supportFragmentManager

            val fragment = MtsJrPaywallFragment().apply {
                arguments = bundleOf(EXTRA_FRAGMENT_CONTEXT to fragmentContext)
            }

            fragmentManager.beginTransaction()
                .add(android.R.id.content, fragment)
                .addToBackStack(MtsJrPaywallFragment.toString())
                .commitAllowingStateLoss()
        }
    }
}