package recycler_users;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.User;
import project2.csc296.socialnetwork.R;


public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerHolder1> {

    private ArrayList<User> mUsers;
    private final String jl = "JEFF_RECYCLER_VIEW";
    private String mCurrUser;


    public RecyclerAdapter1(ArrayList<User> objects, String email) {
        mUsers = objects;
        Log.d(jl, "Recycler View Adapter Constructor Called");
        mCurrUser = email;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    @Override
    public void onBindViewHolder(RecyclerHolder1 rh, int i) {
        User u = mUsers.get(i);
        rh.bind(u);


    }


    @Override
    public RecyclerHolder1 onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_recycler, parent, false);

        Log.d(jl, mCurrUser);
        return new RecyclerHolder1(itemView, mCurrUser);
    }





}