package user_recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.DbDao;
import model.User;
import project03.csc296.pollinateapp.PhotoUtils;
import project03.csc296.pollinateapp.R;
import project03.csc296.pollinateapp.ViewUserActivity;

/**
 * Created by jhrebena on 12/8/15.
 */
public class UserHolder extends RecyclerView.ViewHolder {


    public ImageView mGroupPic;
    public TextView mGroupName;
    public Button mProfileButton;
    private User mUser;
    DbDao DAO;
    View v;
    ArrayList<String> mFavoriteUsers;
    private static final String jl = "JEFF_USER_HOLDER1";

    private String mCurrUser = "not found";

    public UserHolder(View view, String email) {
        super(view);
        v = view;
        Log.d(jl, "View Holder Created");
        mGroupPic =  (ImageView) view.findViewById(R.id.image_user_recycler);
        mGroupName =  (TextView) view.findViewById(R.id.text_user_recycler);
        mProfileButton = (Button) view.findViewById(R.id.button_user_recycler);

        mCurrUser = email;
        Log.d(jl, mCurrUser);

        Context c = view.getContext();
        DAO = DAO.get(c);
        mFavoriteUsers = DAO.getFavoriteUsers(mCurrUser);

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
//        mFavoriteBox.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                if (mFavoriteBox.isChecked()) {
//                    mFavoriteUsers = DAO.getFavoriteEvents(mCurrUser);
//                    mFavoriteUsers.add(mUser.getEmail());
//                    v.setBackgroundColor(Color.parseColor("#f5e3c0"));
//                }
//                else if (!mFavoriteBox.isChecked()) {
//                    mFavoriteUsers = DAO.getFavoriteEvents(mCurrUser);
//                    mFavoriteUsers.remove(mUser.getEmail());
//                    v.setBackgroundColor(0x00000000);
//                }
//                DAO.updateFavoriteEvents(mCurrUser, mFavoriteUsers);
//                Log.d(jl, "Holder Clicked!");
//            }
//        });

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewUserActivity.class);
                i.putExtra("KEY_EMAIL", mCurrUser);
                i.putExtra("CLICKED_EMAIL", mUser.getEmail());
                v.getContext().startActivity(i);
                Log.d(jl, "Holder Clicked!");
            }
        });
    }

    public void bind(User u) {
        Log.d(jl, "View Holder Bound");
        mUser = u;

        if (!mUser.getOrganization().equals("") && mUser.getOrganization() != null) {
            mGroupName.setText(mUser.getOrganization());
        }


        if (mUser.getPhoto() != null) {
            mGroupPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(mUser.getPhoto()));
        }

        mFavoriteUsers = DAO.getFavoriteUsers(mCurrUser);

        v.setBackgroundColor(0x00000000);

        for (String s : mFavoriteUsers) {
            if (u.getEmail().equals(s)) {
                v.setBackgroundColor(Color.parseColor("#f5e3c0"));
            }
        }
//        bindPhoto();




    }

}

