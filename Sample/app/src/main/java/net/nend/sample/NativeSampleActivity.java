package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class NativeSampleActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Class<?> cls = null;

        switch (position) {
            case 0:
                cls = NativeLayoutActivity.class;
                break;
            case 1:
                cls = NativeLayoutActivity.class;
                break;
            case 2:
                cls = NativeLayoutActivity.class;
                break;
            case 3:
                cls = NativeLayoutActivity.class;
                break;
            case 4:
                cls = NativeListActivity.class;
                break;
            case 5:
                cls = NativeMultipleLayoutListActivity.class;
                break;
            case 6:
                cls = NativeGridActivity.class;
                break;
            case 7:
                cls = NativeRecyclerActivity.class;
                break;
            case 8:
                cls = NativeViewPagerActivity.class;
                break;
            case 9:
            case 10:
                cls = NativeCarouselAdActivity.class;
                break;
        }
        if(cls != null){
            Intent intent = new Intent(getApplicationContext(), cls);
            intent.putExtra("type", position);
            startActivity(intent);
        }
    }
}