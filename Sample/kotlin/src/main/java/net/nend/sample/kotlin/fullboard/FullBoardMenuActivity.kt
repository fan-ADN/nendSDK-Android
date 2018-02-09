package net.nend.sample.kotlin.fullboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import net.nend.sample.kotlin.R

class FullBoardMenuActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private enum class SampleType(val id: Int) {
        DEFAULT_SAMPLE(0),
        VIEWPAGER_SAMPLE(1),
        WEBVIEW_SAMPLE(2),
        TAB_LAYOUT_SAMPLE(3);

        companion object {
            fun getType(id: Int) = SampleType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                DEFAULT_SAMPLE -> activity.startActivity(Intent(activity,
                        FullBoardDefaultActivity::class.java))
                VIEWPAGER_SAMPLE -> activity.startActivity(Intent(activity,
                        FullBoardPagerActivity::class.java))
                WEBVIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        FullBoardWebViewActivity::class.java))
                TAB_LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        FullBoardTabLayoutActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_board_menu)

        val items = TITLES.mapIndexed { i, string -> mapOf("title" to string, "detail" to DETAILS[i]) }
        val adapter = SimpleAdapter(
                this, items, android.R.layout.simple_list_item_2,
                arrayOf("title", "detail"),
                intArrayOf(android.R.id.text1, android.R.id.text2))
        (findViewById(R.id.list) as ListView).also {
            it.adapter = adapter
            it.onItemClickListener = this
        }
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        SampleType.getType(i).startActivity(this)
    }

    companion object {
        const val FULLBOARD_SPOT_ID = 485520
        const val FULLBOARD_API_KEY = "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff"
        const val FULLBOARD_LOG_TAG = "FullBoardAd"
        private val TITLES = arrayOf("Interstitial style", "Swipe style", "Scroll end style",
                "Tab style")
        private val DETAILS = arrayOf("ポップアップで表示された広告は右上の×ボタンにより閉じることができます。", "マンガや小説などスワイプでページ送りをするアプリにてページとページの間に広告を差し込むことができます。※×ボタンは非表示にできます。", "ニュースや記事まとめ、縦スクロール式のマンガアプリなどで最下部までスクロールした後に画面下部から広告を呼び出します。右上の×ボタンにて閉じることができます。", "ニュースや記事まとめアプリでカテゴリタブの中に\"PR\"タブを作成し、PRタブがタップされた際に広告を表示します。")
    }
}