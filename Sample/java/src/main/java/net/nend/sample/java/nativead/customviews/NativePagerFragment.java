package net.nend.sample.java.nativead.customviews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.nend.sample.java.R;

public class NativePagerFragment extends Fragment {

    OnAdListener mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            mCallback = (OnAdListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.native_fragment, container, false);
        RelativeLayout adLayout = view.findViewById(R.id.ad);

        int position = 0;
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }

        mCallback.onAdRequest(adLayout, position);

        return view;
    }

    public interface OnAdListener {
        void onAdRequest(View view, int position);
    }
}
