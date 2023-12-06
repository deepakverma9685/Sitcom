package com.deepak.sitcom

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class SitcomApp :MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

    }
}