package net.nend.sample.java.nativead;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.nend.sample.java.R;

public class NativeCarouselPagerFragment extends Fragment {

    OnAdListener mCallback;

    static NativeCarouselPagerFragment newInstance(int position) {
        NativeCarouselPagerFragment fragment = new NativeCarouselPagerFragment();
        Bundle extras = new Bundle();
        extras.putInt("position", position);
        fragment.setArguments(extras);
        return fragment;
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
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.native_carousel_fragment, container, false);
        RelativeLayout adLayout = (RelativeLayout) view.findViewById(R.id.ad);

        int position = getArguments().getInt("position");
        mCallback.onAdRequest(adLayout, position);

        return view;
    }

    public interface OnAdListener {
        void onAdRequest(View view, int position);
    }
}
