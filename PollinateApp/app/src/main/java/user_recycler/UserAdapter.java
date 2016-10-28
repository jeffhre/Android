package user_recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import event_preference_recycler.EventPreferenceHolder;
import model.User;
import project03.csc296.pollinateapp.R;

/**
 * Created by jhrebena on 12/8/15.
 */
public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
    private ArrayList<User> mUsers;
    private ArrayList<User> mAllUsers;
    private final String jl = "JEFF_RECYCLER_VIEW";
    private String mCurrUser;


    public UserAdapter(ArrayList<User> objects, String email) {
        mUsers = objects;
        mAllUsers = objects;
        Log.d(jl, "Recycler View Adapter Constructor Called");
        mCurrUser = email;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    @Override
    public void onBindViewHolder(UserHolder uh, int i) {
        User u = mUsers.get(i);
        uh.bind(u);

    }


    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_user_recycler, parent, false);

        Log.d(jl, mCurrUser);
        return new UserHolder(itemView, mCurrUser);
    }


    public void animateTo(List<User> users) {
        applyRemovals(users);
        applyAdditions(users);
        applyMovedItems(users);
    }

    private void applyRemovals(List<User> newUsers) {
        for (int i = mUsers.size() - 1; i >= 0; i--) {
            final User u = mUsers.get(i);
            if (!newUsers.contains(u)) {
                removeItem(i);
            }
        }
    }

    private void applyAdditions(List<User> newUsers) {
        Log.d(jl, Integer.toString(newUsers.size()));
        for (int i = 0; i < newUsers.size(); i++) {
            final User u = newUsers.get(i);
            if (!mUsers.contains(u)) {
                addItem(i, u);
            }
        }
    }

    private void applyMovedItems(List<User> newUsers) {
        for (int toPosition = newUsers.size() - 1; toPosition >= 0; toPosition--) {
            final User u = newUsers.get(toPosition);
            final int fromPosition = mUsers.indexOf(u);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public void removeItem(int position) {
        mUsers.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, User u) {
        mUsers.add(position, u);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final User u = mUsers.remove(fromPosition);
        mUsers.add(toPosition, u);
        notifyItemMoved(fromPosition, toPosition);
    }

}





