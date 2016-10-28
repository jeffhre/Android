package user_recycler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import Database.DbDao;
import event_preference_recycler.EventPreferenceAdapter;
import model.User;
import project03.csc296.pollinateapp.R;

public class UserRecyclerFragment extends Fragment {


    private final String jl = "JEFF_RECYCLER_FRAG";
    private String mCurrUser;

    private ArrayList<String> mFavUsers;
    private ArrayList<User> mAllUsers;
    private RecyclerView mRecyclerView;
    DbDao DAO;
    UserAdapter UA;


    public UserRecyclerFragment() {

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
        View v = null;
        // Inflate the layout for this fragment
        if (savedInstanceState == null) {
            v = inflater.inflate(R.layout.fragment_user_recycler, container, false);
            Bundle b = this.getArguments();
            if (b != null) {
                mCurrUser = b.getString("KEY_EMAIL");
            }
            mFavUsers = DAO.getFavoriteUsers(mCurrUser);
            Log.d(jl, mCurrUser);
            mAllUsers = DAO.getAllUsers();
            mRecyclerView = (RecyclerView) v.findViewById(R.id.user_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            UA = new UserAdapter(mAllUsers, mCurrUser);
            mRecyclerView.setAdapter(UA);
            Log.d(jl, "RecyclerFragment onCreate called");
        }

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                final List<User> filteredUserList = filter(DAO.getAllUsers(), query);
                UA.animateTo(filteredUserList);
                mRecyclerView.scrollToPosition(0);
                return true;
            }
        });
    }



    private List<User> filter(List<User> users, String query) {
        query = query.toLowerCase();

        Log.d(jl, Integer.toString(users.size()));
        final List<User> filteredUserList = new ArrayList<>();
        for (User u : users) {
            final String text = u.getOrganization().toLowerCase();
            if (text.contains(query)) {
                filteredUserList.add(u);
            }
        }
        return filteredUserList;
    }

}
