package net.nend.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.nend.android.NendAdFullBoard;
import net.nend.android.NendAdFullBoardLoader;

public class FullBoardActivity extends AppCompatActivity {

    private NendAdFullBoardLoader mLoader;
    private NendAdFullBoard mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_board);
        mLoader = new NendAdFullBoardLoader(getApplicationContext(), 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
    }

    public void onClickLoad(View view) {
        mLoader.loadAd(new NendAdFullBoardLoader.Callback() {
            @Override
            public void onSuccess(NendAdFullBoard nendAdFullBoard) {
                mAd = nendAdFullBoard;
            }

            @Override
            public void onFailure(NendAdFullBoardLoader.FullBoardAdError fullBoardAdError) {
                Log.w("FullBoard", fullBoardAdError.name());
            }
        });
    }

    public void onClickShow(View view) {
        if (mAd != null) {
            mAd.show(this);
        }
    }

    public void onClickPager(View view) {
        startActivity(new Intent(this, FullBoardPagerActivity.class));
    }
}
