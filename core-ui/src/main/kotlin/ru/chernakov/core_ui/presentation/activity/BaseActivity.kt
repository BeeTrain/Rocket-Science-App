package ru.chernakov.core_ui.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.chernakov.core_ui.R
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        if (savedInstanceState == null) {
            supportFragmentManager.replaceFragment(createFragment()).commit()
        }
    }

    abstract fun createFragment(): Fragment

    @LayoutRes
    protected abstract fun getLayout(): Int

    companion object {
        fun makeIntent(context: Context) = Intent(context, BaseActivity::class.java)
    }
}