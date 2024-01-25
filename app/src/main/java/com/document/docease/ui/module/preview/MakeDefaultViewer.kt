package com.document.docease.ui.module.preview

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.document.docease.databinding.BsMakeDefaultBinding
import com.document.docease.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class MakeDefaultViewer : BottomSheetDialogFragment() {
    lateinit var  btnMarkNow: Button
    private var skippedDefault = true
    private var finalUri: String? = null
    private var finalData: String? = null

    lateinit var binding: BsMakeDefaultBinding

    var file: File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsMakeDefaultBinding.inflate(layoutInflater)
        var args: Bundle? = Bundle()
        args = arguments
        finalData = args!!.getString(Constant.INTENT_DATA)
        finalUri = args.getString(Constant.INTENT_FILE_URI)
        binding.btnMarkNow.setOnClickListener {
            skippedDefault = false
            val selector = Intent(Intent.ACTION_VIEW)
            selector.data = Uri.parse(finalData)
            selector.putExtra(Constant.FILE_URI, finalUri)
            startActivity(selector)
            dismiss()
        }
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}
