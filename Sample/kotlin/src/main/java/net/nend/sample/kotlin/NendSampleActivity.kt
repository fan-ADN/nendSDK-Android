package net.nend.sample.kotlin

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.banner.BannerSampleActivity
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity
import net.nend.sample.kotlin.icon.IconSampleActivity
import net.nend.sample.kotlin.interstitial.InterstitialActivity
import net.nend.sample.kotlin.nativead.NativeSampleActivity
import net.nend.sample.kotlin.nativeadvideo.ExamplesActivity
import net.nend.sample.kotlin.video.VideoActivity

class NendSampleActivity : ListActivity() {

    private enum class AdType(val id: Int) {
        BANNER(0),
        ICON(1),
        INTERSTITIAL(2),
        NATIVE(3),
        FULLBOARD(4),
        VIDEO(5),
        NATIVE_VIDEO(6);

        companion object {
            fun getAdType(id: Int) = values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                BANNER -> activity.startActivity(Intent(activity,
                        BannerSampleActivity::class.java))
                ICON -> activity.startActivity(Intent(activity,
                        IconSampleActivity::class.java))
                INTERSTITIAL -> activity.startActivity(Intent(activity,
                        InterstitialActivity::class.java))
                NATIVE -> activity.startActivity(Intent(activity,
                        NativeSampleActivity::class.java))
                FULLBOARD -> activity.startActivity(Intent(activity,
                        FullBoardMenuActivity::class.java))
                VIDEO -> activity.startActivity(Intent(activity,
                        VideoActivity::class.java))
                NATIVE_VIDEO -> activity.startActivity(Intent(activity,
                        ExamplesActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nend_sample)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        AdType.getAdType(position).startActivity(this)
    }
}