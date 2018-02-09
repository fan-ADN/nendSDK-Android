package net.nend.sample.java.fullboard;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ObservableWebView extends WebView {

    private Callback mCallback;

    public ObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        int height = (int) Math.floor(getContentHeight() * getResources().getDisplayMetrics().density);
        int webViewHeight = getHeight();
        if (t > oldt && getScrollY() + webViewHeight >= height - 5) {
            mCallback.onScrollEnd();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    interface Callback {
        void onScrollEnd();
    }
}
