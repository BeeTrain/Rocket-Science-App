package ru.chernakov.rocketscienceapp.presentation.paint

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_paint.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.extension.android.content.getColorKtx
import ru.chernakov.rocketscienceapp.navigation.PaintNavigation
import ru.chernakov.rocketscienceapp.paint.R
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.widget.colorpicker.ColorPicker

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

        btColorPicker.setOnClickListener { ColorPicker.show(childFragmentManager) }
        setupToolbar()

        paintViewModel.selectedColorResData.observe(viewLifecycleOwner) {
            vSelectedColor.background.setColorFilter(it, PorterDuff.Mode.SRC_IN)
            vPaint.setPaintColor(it)
        }
    }

    override fun onColorSelected(color: Int?) {
        color?.let { paintViewModel.setSelectedColorRes(it) }
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
}