package net.nend.sample.java.banner;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class JavaCallActivity extends ListActivity {

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(JavaCallLinearActivity.class);
            add(JavaCallRelativeActivity.class);
            add(JavaCallWithListenerActivity.class);
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }
}