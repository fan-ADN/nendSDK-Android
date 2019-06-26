package net.nend.sample.java.fullboard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.nend.android.NendAdFullBoard;
import net.nend.android.NendAdFullBoardLoader;
import net.nend.sample.java.R;

public class FullBoardDefaultActivity extends AppCompatActivity {

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
                Toast.makeText(FullBoardDefaultActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
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
}
