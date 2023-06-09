package com.belyakov.listofcats.factory

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belyakov.listofcats.base.BaseFragment
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.MainNavigator
import com.belyakov.listofcats.navigation.Navigator

class ViewModelFactory(
    private val fragment: BaseFragment,
    private val interactor: CatInteractor,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider =
            ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java]

        val constructor = modelClass.getConstructor(Navigator::class.java, CatInteractor::class.java)
        return constructor.newInstance(navigator, interactor)
    }
}

inline fun <reified VM : ViewModel> BaseFragment.screenViewModel(interactor: CatInteractor) = viewModels<VM> {
    ViewModelFactory(this, interactor)
}