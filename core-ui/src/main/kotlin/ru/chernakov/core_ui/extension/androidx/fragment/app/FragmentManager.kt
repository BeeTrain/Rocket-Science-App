package ru.chernakov.core_ui.extension.androidx.fragment.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.chernakov.core_ui.R

fun FragmentManager.addFragment(fragment: Fragment, containerId: Int = R.id.container): Transaction {
    return Transaction(this, fragment, Transaction.Type.ADD, containerId)
}

fun FragmentManager.replaceFragment(fragment: Fragment, containerId: Int = R.id.container): Transaction {
    return Transaction(this, fragment, Transaction.Type.REPLACE, containerId)
}