package event_recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import model.Event;
import model.EventComparator;
import project03.csc296.pollinateapp.R;

public class EventRecyclerFragment extends Fragment {


    private final String jl = "JEFF_RECYCLER_FRAG";

    private ArrayList<Event> mEvents;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mFavoriteTypes;
    private ArrayList<String> mFavoriteUsers;
    private String mCurrUser;
    DbDao DAO;


    public EventRecyclerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        setHasOptionsMenu(true);
        DAO = DAO.get(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_recycler, container, false);

        Bundle b = this.getArguments();
        if (b != null) {
            mCurrUser = b.getString("KEY_EMAIL");
        }

        mEvents = DAO.getAllEvents();
        mFavoriteTypes = DAO.getFavoriteEvents(mCurrUser);
        mFavoriteUsers = DAO.getFavoriteUsers(mCurrUser);

        for (int i = 0; i < mEvents.size(); i++) {
            boolean found = false;

            for (String s : mFavoriteTypes) {
                if (s.equals(mEvents.get(i).getType())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (String s : mFavoriteUsers) {
                    if (s.equals(mEvents.get(i).getEmail())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                mEvents.remove(i);
                i--;
            }
        }

        Collections.sort(mEvents, new EventComparator());


        mRecyclerView = (RecyclerView)v.findViewById(R.id.event_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventAdapter ea = new EventAdapter(mEvents, mCurrUser);
        mRecyclerView.setAdapter(ea);

        Log.d(jl, "RecyclerFragment onCreate called");

        return v;
    }


    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


}
