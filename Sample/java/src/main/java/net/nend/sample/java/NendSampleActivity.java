package net.nend.sample.java;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.banner.BannerSampleActivity;
import net.nend.sample.java.fullboard.FullBoardMenuActivity;
import net.nend.sample.java.icon.IconSampleActivity;
import net.nend.sample.java.interstitial.InterstitialActivity;
import net.nend.sample.java.nativead.NativeSampleActivity;
import net.nend.sample.java.nativeadvideo.ExamplesActivity;
import net.nend.sample.java.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class NendSampleActivity extends ListActivity {


    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(BannerSampleActivity.class);
            add(IconSampleActivity.class);
            add(InterstitialActivity.class);
            add(NativeSampleActivity.class);
            add(FullBoardMenuActivity.class);
            add(VideoActivity.class);
            add(ExamplesActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }
}