package com.example.hotpizzaapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.hotpizzaapp.data.BundleKeys
import com.example.hotpizzaapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

private const val COLLAPSED_HEIGHT = 220

class BottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density
        dialog?.let {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = ((COLLAPSED_HEIGHT + 80) * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            val coordinator =
                (it as BottomSheetDialog).findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout =
                it.findViewById<FrameLayout>(com.google.android.material.R.id.container)

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

            val button = it.findViewById<LinearLayout>(R.id.btnBottomSheet)
            button?.setOnClickListener { buttonClick() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        tvPizzaName.text = arguments?.getString(BundleKeys.NAME)
        descriptionPizza.text = arguments?.getString(BundleKeys.DESCRIPTION)
        Picasso.get().load(arguments?.getString(BundleKeys.IMAGEURL)).into(ivPizzaSheet)

        val price = requireDialog().findViewById<TextView>(R.id.pricePizza)
        price?.text = getString(R.string.price, arguments?.getString(BundleKeys.PRICE))

        ivPizzaSheet.setOnClickListener {
            viewClick()
        }
    }

    private fun viewClick() {
        val bundle = Bundle()
        val fragmentImage = ImageFragment()
        bundle.apply {
            putString(BundleKeys.IMAGEURL, arguments?.getString(BundleKeys.IMAGEURL))
            putString(BundleKeys.PRICE, arguments?.getString(BundleKeys.PRICE))
        }

        fragmentImage.arguments = bundle
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragmentImage)
            addToBackStack(null)
            commit()
        }
        dismiss()
    }

    private fun buttonClick() {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, CartFragment())
            addToBackStack(null)
            commit()
        }
        dismiss()
    }
}