package ru.chernakov.rocketscienceapp.presentation.ui.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.extension.androidx.fragment.app.addFragment

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        if (savedInstanceState == null) {
            supportFragmentManager.addFragment(createFragment()).commit()
        }
    }

    abstract fun createFragment(): Fragment

    @LayoutRes
    protected abstract fun getLayout(): Int
}