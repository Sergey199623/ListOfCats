package com.belyakov.listofcats.navigation

import com.belyakov.listofcats.HeadActivity

typealias HeadActivityActions = (HeadActivity) -> Unit

class HeadActivityActions {

    var mainActivity: HeadActivity? = null
        set(activity) {
            field = activity
            if (activity != null) {
                actions.forEach { it(activity) }
                actions.clear()
            }
        }

    private val actions = mutableListOf<HeadActivityActions>()

    operator fun invoke(action: HeadActivityActions) {
        val activity = this.mainActivity
        if (activity == null) {
            actions += action
        } else {
            action(activity)
        }
    }

    fun clear() {
        actions.clear()
    }
}