package net.nend.sample.kotlin

import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.android.gms.security.ProviderInstaller
import net.nend.android.NendAdLogger
import net.nend.android.NendAdLogging

class NendSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()

        NendAdLogger.setLogLevel(NendAdLogger.LogLevel.INFO)
        NendAdLogger.sharedInstance().logger = NendAdLogging { message, logLevel ->
            when (logLevel) {
                NendAdLogger.LogLevel.DEBUG -> Log.d(TAG, message)
                NendAdLogger.LogLevel.INFO -> Log.i(TAG, message)
                NendAdLogger.LogLevel.WARN -> Log.w(TAG, message)
                NendAdLogger.LogLevel.ERROR -> Log.e(TAG, message)
                else -> {
                }
            }
        }

        ProviderInstaller.installIfNeededAsync(this, object : ProviderInstaller.ProviderInstallListener {
            override fun onProviderInstalled() {
                Log.d(TAG, "onProviderInstalled")
            }

            override fun onProviderInstallFailed(errorCode: Int, intent: Intent?) {
                Log.e(TAG, "onProviderInstallFailed, errorCode: $errorCode")
            }
        })
    }

    companion object {
        private const val TAG = "NendSampleApp"
    }
}