package com.example.hotpizzaapp

import android.os.Bundle
import android.view.Gravity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialog

private const val COLLAPSED_HEIGHT = 220

class BottomFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)


            behavior.peekHeight = ((COLLAPSED_HEIGHT + 85) * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            val coordinator = (it as BottomSheetDialog).findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout = it.findViewById<FrameLayout>(com.google.android.material.R.id.container)

            val buttons = it.layoutInflater.inflate(R.layout.button, null)


            buttons.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                height = (100 * density).toInt()
                gravity = Gravity.BOTTOM
            }
            containerLayout?.addView(buttons)

            buttons.post {
                (coordinator?.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    buttons.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )

                    this.bottomMargin = (buttons.measuredHeight - 8 * density).toInt()
                    containerLayout?.requestLayout()
                }
            }
            val button = it.findViewById<Button>(R.id.btnBottomSheet)
            button?.setOnClickListener {
                activity?.let {
                    it.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, CartFragment())
                        addToBackStack(null)
                        commit()
                    }
                    dismiss()
                }
            }
            val ivPizza = it.findViewById<ImageView>(R.id.ivPizzaSheet)
            ivPizza?.setOnClickListener {
                activity?.let {
                    it.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, ImageFragment())
                        addToBackStack(null)
                        commit()
                    }
                    dismiss()
                }
            }
        }
    }

}