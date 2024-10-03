package com.functional.mom.applicationmom

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class ApplicationMom : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this@ApplicationMom) {}

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            val testDeviceIds = listOf("4918BF419EF72094F4AC577E38FA4E5E")
            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)

            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@ApplicationMom) {}
        }
    }
}
