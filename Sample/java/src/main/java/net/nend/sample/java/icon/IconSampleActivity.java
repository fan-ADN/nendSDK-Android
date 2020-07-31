package net.nend.sample.java.icon;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class IconSampleActivity extends ListActivity {

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(IconActivity.class);
            add(IconResourceActivity.class);
            add(IconJavaCallActivity.class);
            add(IconDialogActivity.class);
            add(IconAttachAndDetachActivity.class);
            add(IconSpaceActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }
}