package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class IconSampleActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Class<?> cls = null;
        switch (position) {
        case 0:
            cls = IconLayoutActivity.class;
            break;
        case 1:
            cls = IconActivity.class;
            break;
        case 2:
            cls = IconJavaCallActivity.class;
            break;
        case 3:
            cls = IconDialogSampleActivity.class;
            break;
        case 4:
        	cls = IconAttachAndDettachActivity.class;
        	break;
        case 5:
        	cls = IconSpaceActivity.class;
        	break;
        }
        if(cls != null){
            startActivity(new Intent(getApplicationContext(), cls));
        }
    }
}