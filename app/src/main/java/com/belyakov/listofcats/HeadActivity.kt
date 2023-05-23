package com.belyakov.listofcats

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.belyakov.listofcats.base.BaseFragment
import com.belyakov.listofcats.navigation.MainNavigator
import com.belyakov.listofcats.presentation.cats.CatsListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadActivity : AppCompatActivity() {

    private val navigator by viewModels<MainNavigator> { ViewModelProvider.AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_head)
        if (savedInstanceState == null) {
            // addToBackStacks - первоначальный экран не добавляем в стек, чтобы спокойно закрывать приложение, а не видеть белый экран
            navigator.launchFragment(this, CatsListFragment.CatsListFragment(), addToBackStacks = false)
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.headActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.headActivity = null
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            val result = navigator.result.value?.getValue() ?: return
            if (f is BaseFragment) {
                f.viewModel.onResult(result)
            }
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        }
    }
}