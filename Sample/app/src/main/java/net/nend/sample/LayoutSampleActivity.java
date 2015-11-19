package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class LayoutSampleActivity extends ListActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Class<?> cls = null;
        switch (position) {
        case 0:
            cls = TopActivity.class;
            break;
        case 1:
            cls = BottomActivity.class;
            break;
        case 2:
            cls = AttachAndDettachActivity.class;
            break;
        case 3:
            cls = ListViewActivity.class;
            break;
        case 4:
            cls = ViewPagerActivity.class;
            break;
        case 5:
            cls = PagerAndListActivity.class;
            break;
        case 6:
            cls = FragmentReplaceActivity.class;
            break;
        }
        if(cls != null){
            startActivity(new Intent(getApplicationContext(), cls));
        }
    }
}