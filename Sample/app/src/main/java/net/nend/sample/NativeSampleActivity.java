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
                cls = NativeListActivity.class;
                break;
            case 4:
                cls = NativeMultipleLayoutListActivity.class;
                break;
            case 5:
                cls = NativeGridActivity.class;
                break;
            case 6:
                cls = NativeRecyclerActivity.class;
                break;
            case 7:
                cls = NativeViewPagerActivity.class;
                break;
            case 8:
            case 9:
                cls = NativeCarouselAdActivity.class;
                break;
            case 10:
                cls = NativeMarqueeActivity.class;
                break;
            case 11:
                cls = NativeGetAdDataActivity.class;
                break;
            case 12:
                cls = NativeAutoReloadActivity.class;
                break;
            case 13:
                cls = NativeV2OnListActivity.class;
                break;
        }
        if (cls != null) {
            Intent intent = new Intent(getApplicationContext(), cls);
            intent.putExtra("type", position);
            startActivity(intent);
        }
    }
}