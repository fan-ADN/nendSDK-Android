package net.nend.sample.kotlin.video

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import net.nend.android.*
import net.nend.sample.kotlin.R



@Suppress("UNUSED_PARAMETER")
/*
　このサンプルは広告配信に位置情報をオプションで利用しています。
  This sample uses location data as an option for ad supply.
*/
class VideoActivity : AppCompatActivity() {

    private var nendAdRewardedVideo: NendAdRewardedVideo? = null
    private var nendAdInterstitialVideo: NendAdInterstitialVideo? = null
    private lateinit var progressDialog: ProgressDialogFragment

    private fun Context.toast(
            message: CharSequence, duration: Int) =
            Toast.makeText(this, message, duration).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        progressDialog = ProgressDialogFragment.newInstance()
        progressDialog.dialogMessage = "Now loading..."
    }

    override fun onStart() {
        super.onStart()
        if (!verifyPermissions()) {
            requestPermissions()
        }
    }

    override fun onPause() {
        super.onPause()
        if (progressDialog.isShowing()) {
            progressDialog.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseRewardVideoInstance()
        releaseInterstitialVideoInstance()
    }

    fun onClickLoadReward(view: View) {
        Log.d(TAG, "Click load reward button.")
        if (nendAdRewardedVideo == null) {
            nendAdRewardedVideo = NendAdRewardedVideo(this,
                    REWARDED_VIDEO_SPOT_ID, REWARDED_VIDEO_API_KEY).apply {
                setUserId(USER_ID)
                val feature = NendAdUserFeature.Builder()
                        .setGender(NendAdUserFeature.Gender.FEMALE)
                        .build()
                setUserFeature(feature)
                setActionListener(object : NendAdRewardedActionListener {
                    override fun onRewarded(
                            nendAdVideo: NendAdVideo, nendAdRewardItem: NendAdRewardItem) {
                        val name = nendAdRewardItem.currencyName
                        val amount = nendAdRewardItem.currencyAmount
                        Log.d(TAG, "onRewarded")
                        Log.d(TAG, "currencyName : $name")
                        Log.d(TAG, "currencyAmount : $amount")
                        toast("onRewarded, Name : $name Amount : $amount", Toast.LENGTH_LONG)

                        when (nendAdVideo.type) {
                            NendAdVideoType.PLAYABLE -> Log.d(TAG, "Playable ad is not provide playing state listener")
                            NendAdVideoType.NORMAL -> {
                                nendAdRewardedVideo?.playingState()?.let {
                                    it.playingStateListener = object : NendAdVideoPlayingStateListener {
                                        override fun onStarted(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onStarted reward")
                                            toast("onStarted reward", Toast.LENGTH_SHORT)
                                        }

                                        override fun onStopped(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onStopped reward")
                                            toast("onStopped reward", Toast.LENGTH_SHORT)
                                        }

                                        override fun onCompleted(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onCompleted reward")
                                            toast("onCompleted reward", Toast.LENGTH_SHORT)
                                        }
                                    }
                                }
                            }
                            else -> Log.d(TAG, nendAdVideo.type.toString())
                        }
                    }

                    override fun onLoaded(nendAdVideo: NendAdVideo) {
                        progressDialog.cancel()
                        Log.d(TAG, "onLoaded reward")
                        toast("onLoaded reward", Toast.LENGTH_SHORT)
                    }

                    override fun onFailedToLoad(nendAdVideo: NendAdVideo, errorCode: Int) {
                        progressDialog.cancel()
                        Log.w(TAG, "onFailedToLoad reward")
                        Log.w(TAG, "VideoAdError code : $errorCode")
                        toast("onFailedToLoad, code : $errorCode", Toast.LENGTH_LONG)
                    }

                    override fun onFailedToPlay(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onFailedToPlay reward")
                        toast("onFailedToPlay reward", Toast.LENGTH_SHORT)
                    }

                    override fun onShown(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onShown reward")
                        toast("onShown reward", Toast.LENGTH_SHORT)
                    }

                    override fun onClosed(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onClosed reward")
                        toast("onClosed reward", Toast.LENGTH_SHORT)
                    }

                    override fun onAdClicked(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onAdClicked reward")
                        toast("onAdClicked reward", Toast.LENGTH_SHORT)
                    }

                    override fun onInformationClicked(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onInformationClicked reward")
                        toast("onInformationClicked reward", Toast.LENGTH_SHORT)
                    }
                })
            }
        }
        nendAdRewardedVideo!!.loadAd()
        createProgressDialog("Rewarded")
    }

    fun onClickShowReward(view: View) {
        Log.d(TAG, "Click show reward button.")
        if (nendAdRewardedVideo != null && nendAdRewardedVideo!!.isLoaded) {
            nendAdRewardedVideo!!.showAd(this)
        } else {
            Log.d(TAG, "Loading is not completed.")
        }
    }

    fun onClickReleaseReward(view: View) {
        Log.d(TAG, "Click release reward button.")
        releaseRewardVideoInstance()
    }

    private fun releaseRewardVideoInstance() {
        nendAdRewardedVideo?.releaseAd()
        nendAdRewardedVideo = null
        Log.i(TAG, "Released.")
    }

    fun onClickLoadInterstitial(view: View) {
        Log.d(TAG, "Click load interstitial button.")
        if (nendAdInterstitialVideo == null) {
            nendAdInterstitialVideo = NendAdInterstitialVideo(this,
                    INTERSTITIAL_VIDEO_SPOT_ID, INTERSTITIAL_VIDEO_API_KEY).apply {
                setUserId(USER_ID)
                val feature = NendAdUserFeature.Builder()
                        .setGender(NendAdUserFeature.Gender.MALE) // 性別
                        .setBirthday(1985, 1, 1) // 生年月日 (e.g. 1985年1月1日)
                        .setAge(34) // 年齢
                        .addCustomFeature("stringParameter", "test") // key-value形式のカスタムパラメーター
                        .addCustomFeature("booleanParameter", true)
                        .addCustomFeature("integerParameter", 100)
                        .addCustomFeature("doubleParameter", 123.45)
                        .build()
                setUserFeature(feature)
                setLocationEnabled(false)
                addFallbackFullboard(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff")
                isMuteStartPlaying = false
                setActionListener(object : NendAdVideoActionListener {
                    override fun onLoaded(nendAdVideo: NendAdVideo) {
                        progressDialog.cancel()
                        Log.d(TAG, "onLoaded interstitial")
                        toast("onLoaded interstitial", Toast.LENGTH_SHORT)

                        when (nendAdVideo.type) {
                            NendAdVideoType.PLAYABLE -> Log.d(TAG, "Playable ad is not provide playing state listener")
                            NendAdVideoType.NORMAL -> {
                                nendAdInterstitialVideo?.playingState()?.let {
                                    it.playingStateListener = object : NendAdVideoPlayingStateListener {
                                        override fun onStarted(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onStarted interstitial")
                                            toast("onStarted interstitial", Toast.LENGTH_SHORT)
                                        }

                                        override fun onStopped(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onStopped interstitial")
                                            toast("onStopped interstitial", Toast.LENGTH_SHORT)
                                        }

                                        override fun onCompleted(nendAdVideo: NendAdVideo) {
                                            Log.d(TAG, "onCompleted interstitial")
                                            toast("onCompleted interstitial", Toast.LENGTH_SHORT)
                                        }
                                    }
                                }
                            }
                            else -> Log.d(TAG, nendAdVideo.type.toString())
                        }
                    }

                    override fun onFailedToLoad(nendAdVideo: NendAdVideo, errorCode: Int) {
                        progressDialog.cancel()
                        Log.w(TAG, "onFailedToLoad interstitial")
                        Log.w(TAG, "VideoAdError code : $errorCode")
                        toast("onFailedToLoad interstitial, code : $errorCode", Toast.LENGTH_LONG)
                    }

                    override fun onFailedToPlay(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onFailedToPlay interstitial")
                        toast("onFailedToPlay interstitial", Toast.LENGTH_SHORT)
                    }

                    override fun onShown(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onShown interstitial")
                        toast("onShown interstitial", Toast.LENGTH_SHORT)
                    }

                    override fun onClosed(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onClosed interstitial")
                        toast("onClosed interstitial", Toast.LENGTH_SHORT)
                    }

                    override fun onAdClicked(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onAdClicked interstitial")
                        toast("onAdClicked interstitial", Toast.LENGTH_SHORT)
                    }

                    override fun onInformationClicked(nendAdVideo: NendAdVideo) {
                        Log.d(TAG, "onInformationClicked interstitial")
                        toast("onInformationClicked interstitial", Toast.LENGTH_SHORT)
                    }
                })
            }
        }
        nendAdInterstitialVideo!!.loadAd()
        createProgressDialog("Interstitial")
    }

    fun onClickShowInterstitial(view: View) {
        Log.d(TAG, "Click show interstitial button.")
        if (nendAdInterstitialVideo != null && nendAdInterstitialVideo!!.isLoaded) {
            nendAdInterstitialVideo!!.showAd(this)
        } else {
            Log.d(TAG, "Loading is not completed.")
        }
    }

    fun onClickReleaseInterstitial(view: View) {
        Log.d(TAG, "Click release interstitial button.")
        releaseInterstitialVideoInstance()
    }

    private fun releaseInterstitialVideoInstance() {
        nendAdInterstitialVideo?.releaseAd()
        nendAdInterstitialVideo = null
        Log.i(TAG, "Released.")
    }

    private fun createProgressDialog(adType: String) {
        progressDialog.show(supportFragmentManager, "Tag")
    }

    private fun verifyPermissions(): Boolean {
        val state = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return state == PackageManager.PERMISSION_GRANTED
    }

    private fun showRequestPermissionDialog() = ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)

    private fun requestPermissions() {
        val shouldRequest = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldRequest) {
            Snackbar.make(findViewById(R.id.base_layout),
                    "Location permission is needed for get the last Location. It's a demo that uses location data.",
                    Snackbar.LENGTH_LONG).setAction(android.R.string.ok) {
                showRequestPermissionDialog()
            }.show()
        } else {
            showRequestPermissionDialog()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Snackbar.make(findViewById(R.id.base_layout),
                        "User interaction was cancelled.", Snackbar.LENGTH_LONG).show()
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    Snackbar.make(findViewById(R.id.base_layout),
                            "Permission granted.", Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(findViewById(R.id.base_layout),
                        "Permission denied.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val TAG = "VideoAd"
        private const val REWARDED_VIDEO_SPOT_ID = 802558
        private const val INTERSTITIAL_VIDEO_SPOT_ID = 802559
        private const val REWARDED_VIDEO_API_KEY = "a6eb8828d64c70630fd6737bd266756c5c7d48aa"
        private const val INTERSTITIAL_VIDEO_API_KEY = "e9527a2ac8d1f39a667dfe0f7c169513b090ad44"
        private const val USER_ID = "DUMMY_USER_ID"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    }
}