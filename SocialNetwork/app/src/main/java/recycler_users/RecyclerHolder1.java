package recycler_users;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.DbDao;
import model.User;
import project2.csc296.socialnetwork.PhotoUtils;
import project2.csc296.socialnetwork.R;
import project2.csc296.socialnetwork.UserListActivity;
import project2.csc296.socialnetwork.ViewProfileActivity;

/**
 * Created by jhrebena on 11/14/15.
 */
public class RecyclerHolder1 extends RecyclerView.ViewHolder {

    public ImageView mPicture;
    public TextView mName;
    public TextView mHometown;
    public CheckBox mFavorite;
    private User mUser;
    DbDao DAO;
    View v;
    ArrayList<String> mFavorites;
    private static final String jl = "JEFF_VIEW_HOLDER1";

    private String mCurrUser = "not found";

    public RecyclerHolder1(View view, String email) {
        super(view);
        v = view;
        Log.d(jl, "View Holder Created");
        mPicture = (ImageView) view.findViewById(R.id.image_picture);
        mName =  (TextView) view.findViewById(R.id.text_name);
        mHometown = (TextView)  view.findViewById(R.id.text_hometown);
        mFavorite = (CheckBox) view.findViewById(R.id.checkbox_favorite);

        mCurrUser = email;
        Log.d(jl, mCurrUser);

        Context c = view.getContext();
        DAO = DAO.get(c);
        mFavorites = DAO.getFavorite(mCurrUser);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewProfileActivity.class);
                i.putExtra("KEY_EMAIL", mCurrUser);
                i.putExtra("CLICKED_EMAIL", mUser.getEmail());
                v.getContext().startActivity(i);
                Log.d(jl, "Holder Clicked!");
            }
        });
//        mFavorite.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ViewProfileActivity.class);
//                i.putExtra("CLICKED_EMAIL", mUser.getEmail());
//                v.getContext().startActivity(i);
//                Log.d(jl, "Holder Clicked!");
//            }
//        });
    }

    public void bind(User u) {
        Log.d(jl, "View Holder Bound");
        mUser = u;
        mName.setText(mUser.getFullName());
        mHometown.setText(mUser.getHometown());
        if (mUser.getPhoto() != null) {
            mPicture.setImageBitmap(new PhotoUtils().getBitmapFromByte(mUser.getPhoto()));
        }
        for (String s : mFavorites) {
            if (mUser.getEmail().equals(s)) {
                v.setBackgroundColor(Color.parseColor("#f5e3c0"));
            }
        }
//        bindPhoto();


    }
//    public interface OnItemClickListener {
//        public void onItemClick(View view , String email);
//    }
//
//    private OnItemClickListener mItemClickListener;
//
//    @Override
//    public void onClick(View v) {
//        mItemClickListener.onItemClick(v, mUser.getEmail());
//    }



    //    private void bindPhoto() {
//
//        File picturesDir = Context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File mPictureFile = new File(picturesDir, mUser.getPhoto());
//        Uri photoUri = Uri.fromFile(mPictureFile);
//        if (mPictureFile == null || !mPictureFile.exists()) {
//
//        }
//        else {
//            Bitmap photo = new PhotoUtils().getScaledBitmap(mPictureFile.getPath(),
//                    mPicture.getWidth(), mPicture.getHeight());
//            mPicture.setImageBitmap(photo);
//
//        }
//    }





}