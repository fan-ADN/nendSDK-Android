package net.nend.sample;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class NativeCarouselLayoutManager extends LinearLayoutManager {
    private int mStartCoordinate = 0;
    private boolean mIsCentralView = false;

     public NativeCarouselLayoutManager(Context context, int horizontal, boolean b) {
         super(context, horizontal, b);
     }
    // 子ビューの左位置を指定
    public void setStartCoordinate(int startCoordinate) {
        mStartCoordinate = startCoordinate;
    }

    @Override
    public void smoothScrollToPosition(final RecyclerView recyclerView, RecyclerView.State state, final int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return NativeCarouselLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            // スクロール位置の指定
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {

                int dx  = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference());
                if(mIsCentralView) {
                    mIsCentralView = false;
                    if(dx != 0 && dx >= 1) {
                        dx = dx - mStartCoordinate;
                    } else if(dx != 0 && dx < 1) {
                        dx = dx + mStartCoordinate;
                    }
                } else {
                    if(dx != 0 && dx >= 1) {
                        dx = dx + mStartCoordinate;
                    } else if(dx != 0 && dx < 1) {
                        dx = dx - mStartCoordinate;
                    }
                }

                final int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference());
                final int distance = (int) Math.sqrt(dx * dx + dy * dy);
                final int time = calculateTimeForDeceleration(distance);
                if (time > 0) {
                    action.update(-dx, -dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                switch (snapPreference) {
                    case SNAP_TO_START:
                        return boxStart - viewStart;
                    case SNAP_TO_END:
                        return boxEnd - viewEnd;
                    case SNAP_TO_ANY:
                        final int dtStart = boxStart - viewStart;
                        if (dtStart > 0) {
                            return dtStart;
                        }
                        final int dtEnd = boxEnd - viewEnd;
                        if (dtEnd < 0) {
                            return dtEnd;
                        }

                        // 中央の広告の場合
                        if (Math.abs(dtEnd) != Math.abs(dtStart)) {
                            mIsCentralView = true;
                            if(Math.abs(dtStart) > Math.abs(dtEnd)) {
                                return dtEnd;
                            } else {
                                return dtStart;
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("snap preference should be one of the"
                                + " constants defined in SmoothScroller, starting with SNAP_");
                }
                return 0;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
