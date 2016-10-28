package event_preference_recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.DbDao;
import model.User;
import project03.csc296.pollinateapp.PhotoUtils;
import project03.csc296.pollinateapp.R;

/**
 * Created by jhrebena on 12/7/15.
 */
public class EventPreferenceHolder extends RecyclerView.ViewHolder {

    public TextView mEventType;
    public CheckBox mFavoriteBox;
    private String mType;
    DbDao DAO;
    View v;
    ArrayList<String> mFavoriteEvents;
    private static final String jl = "JEFF_VIEW_HOLDER1";

    private String mCurrUser = "not found";

    public EventPreferenceHolder(View view, String email) {
        super(view);
        v = view;
        Log.d(jl, "View Holder Created");
        mEventType =  (TextView) view.findViewById(R.id.text_event_pref);
        mFavoriteBox = (CheckBox) view.findViewById(R.id.checkbox_event_pref);

        mCurrUser = email;
        Log.d(jl, mCurrUser);

        Context c = view.getContext();
        DAO = DAO.get(c);
        mFavoriteEvents = DAO.getFavoriteEvents(mCurrUser);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ViewProfileActivity.class);
//                i.putExtra("KEY_EMAIL", mCurrUser);
//                i.putExtra("CLICKED_EMAIL", mUser.getEmail());
//                v.getContext().startActivity(i);
//                Log.d(jl, "Holder Clicked!");
//            }
//        });
        mFavoriteBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (mFavoriteBox.isChecked()) {
                    mFavoriteEvents = DAO.getFavoriteEvents(mCurrUser);
                    mFavoriteEvents.add(mType);
                    v.setBackgroundColor(Color.parseColor("#f5e3c0"));
                }
                else if (!mFavoriteBox.isChecked()) {
                    mFavoriteEvents = DAO.getFavoriteEvents(mCurrUser);
                    mFavoriteEvents.remove(mType);
                    v.setBackgroundColor(0x00000000);
                }
                DAO.updateFavoriteEvents(mCurrUser, mFavoriteEvents);
                Log.d(jl, "Holder Clicked!");
            }
        });
    }

    public void bind(String s) {
        Log.d(jl, "View Holder Bound");
        mType = s;
        mEventType.setText(mType);
        v.setBackgroundColor(0x00000000);
        mFavoriteBox.setChecked(false);

        for (String str : mFavoriteEvents) {
            if (mType.equals(str)) {
                v.setBackgroundColor(Color.parseColor("#f5e3c0"));
                mFavoriteBox.setChecked(true);
            }
        }
//        bindPhoto();


    }

}
