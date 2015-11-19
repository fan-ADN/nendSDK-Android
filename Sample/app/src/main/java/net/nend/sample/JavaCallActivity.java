package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class JavaCallActivity extends ListActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Class<?> cls = null;
        switch (position) {
        case 0:
            cls = JavaCallLinearActivity.class;
            break;
        case 1:
            cls = JavaCallRelativeActivity.class;
            break;
        case 2:
            cls = JavaCallWithListenerActivity.class;
            break;
        }
        if(cls != null){
            startActivity(new Intent(getApplicationContext(), cls));
        }
    }
}