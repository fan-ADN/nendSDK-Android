package net.nend.sample.kotlin

import android.app.Application
import android.util.Log
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
    }

    companion object {
        private const val TAG = "NendSampleApp"
    }
}