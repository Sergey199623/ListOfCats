package com.belyakov.listofcats.navigation

import com.belyakov.listofcats.HeadActivity

typealias HeadActivityAction = (HeadActivity) -> Unit

class HeadActivityActions {

    var headActivity: HeadActivity? = null
        set(activity) {
            field = activity
            if (activity != null) {
                actions.forEach { it(activity) }
                actions.clear()
            }
        }

    private val actions = mutableListOf<HeadActivityAction>()

    operator fun invoke(action: HeadActivityAction) {
        val activity = this.headActivity
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