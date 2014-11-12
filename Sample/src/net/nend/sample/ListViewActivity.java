package net.nend.sample;

import java.util.ArrayList;

import net.nend.android.NendAdView;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ListViewに表示するサンプル
 */
public class ListViewActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ListView listView = getListView();
        NendAdView adView = new NendAdView(this, 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
        adView.loadAd();
        listView.addHeaderView(adView);
        
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            list.add("item"+i);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }
}