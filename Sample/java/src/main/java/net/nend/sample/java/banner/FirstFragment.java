package net.nend.sample.java.banner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nend.sample.java.R;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, 
            ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.replace_fragment1, null);
    }
}
