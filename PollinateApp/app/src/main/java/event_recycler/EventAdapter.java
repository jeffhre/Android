package event_recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.Event;
import project03.csc296.pollinateapp.R;

/**
 * Created by jhrebena on 12/9/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventHolder> {

    private ArrayList<Event> mEvents;
    private final String jl = "JEFF_RECYCLER_VIEW";

    private String mCurrUser;


    public EventAdapter(ArrayList<Event> objects, String currUser) {
        mEvents = objects;
        Log.d(jl, "Recycler View Adapter Constructor Called");
        mCurrUser = currUser;
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }


    @Override
    public void onBindViewHolder(EventHolder eh, int i) {
        Event e = mEvents.get(i);
        eh.bind(e);


    }


    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_event_recycler, parent, false);

        return new EventHolder(itemView, mCurrUser);
    }





}