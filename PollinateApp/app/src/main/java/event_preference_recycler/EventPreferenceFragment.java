package event_preference_recycler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Database.DbDao;
import model.EventTypes;
import model.User;
import project03.csc296.pollinateapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventPreferenceFragment extends Fragment {


    private final String jl = "JEFF_RECYCLER_FRAG";
    private String mCurrUser;

    private ArrayList<String> mAllTypes;
    private ArrayList<String> mPrefList;
    private RecyclerView mRecyclerView;
    DbDao DAO;


    public EventPreferenceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(false);
        DAO = DAO.get(getContext());
        mAllTypes = new EventTypes().getEventTypes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_preference, container, false);

        Bundle b = this.getArguments();
        if (b != null) {
            mCurrUser = b.getString("KEY_EMAIL");
        }

        Log.d(jl, mCurrUser);

        mPrefList = DAO.getFavoriteEvents(mCurrUser);


        mRecyclerView = (RecyclerView)v.findViewById(R.id.event_pref_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventPreferenceAdapter EPA = new EventPreferenceAdapter(mAllTypes, mCurrUser);
        mRecyclerView.setAdapter(EPA);

        Log.d(jl, "RecyclerFragment onCreate called");

        return v;
    }


}
