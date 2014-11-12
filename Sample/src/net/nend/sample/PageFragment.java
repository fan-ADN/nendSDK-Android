package net.nend.sample;

import java.util.ArrayList;

import net.nend.android.NendAdView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, 
            ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> list = getArguments().getStringArrayList("itemList");
        int position = getArguments().getInt("position");

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.page_list, null);
        TextView textView = (TextView) layout.findViewById(R.id.page_title);
        textView.setText("PAGE"+position);

        ListView listView = (ListView) layout.findViewById(R.id.page_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        NendAdView adView = new NendAdView(getActivity(), 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
        adView.loadAd();
        listView.addHeaderView(adView);
        listView.setAdapter(adapter);

        return layout;
    }
}
