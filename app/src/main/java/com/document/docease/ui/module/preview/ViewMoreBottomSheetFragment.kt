package com.document.docease.ui.module.preview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.artifex.sonui.ChoosePathActivity
import com.document.docease.databinding.BsViewMoreBinding
import com.document.docease.ui.module.editors.HomeViewModel
import com.document.docease.utils.Constant
import com.document.docease.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class ViewMoreBottomSheetFragment : BottomSheetDialogFragment() {
    private var mFile: File? = null
    private var homeViewModel: HomeViewModel? = null
    lateinit var binding: BsViewMoreBinding
    private val viewGroup:ViewGroup ?=null

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = BsViewMoreBinding.inflate(LayoutInflater.from(context),viewGroup,false)

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        arguments = arguments
        if (arguments != null && requireArguments().containsKey(Constant.FILE)) {
            mFile = requireArguments()[Constant.FILE] as? File
        }

        if (Utility.fileType(mFile!!) == 6) {
            binding.viewMoreFind.visibility = View.GONE
        }
        if (Utility.fileType(mFile!!) == 9) {
            binding.viewMoreSave.visibility = View.GONE
        }
        binding.viewMoreSave.setOnClickListener {
            if (Utility.fileType(mFile!!) == 6 || Utility.fileType(mFile!!) == 11) {
                val intent = Intent(activity, ChoosePathActivity::class.java)
                intent.putExtra(Constant.FILE_PATH, mFile!!.path)
                intent.putExtra(Constant.IS_IMAGE_FILE, true)
                requireActivity().startActivity(intent)
            } else {
                homeViewModel?.getTriggerSave()?.setValue(true)
            }
            dismiss()
        }
        binding.viewMoreSavePdf.setOnClickListener {
            homeViewModel?.getTriggerSaveAsPdf()?.value = true
            dismiss()
        }
        binding.viewMoreFind.setOnClickListener {
            homeViewModel?.getTriggerSearch()?.value = true
            dismiss()
        }
        return binding.root
    }
}
