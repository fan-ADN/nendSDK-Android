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

public class NativeCarouselPagerFragment extends Fragment {

    OnAdListener mCallback;

    public static NativeCarouselPagerFragment newInstance(int position, int layoutId) {
        NativeCarouselPagerFragment fragment = new NativeCarouselPagerFragment();
        Bundle extras = new Bundle();
        extras.putInt("position", position);
        extras.putInt("layoutId", layoutId);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arg = requireArguments();

        View view = inflater.inflate(arg.getInt("layoutId"), container, false);
        RelativeLayout adLayout = view.findViewById(R.id.ad);

        mCallback.onAdRequest(adLayout, arg.getInt("position"));

        return view;
    }

    public interface OnAdListener {
        void onAdRequest(View view, int position);
    }
}
