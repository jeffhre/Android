package project2.csc296.socialnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Database.DbDao;
import model.User;

public class ViewProfileActivity extends AppCompatActivity {

    public static final String jl = "JEFF_VIEW_PROFILE";

    TextView mFullName;
    ImageView mProPic;
    TextView mEmail;
    TextView mHometown;
    TextView mBirthday;
    TextView mBio;
    CheckBox mFavoriteBox;
    Button mCancel;

    ArrayList<String> mFavorites;

    User u;
    private String mCurrUser;


    DbDao DAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        DAO = DAO.get(getApplicationContext());

        mFullName = (TextView) findViewById(R.id.text_user_full_name);
        mProPic = (ImageView) findViewById(R.id.image_user_propic);
        mEmail = (TextView) findViewById(R.id.edit_user_email);
        mHometown = (TextView) findViewById(R.id.text_user_hometown);
        mBirthday = (TextView) findViewById(R.id.edit_user_bday);
        mBio = (TextView) findViewById(R.id.text_user_bio);
        mFavoriteBox = (CheckBox) findViewById(R.id.checkbox_favorite);
        mCancel = (Button) findViewById(R.id.button_back);


        u = DAO.getUser(getIntent().getStringExtra("CLICKED_EMAIL"));
        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");

        mFullName.setText(u.getFullName());
        if (u.getPhoto() != null)
            mProPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(u.getPhoto()));
        mEmail.setText(u.getEmail());
        mHometown.setText(u.getHometown());
        mBirthday.setText(u.getBirthday());
        mBio.setText(u.getBio());

        setTitle(u.getFullName() + "'s Profile");


        mFavorites = DAO.getFavorite(mCurrUser);
        for (String s : mFavorites) {
            if (s.equals(u.getEmail())) {
                mFavoriteBox.setChecked(true);
            }
        }



        mFavoriteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(jl, "Click Registered");
                if (isChecked) {
                    mFavorites.add(u.getEmail());
                    DAO.updateFavorite(mCurrUser, mFavorites);
                    logArray();
                }
                if (!isChecked) {
                    for (int i = 0; i < mFavorites.size(); i++) {
                        if (mFavorites.get(i).equals(u.getEmail())) {
                            mFavorites.remove(i);
                            i--;
                        }
                    }
                    DAO.updateFavorite(mCurrUser, mFavorites);
                    logArray();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                restartActivity(UserListActivity.class);
            }
        });

    }

    private void logArray() {
        ArrayList<String> logList = DAO.getFavorite(mCurrUser);
        for (String s : logList) {
            Log.d(jl, s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch(item.getItemId()) {
            case R.id.menu_new_post:
                restartActivity(NewPostActivity.class);
                handled = true;
                break;
            case R.id.menu_user_list:
                restartActivity(UserListActivity.class);
                handled = true;
                break;
            case R.id.menu_favorite_list:
                restartActivity(FavoriteListActivity.class);
                handled = true;
                break;
            case R.id.menu_post_feed:
                restartActivity(FeedItemActivity.class);
                handled = true;
                break;
            case R.id.menu_edit_profile:
                restartActivity(UpdateProfileActivity.class);
                handled = true;
                break;
            case R.id.menu_logout:
                restartActivity(LoginActivity.class);
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void restartActivity(Class activityClass) {
        if (activityClass == LoginActivity.class) {
            Intent intent = new Intent(this, activityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(ViewProfileActivity.this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(jl, "restartActivity() Called");
            Intent intent = new Intent(this, activityClass);
            intent.putExtra("KEY_EMAIL", mCurrUser);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
