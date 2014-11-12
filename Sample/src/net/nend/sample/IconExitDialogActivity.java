package net.nend.sample;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import net.nend.android.NendAdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class IconExitDialogActivity extends Activity implements SampleConstants {

    NendDialog mDialog;
    private View mIconView;
    private NendAdIconLoader mIconLoader;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        mDialog = new NendDialog(this);
        
        mIconView = View.inflate(getApplicationContext(), R.layout.icon_dialog_layout, null);
    	
        mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
        mIconLoader.addIconView((NendAdIconView)mIconView.findViewById(R.id.icon1));
        mIconLoader.addIconView((NendAdIconView)mIconView.findViewById(R.id.icon2));
        mIconLoader.addIconView((NendAdIconView)mIconView.findViewById(R.id.icon3));
        mIconLoader.addIconView((NendAdIconView)mIconView.findViewById(R.id.icon4));
        
        ViewGroup root = (ViewGroup)findViewById(R.id.root);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        final Button button = new Button(getApplicationContext());
        button.setText("Exit");
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showExitDialog();
			}
		});
        root.addView(button, params);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialog.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private void showExitDialog() {
    	ViewGroup viewGroup = (ViewGroup)mIconView.getParent();
    	if  ( null != viewGroup ) {
    		viewGroup.removeView(mIconView);
    	}

    	new AlertDialog.Builder(this)
    	.setView(mIconView)
    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				IconExitDialogActivity.this.finish();
			}
		})
		.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mIconLoader.pause();
				dialog.dismiss();
			}
		})
		.setCancelable(false)
		.create()
		.show();
    	
        mIconLoader.loadAd();
    }

    class NendDialog extends Dialog implements android.view.View.OnClickListener {

        NendAdView mNendAdView;
        NendAdIconLoader mIconLoader;
        Context mContext;

        public NendDialog(Context context) {
            super(context);
            mContext = context;
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.icon_exit_dialog);
            
            Button finishButton = (Button) findViewById(R.id.finish);
            Button cancelButton = (Button) findViewById(R.id.cancel);
            finishButton.setOnClickListener(this);
            cancelButton.setOnClickListener(this);
            
            NendAdIconView view1 = (NendAdIconView) findViewById(R.id.icon1);
            NendAdIconView view2 = (NendAdIconView) findViewById(R.id.icon2);
            NendAdIconView view3 = (NendAdIconView) findViewById(R.id.icon3);
            NendAdIconView view4 = (NendAdIconView) findViewById(R.id.icon4);
            
            mNendAdView = (NendAdView) findViewById(R.id.nend);
            mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
            mIconLoader.addIconView(view1);
            mIconLoader.addIconView(view2);
            mIconLoader.addIconView(view3);
            mIconLoader.addIconView(view4);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            case R.id.finish:
                destroy();
                finish();
                break;
            case R.id.cancel:
                destroy();
                break;
            }
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            mNendAdView.loadAd();
            mIconLoader.loadAd();
        }

        @Override
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mNendAdView.pause();
            mIconLoader.pause();
        }

        public void destroy(){
            dismiss();
        }
    }
}
