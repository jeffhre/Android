package recycler_users;

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
import model.User;
import project2.csc296.socialnetwork.R;


public class RecyclerFragmentFavoriteUsers extends Fragment {


    private final String jl = "JEFF_FAVORITE_FRAG";
    private String mCurrUser;

    private ArrayList<User> mUserList;
    private ArrayList<String> mFavorites;
    private RecyclerView mRecyclerView;
    DbDao DAO;


    public RecyclerFragmentFavoriteUsers() {

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
        Log.d(jl, mCurrUser);

        mFavorites = DAO.getFavorite(mCurrUser);
        mUserList = new ArrayList<User>();

        for (String s : mFavorites) {
            User u = DAO.getUser(s);
            if (u != null)
                mUserList.add(u);
        }


        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter1 RA = new RecyclerAdapter1(mUserList, mCurrUser);
        mRecyclerView.setAdapter(RA);

        Log.d(jl, "RecyclerFragment onCreate called");

        return v;
    }


}
