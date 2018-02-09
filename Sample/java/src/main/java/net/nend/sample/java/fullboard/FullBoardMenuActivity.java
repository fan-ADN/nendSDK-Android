package net.nend.sample.java.fullboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullBoardMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String[] TITLES = new String[]{
            "Interstitial style",
            "Swipe style",
            "Scroll end style",
            "Tab style"
    };

    private static final String[] DETAILS = new String[]{
            "ポップアップで表示された広告は右上の×ボタンにより閉じることができます。",
            "マンガや小説などスワイプでページ送りをするアプリにてページとページの間に広告を差し込むことができます。※×ボタンは非表示にできます。",
            "ニュースや記事まとめ、縦スクロール式のマンガアプリなどで最下部までスクロールした後に画面下部から広告を呼び出します。右上の×ボタンにて閉じることができます。",
            "ニュースや記事まとめアプリでカテゴリタブの中に\"PR\"タブを作成し、PRタブがタップされた際に広告を表示します。"
    };

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(FullBoardDefaultActivity.class);
            add(FullBoardPagerActivity.class);
            add(FullBoardWebViewActivity.class);
            add(FullBoardTabLayoutActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_board_menu);

        List<Map<String, String>> items = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", TITLES[i]);
            map.put("detail", DETAILS[i]);
            items.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_2,
                new String[]{"title", "detail"}, new int[]{android.R.id.text1, android.R.id.text2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(i)));
    }
}
