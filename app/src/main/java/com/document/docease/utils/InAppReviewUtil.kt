package com.document.docease.utils

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

class InAppReviewUtil(private val activity: Activity) {

    private var manager: ReviewManager? = null


    init {
        manager = ReviewManagerFactory.create(activity)
    }

    fun showInAppReview() {
        manager?.requestReviewFlow()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager?.launchReviewFlow(
                    activity,
                    reviewInfo
                )
            }
        }
    }


}