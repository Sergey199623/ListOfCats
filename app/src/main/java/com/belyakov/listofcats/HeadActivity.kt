package com.belyakov.listofcats

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.belyakov.listofcats.databinding.ActivityHeadBinding
import com.belyakov.listofcats.navigation.Navigator
import java.util.UUID

class HeadActivity : AppCompatActivity(), Navigator {

    private val navigator by viewModels<MainNavigator> { ViewModelProvider.AndroidViewModelFactory(application) }

    private lateinit var binding: ActivityHeadBinding

    private val fragmentListener: FragmentManager.FragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        super.onCreate(savedInstanceState)
        binding = ActivityHeadBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, createFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun launchNext() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragmentContainer, createFragment())
            .commit()
    }

    override fun update() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment is HasUuid) {
            binding.currentFragmentUuidTextView.text = currentFragment.getUuid()
        } else {
            binding.currentFragmentUuidTextView.text = ""
        }
        if (currentFragment is NumberListener) {
            currentFragment.onNewScreenNumber(1 + supportFragmentManager.backStackEntryCount)
        }
    }

    override fun generateUuid(): String = UUID.randomUUID().toString()

    private fun createFragment(): RandomFragment {
        return RandomFragment.newInstance(generateUuid())
    }

}