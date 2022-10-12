package net.nend.sample.java.banner.adjust;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class AdjustSizeActivity extends SimpleListActivity {

	private static final List<String> SAMPLE_ACTIVITIES = new ArrayList<String>() {
		{
			add(AdjustSizeXmlLayoutActivity.class.getName());
			add(AdjustSizeJavaCallActivity.class.getName());
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instantiateMenuListFragment(R.layout.adjust_size_sample, SAMPLE_ACTIVITIES);
	}
}