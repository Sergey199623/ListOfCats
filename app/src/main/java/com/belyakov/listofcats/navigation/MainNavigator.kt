package com.belyakov.listofcats.navigation

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.belyakov.listofcats.HeadActivity
import com.belyakov.listofcats.R
import com.belyakov.listofcats.base.BaseScreen
import com.belyakov.listofcats.base.Event

const val ARG_SCREEN = "screen"

class MainNavigator(
    application: Application
) : AndroidViewModel(application), Navigator {

    val whenActivityActive = HeadActivityActions()

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    @SuppressLint("CommitTransaction")
    fun launchFragment(
        activity: HeadActivity,
        screen: BaseScreen,
        addToBackStacks: Boolean = true
    ) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStacks) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}