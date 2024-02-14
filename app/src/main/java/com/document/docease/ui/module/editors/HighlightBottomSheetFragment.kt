package com.document.docease.ui.module.editors

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artifex.sonui.editor.Utilities
import com.document.docease.R
import com.document.docease.databinding.FragmentHighlightBottomSheetBinding
import com.document.docease.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.document.docease.ui.module.editors.adapters.ColorListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HighlightBottomSheetFragment : BottomSheetDialogFragment() {
    private var colorHeightLight: Boolean = false
    private var colorFont: Boolean = false
    private lateinit var fragmentHighlightBottomSheetBinding: FragmentHighlightBottomSheetBinding
    private val colorListHighlight = ArrayList(
        listOf(
            "#ffff00",
            "#00ff00",
            "#00ffff",
            "#ff00ff",
            "#0000ff",
            "#ff0000",
            "#808000",
            "#008000",
            "#008080",
            "#800080",
            "#000080",
            "#800000",
            "#ffffff",
            "#808080",
            "#c0c0c0",
            "#000000"
        )
    )
    private val colorListFont = ArrayList(
        listOf(
            "#000000",
            "#FFFFFF",
            "#D8D8D8",
            "#808080",
            "#EEECE1",
            "#1F497D",
            "#0070C0",
            "#C0504D",
            "#9BBB59",
            "#8064A2",
            "#4BACC6",
            "#F79646",
            "#FF0000",
            "#FFFF00",
            "#DBE5F1",
            "#F2DCDB",
            "#EBF1DD",
            "#00B050"
        )
    )
    private var mFinalColorList = ArrayList<String>()


    private var homeViewModel: HomeViewModel? = null

    //type = 1 for Highlight and type = 0 for font color
    private var type: Int? = null
    private var showKeyboard = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        arguments?.let {
            colorHeightLight = it.getBoolean(Constant.COLOR_HEIGHT_LIGHT)
            colorFont = it.getBoolean(Constant.COLOR_FONT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHighlightBottomSheetBinding = FragmentHighlightBottomSheetBinding.inflate(layoutInflater,container,false)
        if(showKeyboard){
            if (Build.VERSION .SDK_INT > Build.VERSION_CODES.S_V2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.showKeyboard(requireContext())
                },300)
            }
        }
        return fragmentHighlightBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (colorHeightLight) {
            fragmentHighlightBottomSheetBinding.tvTitle.text = getString(R.string.highlight)
            fragmentHighlightBottomSheetBinding.iconFont.visibility = View.GONE
            fragmentHighlightBottomSheetBinding.iconHighlight.visibility = View.VISIBLE
            mFinalColorList = colorListHighlight
            type = 1
        } else if (colorFont) {
            fragmentHighlightBottomSheetBinding.tvTitle.text = getString(R.string.color)
            fragmentHighlightBottomSheetBinding.removeHighlight.visibility = View.GONE
            fragmentHighlightBottomSheetBinding.iconHighlight.visibility = View.GONE
            fragmentHighlightBottomSheetBinding.iconFont.visibility = View.VISIBLE
            mFinalColorList = colorListFont
            type = 0
        }
        val colorListAdapter = ColorListAdapter(mFinalColorList,
            requireContext(), object : ColorListAdapter.ColorItemClickListener {
                override fun onColorClicked(colorCode: String?) {
                    if (type == 1) {
                        homeViewModel!!.getTriggerHighlight().setValue(colorCode)
                    } else {
                        homeViewModel!!.getTriggerFontColor().setValue(colorCode)
                    }
                    dismiss()
                }
            })
        fragmentHighlightBottomSheetBinding.colorRv.setHasFixedSize(true)
        fragmentHighlightBottomSheetBinding.colorRv.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        fragmentHighlightBottomSheetBinding.colorRv.adapter = colorListAdapter
        colorListAdapter.notifyDataSetChanged()

        fragmentHighlightBottomSheetBinding.removeHighlight.setOnClickListener {
            homeViewModel!!.getTriggerHighlight().value = Constant.TRANSPARENT
            dismiss()
        }

        fragmentHighlightBottomSheetBinding.bsBackBtn.setOnClickListener { dismiss() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(showKeyboard){
            if (Build.VERSION .SDK_INT > Build.VERSION_CODES.S_V2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.showKeyboard(requireContext())
                },300)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Boolean,param2: Boolean, showKeyboard: Boolean) =
            HighlightBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(Constant.COLOR_HEIGHT_LIGHT, param1)
                    putBoolean(Constant.COLOR_FONT,param2)
                }
                this.showKeyboard = showKeyboard
            }
    }
}