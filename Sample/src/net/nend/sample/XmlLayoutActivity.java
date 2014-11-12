package net.nend.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class XmlLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xml_layout);
    }
}
