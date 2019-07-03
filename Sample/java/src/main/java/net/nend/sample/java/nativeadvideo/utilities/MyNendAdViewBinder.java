package net.nend.sample.java.nativeadvideo.utilities;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.nend.android.NendAdNativeMediaStateListener;
import net.nend.android.NendAdNativeVideo;
import net.nend.sample.java.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MyNendAdViewBinder {

    private int mediaViewId;
    private int logoImageId;
    private int titleId;
    private int contentId;
    private int advertiserId;
    private int ctaId;

    private int ratingId;
    private int ratingCountId;

    MyNendAdViewBinder(Builder builder) {
        mediaViewId = builder.mediaViewId;
        titleId = builder.titleId;
        contentId = builder.contentId;
        ctaId = builder.actionId;
        logoImageId = builder.logoImageId;
        advertiserId = builder.advertiserId;
        ratingId = builder.ratingId;
        ratingCountId = builder.ratingCountId;
    }

    public int getMediaViewId() {
        return mediaViewId;
    }

    public int getLogoImageId() {
        return logoImageId;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getContentId() {
        return contentId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public int getRatingCountId() {
        return ratingCountId;
    }

    public int getActionId() {
        return ctaId;
    }

    public void renderView(MyNendAdViewHolder viewHolder, NendAdNativeVideo ad, NendAdNativeMediaStateListener listener) {
        if (ad == null) {
            return;
        }
        renderTextView(viewHolder.titleTextView, ad.getTitleText());
        renderTextView(viewHolder.contentTextView, ad.getDescriptionText());
        renderTextView(viewHolder.actionTextView, ad.getCallToActionText());
        renderTextView(viewHolder.advertiserNameTextView, ad.getAdvertiserName());
        renderImageView(viewHolder.logoImageView, ad.getLogoImageBitmap());
        renderRatingInfo(viewHolder.ratingStarsLinearLayout, viewHolder.ratingCountTextView, ad);

        ArrayList<View> list = new ArrayList<>();
        if (viewHolder.actionTextView != null) list.add(viewHolder.actionTextView);
        if (viewHolder.titleTextView != null) list.add(viewHolder.titleTextView);
        if (viewHolder.contentTextView != null) list.add(viewHolder.contentTextView);
        if (viewHolder.logoImageView != null) list.add(viewHolder.logoImageView);
        ad.registerInteractionViews(list);

        viewHolder.adNativeMediaView.setVisibility(View.VISIBLE);
        if (listener != null) {
            viewHolder.adNativeMediaView.setMediaStateListener(listener);
        }

        viewHolder.adNativeMediaView.setMedia(ad);
    }

    private void renderTextView(final TextView textView, final String text) {
        if (textView != null && textView.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    private void renderImageView(final ImageView imageView, final Bitmap bitmap) {
        if (imageView != null && imageView.getVisibility() == View.VISIBLE && bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void renderRatingInfo(final LinearLayout parent, final TextView textView, NendAdNativeVideo ad) {
        if (parent == null || textView == null) {
            return;
        }
        if (ad.getUserRating() < 0) {
            parent.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            return;
        }
        int[] ratingStarIds = {
                R.id.native_video_ad_star_001,
                R.id.native_video_ad_star_002,
                R.id.native_video_ad_star_003,
                R.id.native_video_ad_star_004,
                R.id.native_video_ad_star_005,
        };
        float ratingIndex = 0.0f;
        for (int viewid : ratingStarIds) {
            int drawableId = getStarDrawableId(ratingIndex, ad.getUserRating());
            ImageView star = parent.findViewById(viewid);
            star.setImageResource(drawableId);
            ratingIndex += 1.0f;
        }

        textView.setText(generateRatingCountText(ad.getUserRatingCount()));
    }

    private int getStarDrawableId(float ratingIndex, float userRating) {
        if (ratingIndex >= userRating) {
            return R.drawable.baseline_star_border_black_24;
        } else if (userRating - ratingIndex <= 0.5f) {
            return R.drawable.baseline_star_half_black_24;
        } else {
            return R.drawable.baseline_star_black_24;
        }
    }

    private String generateRatingCountText(long userRatingCount) {
        return "(" + NumberFormat.getNumberInstance().format(userRatingCount) + ")";
    }

    public static class Builder {

        private int mediaViewId;
        private int logoImageId;
        private int titleId;
        private int contentId;
        private int advertiserId;
        private int actionId;
        private int ratingId;
        private int ratingCountId;

        public Builder() {
        }

        public Builder mediaViewId(int mediaViewId) {
            this.mediaViewId = mediaViewId;
            return this;
        }

        public Builder logoImageId(int logoImageId) {
            this.logoImageId = logoImageId;
            return this;
        }

        public Builder advertiserId(int advertiserId) {
            this.advertiserId = advertiserId;
            return this;
        }

        public Builder titleId(int titleId) {
            this.titleId = titleId;
            return this;
        }

        public Builder contentId(int contentId) {
            this.contentId = contentId;
            return this;
        }

        public Builder ratingId(int ratingId) {
            this.ratingId = ratingId;
            return this;
        }

        public Builder ratingCountId(int ratingCountId) {
            this.ratingCountId = ratingCountId;
            return this;
        }

        public Builder actionId(int actionId) {
            this.actionId = actionId;
            return this;
        }

        public MyNendAdViewBinder build() {
            return new MyNendAdViewBinder(this);
        }
    }

}
