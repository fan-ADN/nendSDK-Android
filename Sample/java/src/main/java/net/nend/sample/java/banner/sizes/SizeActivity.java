package net.nend.sample.java.banner.sizes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static net.nend.sample.java.banner.sizes.SizeSampleActivity.SIZE_SAMPLE_ACTIVITIES;

public class SizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SIZE_SAMPLE_ACTIVITIES.get(getIntent().getIntExtra("size_type", 0)));
    }
}
