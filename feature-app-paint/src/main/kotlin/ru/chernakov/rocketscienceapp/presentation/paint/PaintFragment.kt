package ru.chernakov.rocketscienceapp.presentation.paint

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_paint.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.extension.android.content.getColorKtx
import ru.chernakov.rocketscienceapp.navigation.PaintNavigation
import ru.chernakov.rocketscienceapp.paint.R
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.widget.colorpicker.ColorPicker

class PaintFragment : BaseFragment(), ColorPicker.OnColorSelectListener {
    private val paintViewModel: PaintViewModel by viewModel()
    private val navigator: PaintNavigation by inject()

    override fun getLayout() = R.layout.fragment_paint

    override fun obtainViewModel() = paintViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintViewModel.initColorDefaultValue(requireContext().getColorKtx(R.color.orange_900))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupListeners()

        paintViewModel.selectedColorLiveData.observe(viewLifecycleOwner) {
            vSelectedColor.background.setColorFilter(it, PorterDuff.Mode.SRC_IN)
            vPaint.setPaintColor(it)
        }
        paintViewModel.menuVisibleLiveData.observe(viewLifecycleOwner) {
            if (it) {
                btMenu.setImageResource(R.drawable.ic_edit_on)
                fabMenu.showMenu(true)
            } else {
                btMenu.setImageResource(R.drawable.ic_edit_off)
                fabMenu.close(true)
                fabMenu.hideMenu(true)
            }
        }
        paintViewModel.selectedModeLiveData.observe(viewLifecycleOwner) {
            when (it) {
                PaintViewModel.PaintMode.DRAW -> {
                    btColorPicker.visibility = View.VISIBLE
                    ivEraser.visibility = View.GONE
                }
                PaintViewModel.PaintMode.ERASE -> {
                    vPaint.setEraseMode()
                    ivEraser.visibility = View.VISIBLE
                    btColorPicker.visibility = View.GONE
                }
                else -> {
                    // do Nothing
                }
            }
        }
    }

    override fun onColorSelected(color: Int?) {
        color?.let { paintViewModel.setSelectedColor(it) }
    }

    override fun onCancel() {
        // do nothing
    }

    private fun setupToolbar() {
        with(toolbarPaint) {
            toolbarTitle.text = getString(R.string.feature_title)
            setNavigationIcon(R.drawable.ic_arrow_back_white)
            setNavigationOnClickListener { navigator.fromPaintToAppFeatures() }
        }
    }

    private fun setupListeners() {
        btMenu.setOnClickListener { paintViewModel.toggleMenu() }
        fabPalette.setOnClickListener {
            fabMenu.close(true)
            Handler().postDelayed({ ColorPicker.show(childFragmentManager) }, FAB_MENU_CLOSE_DELAY)
        }
        fabEraser.setOnClickListener {
            fabMenu.close(true)
            Handler().postDelayed({ paintViewModel.setEraseMode() }, FAB_MENU_CLOSE_DELAY)
        }
        fabNewPicture.setOnClickListener {
            fabMenu.close(true)
            Handler().postDelayed({ vPaint.clearImage() }, FAB_MENU_CLOSE_DELAY)
        }
    }

    companion object {
        private const val FAB_MENU_CLOSE_DELAY = 300L
    }
}