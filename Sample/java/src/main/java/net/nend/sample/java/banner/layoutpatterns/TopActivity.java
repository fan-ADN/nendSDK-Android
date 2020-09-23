package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import net.nend.sample.java.R;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
    }
}