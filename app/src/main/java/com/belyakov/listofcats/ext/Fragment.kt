package com.belyakov.listofcats.ext

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        val tempBinding = viewBindingFactory(thisRef.requireView())

        /**
         * Событие [Lifecycle.State.DESTROYED] обрабатывается раньше, чем вызывается onDestroyView (@see [Fragment.performDestroyView])
         * Поэтому нужно предусмотреть возможность обратиться к биндингу в onDestroyView, то есть создать его заново, не сохранять в [binding]
         */
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            binding = tempBinding
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                    lifecycle.removeObserver(this)
                }
            })
        }
        return tempBinding
    }
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)