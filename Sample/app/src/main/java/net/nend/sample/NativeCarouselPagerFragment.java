package net.nend.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NativeCarouselPagerFragment extends Fragment {

    OnAdListener mCallback;

    public interface OnAdListener {
        void onAdRequest(View view, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            mCallback = (OnAdListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.native_carousel_fragment, container, false);
        RelativeLayout adLayout = (RelativeLayout) view.findViewById(R.id.ad);

        int position = getArguments().getInt("position");
        mCallback.onAdRequest(adLayout, position);

        return view;
    }
}
