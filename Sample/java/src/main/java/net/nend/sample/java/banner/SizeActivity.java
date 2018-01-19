package net.nend.sample.java.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class SizeActivity extends AppCompatActivity {

    private static final List<Integer> SAMPLE_ACTIVITIES = new ArrayList<Integer>() {
        {
            add(R.layout.ad_320x50);
            add(R.layout.ad_320x100);
            add(R.layout.ad_300x100);
            add(R.layout.ad_300x250);
            add(R.layout.ad_728x90);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SAMPLE_ACTIVITIES.get(getIntent().getIntExtra("size_type", 0)));
    }
}
