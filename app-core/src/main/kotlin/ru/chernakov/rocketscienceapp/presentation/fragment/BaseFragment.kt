package ru.chernakov.rocketscienceapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.chernakov.rocketscienceapp.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.rocketscienceapp.presentation.error.ErrorHandler
import ru.chernakov.rocketscienceapp.presentation.error.ToastErrorView
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

abstract class BaseFragment : Fragment() {
    private var runOnResume: Runnable? = null
    private var isAfterOnSavedState: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = obtainViewModel()
        setupErrorHandling(viewModel.error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAfterOnSavedState = false
    }

    override fun onResume() {
        super.onResume()
        runOnResume?.run()
        runOnResume = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isAfterOnSavedState = true
    }

    private fun setupErrorHandling(errorLiveData: LiveData<Throwable>) {
        createErrorObserver()?.let {
            errorLiveData.observe(viewLifecycleOwner, it)
        }
    }

    fun postOnResume(run: Runnable) {
        if (isAfterOnSavedState) {
            runOnResume = run
        } else {
            run.run()
        }
    }

    fun startFragment(fragment: Fragment, isAddToBackStack: Boolean = true) {
        activity?.let {
            it.supportFragmentManager.replaceFragment(fragment).apply {
                addToBackStack = isAddToBackStack
                commit()
            }
        }
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected abstract fun obtainViewModel(): BaseViewModel

    protected open fun createErrorObserver(): Observer<Throwable>? = ErrorHandler(ToastErrorView(activity!!))
}