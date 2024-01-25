package com.document.docease.ui.module.editors

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artifex.sonui.editor.Utilities
import com.document.docease.R
import com.document.docease.databinding.FragmentEditFontBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.document.docease.ui.module.editors.adapters.EditFontListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFontBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var fragmentEditFontBottomSheetBinding: FragmentEditFontBottomSheetBinding
    private val viewEditorViewModel by viewModels<ViewEditorViewModel>()
    private val fontSizeList = ArrayList<String>()
    private val fontFamilyList = ArrayList<String>()
    private var homeViewModel: HomeViewModel? = null
    private var showKeyboard = false


    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager?.beginTransaction()
            ft?.add(this, tag)
            ft?.commitAllowingStateLoss()
        } catch (ignored: IllegalStateException) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentEditFontBottomSheetBinding =
            FragmentEditFontBottomSheetBinding.inflate(layoutInflater, container, false)
        if (showKeyboard) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.showKeyboard(requireContext())
                }, 300)
            }
        }
        return fragmentEditFontBottomSheetBinding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editFontListAdapterFontFamily = EditFontListAdapter(fontFamilyList,
            requireContext(), object : EditFontListAdapter.FontItemClickListener {
                override fun onFontItemClicked(fontItem: String?) {
                    homeViewModel!!.getTriggerFontFamily().value = fontItem
                    dismiss()
                }
            })

        val editFontListAdapterFontSize = EditFontListAdapter(fontSizeList,
            requireContext(), object : EditFontListAdapter.FontItemClickListener {
                override fun onFontItemClicked(fontItem: String?) {
                    homeViewModel!!.getTriggerFontSize().value = formatSize(fontItem!!).toDouble()
                    dismiss()
                }
            })
        fragmentEditFontBottomSheetBinding.apply {
            fontRv.setOnTouchListener { v, event ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                true
            }

            fontSizeRv.setOnTouchListener { v, event ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                true
            }

            fontRv.setHasFixedSize(true)
            fontRv.layoutManager = LinearLayoutManager(requireContext())
            fontRv.adapter = editFontListAdapterFontFamily

            fontSizeRv.setHasFixedSize(true)
            fontSizeRv.layoutManager = LinearLayoutManager(requireContext())
            fontSizeRv.adapter = editFontListAdapterFontSize

            viewEditorViewModel.getFontSizeList()
            viewEditorViewModel.getFontFamilyList()

            viewEditorViewModel.fontSizeLiveData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    editFontListAdapterFontSize.updateData(it)
                }
            }

            viewEditorViewModel.fontFamilyLiveData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    editFontListAdapterFontFamily.updateData(it)
                }
            }

            bsBackBtn.setOnClickListener { dismiss() }
        }
    }

    private fun formatSize(str: String): Float {
        return str.substring(0, str.length - 3).toInt().toFloat()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(showKeyboard){
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.showKeyboard(requireContext())
                }, 300)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(showKeyboard: Boolean) =
            EditFontBottomSheetFragment().apply {
                this.showKeyboard = showKeyboard
            }
    }
}