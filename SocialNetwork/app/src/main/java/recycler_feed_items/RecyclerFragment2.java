package recycler_feed_items;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import Database.DbDao;
import model.FeedItem;
import model.FeedItemComparator;
import project2.csc296.socialnetwork.R;


public class RecyclerFragment2 extends Fragment {


    private final String jl = "JEFF_RECYCLER_FRAG";

    private ArrayList<FeedItem> mFeedItems;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mFavorites;
    private String mCurrUser;
    DbDao DAO;


    public RecyclerFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DAO = DAO.get(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        Bundle b = this.getArguments();
        if (b != null) {
            mCurrUser = b.getString("KEY_EMAIL");
        }

        mFeedItems = DAO.getAllFeedItems();
        mFavorites = DAO.getFavorite(mCurrUser);

        for (int i = 0; i < mFeedItems.size(); i++) {
            boolean found = false;

            for (String s : mFavorites) {
                if (s.equals(mFeedItems.get(i).getEmail())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                mFeedItems.remove(i);
                i--;
            }
        }

        Collections.sort(mFeedItems, new FeedItemComparator());
        for (FeedItem f : mFeedItems) {
            Log.d(jl, f.getPostDate());
        }

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter2 RA = new RecyclerAdapter2(mFeedItems);
        mRecyclerView.setAdapter(RA);

        Log.d(jl, "RecyclerFragment onCreate called");

        return v;
    }


    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


}
