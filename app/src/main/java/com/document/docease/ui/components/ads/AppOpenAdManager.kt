import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.document.docease.ui.components.ads.isInterstitialAdShowing
import com.document.docease.utils.Constant
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.Date


interface OnShowAdCompleteListener {
    fun onShowAdComplete()
}

class AppOpenAdManager(application: Application, private val adUnit: String) :
    Application.ActivityLifecycleCallbacks {


    private var currentActivity: Activity? = null
    private val TAG = "AppOpenAd"

    private val lifecycleEventObserver = LifecycleEventObserver { source, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            Log.e(TAG, "ON_RESUME: showOpen Ad")
            if (canShowAd()) {
                currentActivity?.let { showAdIfAvailable(it) }
            } else {
                Log.e(TAG, "Cannot show Ad")
            }
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            Log.e("APP", "paused")
        }
    }

    init {
        loadAd(application)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
        // SDK or another activity class implemented by a third party mediation partner. Updating the
        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
        // one that shows the ad.
        if (!isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}


    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
    private var loadTime: Long = 0

    fun loadAd(context: Context) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable() || !Constant.showAds) {
            return
        }

        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            adUnit,
            request,
            object : AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = System.currentTimeMillis()
                    Log.d(TAG, "onAdLoaded.")
                    //Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show()
                }


                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.d(TAG, "onAdFailedToLoad: " + loadAdError.message)
                    //Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    /** Check if ad was loaded more than n hours ago. */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun canShowAd(): Boolean {
        if (isInterstitialAdShowing) return false
        val currentTimeInMillis = System.currentTimeMillis()
        val dateDifference: Long = currentTimeInMillis - loadTime
        return dateDifference > Constant.appOpenTime

    }


    /** Check if ad exists and can be shown. */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    private fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(
            activity,
            object : OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    // Empty because the user will go back to the activity that shows the ad.
                }
            }
        )
    }

    private fun showAdIfAvailable(
        activity: Activity,
        onShowAdCompleteListener: OnShowAdCompleteListener
    ) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(TAG, "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d(TAG, "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete()
            loadAd(activity)
            return
        }

        Log.d(TAG, "Will show ad.")

        appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            /** Called when full screen content is dismissed. */
            override fun onAdDismissedFullScreenContent() {
                // Set the reference to null so isAdAvailable() returns false.
                appOpenAd = null
                isShowingAd = false
                Log.d(TAG, "onAdDismissedFullScreenContent.")
                /* Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
                             .show()*/

                onShowAdCompleteListener.onShowAdComplete()
                loadAd(activity)
            }

            /** Called when fullscreen content failed to show. */
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.d(TAG, "onAdFailedToShowFullScreenContent: " + adError.message)
                /*Toast.makeText(
                            activity,
                            "onAdFailedToShowFullScreenContent",
                            Toast.LENGTH_SHORT
                        ).show()*/

                onShowAdCompleteListener.onShowAdComplete()
                loadAd(activity)
            }

            /** Called when fullscreen content is shown. */
            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "onAdShowedFullScreenContent.")
                /*Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
                            .show()*/
            }
        }
        isShowingAd = true
        appOpenAd!!.show(activity)
    }

    init {
        Log.e(TAG, "Ad Open ID : $adUnit")
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
    }
}