package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.nend.sample.kotlin.R

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top)
    }
}