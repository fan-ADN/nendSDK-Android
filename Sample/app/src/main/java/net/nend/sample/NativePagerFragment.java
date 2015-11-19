package net.nend.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NativePagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.native_fragment, container, false);
        RelativeLayout adLayout = (RelativeLayout) view.findViewById(R.id.ad);

        int position = getArguments().getInt("position");

        NativeViewPagerActivity activity = (NativeViewPagerActivity) getActivity();
        activity.onAdRequest(adLayout, position);

        return view;
    }
}
