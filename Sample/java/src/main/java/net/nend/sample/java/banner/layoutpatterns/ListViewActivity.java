package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.nend.android.NendAdView;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;

/**
 * ListViewに表示するサンプル
 */
public class ListViewActivity extends SimpleListActivity {
    private NendAdView nendAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        instantiateListAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView listView = getListView();
        if (listView != null && nendAdView == null) {
            nendAdView = new NendAdView(this, 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
            nendAdView.loadAd();
            listView.addHeaderView(nendAdView);
        }
    }
}