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


public class RecyclerFragment1 extends Fragment {


    private final String jl = "JEFF_RECYCLER_FRAG";
    private String mCurrUser;

    private ArrayList<User> mUserList;
    private RecyclerView mRecyclerView;
    DbDao DAO;


    public RecyclerFragment1() {

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

        mUserList = DAO.getAllUsers();

        Bundle b = this.getArguments();
        if (b != null) {
            mCurrUser = b.getString("KEY_EMAIL");
        }
        Log.d(jl, mCurrUser);


        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter1 RA = new RecyclerAdapter1(mUserList, mCurrUser);
        mRecyclerView.setAdapter(RA);

        Log.d(jl, "RecyclerFragment onCreate called");

        return v;
    }


}
