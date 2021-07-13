package net.nend.sample.java;

import android.app.Application;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.security.ProviderInstaller;

import net.nend.android.NendAdLogger;
import net.nend.android.NendAdLogging;

public class NendSampleApp extends Application {

    private static final String TAG = "NendSampleApp";

    @Override
    public void onCreate() {
        super.onCreate();

        NendAdLogger.setLogLevel(NendAdLogger.LogLevel.INFO);
        NendAdLogger.sharedInstance().logger = new NendAdLogging() {
            @Override
            public void logMessage(@NonNull String message, @NonNull NendAdLogger.LogLevel logLevel) {
                switch (logLevel) {
                    case DEBUG:
                        Log.d(TAG, message);
                        break;
                    case INFO:
                        Log.i(TAG, message);
                        break;
                    case WARN:
                        Log.w(TAG, message);
                        break;
                    case ERROR:
                        Log.e(TAG, message);
                        break;
                    default:
                        break;
                }
            }
        };

        // Android 4.x系を対象とするアプリケーションの場合、セキュリティプロバイダを検証、更新する必要があります
        // You need to update security provider if your app target Android 4.x
        // See also: https://developer.android.com/training/articles/security-gms-provider#patching
        ProviderInstaller.installIfNeededAsync(this, new ProviderInstaller.ProviderInstallListener() {
            @Override
            public void onProviderInstalled() {
                Log.d(TAG, "onProviderInstalled");
            }

            @Override
            public void onProviderInstallFailed(int errorCode, Intent intent) {
                Log.e(TAG, "onProviderInstallFailed, errorCode: " + errorCode);
            }
        });
    }


}
