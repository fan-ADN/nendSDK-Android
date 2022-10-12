package net.nend.sample.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleListActivity extends AppCompatActivity {
    private ListFragment listFragment;

    protected void instantiateMenuListFragment(int resId, List<String> menus) {
        int id = new Random().nextInt(0xFFFF);
        FrameLayout container = new FrameLayout(this);
        container.setId(id);
        setContentView(container, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        SimpleMenuListFragment fragment = new SimpleMenuListFragment();
        Bundle extras = new Bundle();
        extras.putInt("resId", resId);
        extras.putStringArrayList("menus", new ArrayList<>(menus));
        fragment.setArguments(extras);

        getSupportFragmentManager()
                .beginTransaction()
                .add(id, fragment)
                .commit();
    }

    protected void instantiateListAdapter(ArrayAdapter adapter) {
        listFragment = new ListFragment();
        listFragment.setListAdapter(adapter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, listFragment, "list_fragment")
                .commit();
    }

    @Nullable
    protected ListView getListView() {
        return (listFragment != null ? listFragment.getListView() : null);
    }

    public static class SimpleMenuListFragment extends ListFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            return inflater.inflate(requireArguments().getInt("resId"), container, false);
        }

        @Override
        public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
            Class<?> activity;
            try {
                activity = Class.forName(requireArguments().getStringArrayList("menus").get(position));
                Intent intent = new Intent(getActivity(), activity);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}