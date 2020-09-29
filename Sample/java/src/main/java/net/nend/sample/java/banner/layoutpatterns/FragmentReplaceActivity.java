package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import net.nend.sample.java.R;

/**
 * Fragment切り替えサンプル
 */
public class FragmentReplaceActivity extends AppCompatActivity implements OnClickListener{
    
    int count = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout, new FirstFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        
        Fragment fragment;
        if (count % 2 == 0) {
            fragment = new SecondFragment();
        } else {
            fragment = new FirstFragment();
        }
        count++;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    public static class FirstFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            return View.inflate(inflater.getContext(), R.layout.replace_fragment1, null);
        }
    }

    public static class SecondFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {

            return View.inflate(inflater.getContext(), R.layout.replace_fragment2, null);
        }
    }
}
