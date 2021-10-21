package com.example.hotpizzaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.Gravity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

private const val COLLAPSED_HEIGHT = 220

class BottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_layout, container,false )

        binding.tvPizzaName.text = arguments?.getString("name")
        binding.descriptionPizza.text = arguments?.getString("description")
        Picasso.get().load(arguments?.getString("imageUrl")).into(binding.ivPizzaSheet)

        return binding.root
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = ((COLLAPSED_HEIGHT + 60) * density).toInt()
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
            val button = it.findViewById<LinearLayout>(R.id.btnBottomSheet)

            button?.setOnClickListener {
                requireActivity().let {
                    it.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, CartFragment())
                        addToBackStack(null)
                        commit()
                    }
                    dismiss()
                }
            }

            val price = it.findViewById<TextView>(R.id.pricePizza)
            price?.text = arguments?.getString("price") + "₽"


            binding.ivPizzaSheet.setOnClickListener {
                requireActivity().let {

                    val bundle = Bundle()
                    val fragmentImage = ImageFragment()
                    bundle.putString("imageUrl",arguments?.getString("imageUrl"))

                    fragmentImage.arguments = bundle

                    it.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fragmentImage)
                        addToBackStack(null)
                        commit()
                    }
                    dismiss()
                }
            }
        }
    }

}