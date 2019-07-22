package ru.chernakov.rocketscienceapp.extension.androidx.fragment.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.chernakov.rocketscienceapp.R

fun FragmentManager.addFragment(fragment: Fragment, containerId: Int = R.id.container): Transaction {
    return Transaction(this, fragment, Transaction.Type.ADD, containerId)
}

fun FragmentManager.replaceFragment(fragment: Fragment, containerId: Int = R.id.container): Transaction {
    return Transaction(this, fragment, Transaction.Type.REPLACE, containerId)
}