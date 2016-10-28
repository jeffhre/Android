package recycler_feed_items;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.FeedItem;
import project2.csc296.socialnetwork.R;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerHolder2> {

    private ArrayList<FeedItem> mFeedItems;
    private final String jl = "JEFF_RECYCLER_VIEW2";


    public RecyclerAdapter2(ArrayList<FeedItem> objects) {
        mFeedItems = objects;
        Log.d(jl, "Recycler View Adapter Constructor Called");
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }


    @Override
    public void onBindViewHolder(RecyclerHolder2 rh, int i) {
        FeedItem f = mFeedItems.get(i);
        rh.bind(f);


    }


    @Override
    public RecyclerHolder2 onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_recycler2, parent, false);

        return new RecyclerHolder2(itemView);
    }





}