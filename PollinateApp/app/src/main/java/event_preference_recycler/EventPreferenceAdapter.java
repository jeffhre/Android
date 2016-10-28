package event_preference_recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.User;
import project03.csc296.pollinateapp.EventPreferenceActivity;
import project03.csc296.pollinateapp.R;

/**
 * Created by jhrebena on 12/7/15.
 */
public class EventPreferenceAdapter extends RecyclerView.Adapter<EventPreferenceHolder> {
    private ArrayList<String> mTypes;
    private final String jl = "JEFF_RECYCLER_VIEW";
    private String mCurrUser;


    public EventPreferenceAdapter(ArrayList<String> objects, String email) {
        mTypes = objects;
        Log.d(jl, "Recycler View Adapter Constructor Called");
        mCurrUser = email;
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }


    @Override
    public void onBindViewHolder(EventPreferenceHolder eph, int i) {
        String s = mTypes.get(i);
        eph.bind(s);


    }


    @Override
    public EventPreferenceHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_event_preference_recycler, parent, false);

        Log.d(jl, mCurrUser);
        return new EventPreferenceHolder(itemView, mCurrUser);
    }





}