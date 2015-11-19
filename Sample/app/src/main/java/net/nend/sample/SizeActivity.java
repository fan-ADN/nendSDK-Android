package net.nend.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SizeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        switch (intent.getIntExtra("size_type", 0)) {
        case 0:
            setContentView(R.layout.ad_320x50);
            break;
        case 1:
            setContentView(R.layout.ad_320x100);
            break;
        case 2:
            setContentView(R.layout.ad_300x100);
            break;
        case 3:
            setContentView(R.layout.ad_300x250);
            break;
        case 4:
            setContentView(R.layout.ad_728x90);
            break;
        }
    }
}
