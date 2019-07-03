package net.nend.sample.java;

import android.app.Application;
import androidx.annotation.NonNull;
import android.util.Log;

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
    }
}
