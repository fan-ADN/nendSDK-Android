package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class NendSampleActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Class<?> cls = null;
        switch (position) {
            case 0:
                cls = XmlSampleActivity.class;
                break;
            case 1:
                cls = JavaCallActivity.class;
                break;
            case 2:
                cls = LayoutSampleActivity.class;
                break;
            case 3:
                cls = DialogActivity.class;
                break;
            case 4:
                cls = SizeSampleActivity.class;
                break;
            case 5:
                cls = IconSampleActivity.class;
                break;
            case 6:
                cls = InterstitialActivity.class;
                break;
            case 7:
                cls = AdjustSizeActivity.class;
                break;
            case 8:
                cls = NativeSampleActivity.class;
                break;
            case 9:
                cls = FullBoardMenuActivity.class;
                break;
            case 10:
                cls = VideoActivity.class;
                break;
        }
        if (cls != null) {
            startActivity(new Intent(getApplicationContext(), cls));
        }
    }
}