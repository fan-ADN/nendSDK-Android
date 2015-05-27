package net.nend.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AdjustSizeActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adjust_size_sample);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Class<?> cls = null;
		switch (position) {
		case 0:
			cls = AdjustSizeXmlLayoutActivity.class;
			break;
		case 1:
			cls = AdjustSizeJavaCallActivity.class;
			break;
		}
		if (cls != null) {
			startActivity(new Intent(getApplicationContext(), cls));
		}
	}
}