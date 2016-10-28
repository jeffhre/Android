package project03.csc296.pollinateapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import soundpool.Radio;
import soundpool.Track;

public class ViewUserActivity extends AppCompatActivity {

    public static final String jl = "JEFF_VIEW_PROFILE";

    TextView mOrganization;
    ImageView mProPic;
    TextView mEmail;
    TextView mLocation;
    TextView mBio;
    Button mFollow;
    Button mCancel;
    Radio mRadio;

    ArrayList<String> mFavorites;

    User u;
    private String mCurrUser;
    boolean mFollowing;


    DbDao DAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        DAO = DAO.get(getApplicationContext());

        mOrganization = (TextView) findViewById(R.id.text_organization);
        mProPic = (ImageView) findViewById(R.id.image_user_propic);
        mEmail = (TextView) findViewById(R.id.edit_user_email);
        mLocation = (TextView) findViewById(R.id.text_user_location);
        mBio = (TextView) findViewById(R.id.text_user_bio);
        mFollow = (Button) findViewById(R.id.button_follow);
        mCancel = (Button) findViewById(R.id.button_back);
        mRadio = new Radio(getApplicationContext());

        mFollowing = false;

        u = DAO.getUser(getIntent().getStringExtra("CLICKED_EMAIL"));
        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");

        if (u.getPhoto() != null)
            mProPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(u.getPhoto()));
        mEmail.setText(u.getEmail());
        mLocation.setText(u.getLocation());
        mOrganization.setText(u.getOrganization());
        mBio.setText(u.getBio());

        setTitle(u.getOrganization() + "'s Profile");


        mFavorites = DAO.getFavoriteUsers(mCurrUser);

        for (String s : mFavorites) {
            if (s.equals(u.getEmail())) {
                mFollowing = true;
                mFollow.setText("Unfollow");
            }
        }



//        mFavoriteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d(jl, "Click Registered");
//                if (isChecked) {
//                    mFavorites.add(u.getEmail());
//                    DAO.updateFavorite(mCurrUser, mFavorites);
//                    logArray();
//                }
//                if (!isChecked) {
//                    for (int i = 0; i < mFavorites.size(); i++) {
//                        if (mFavorites.get(i).equals(u.getEmail())) {
//                            mFavorites.remove(i);
//                            i--;
//                        }
//                    }
//                    DAO.updateFavorite(mCurrUser, mFavorites);
//                    logArray();
//                }
//            }
//        });

        mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean followNow = mFollowing;
                if (mFollowing) {
                    new AlertDialog.Builder(ViewUserActivity.this)
                            .setTitle("Unfollow " + u.getEmail() + "?")
                            .setMessage("You will no longer see all of their event postings")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    unfollow();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    follow();
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
    private void follow() {
        mFollowing = true;
        mFavorites.add(u.getEmail());
        mFollow.setText("Unfollow");
        DAO.updateFavoriteUsers(mCurrUser, mFavorites);
        logArray();
    }

    private void unfollow() {
        mFollowing = false;
        mFavorites.remove(u.getEmail());
        mFollow.setText("Follow");
        DAO.updateFavoriteUsers(mCurrUser, mFavorites);
        logArray();
    }

    private void logArray() {
        ArrayList<String> logList = DAO.getFavoriteUsers(mCurrUser);
        for (String s : logList) {
            Log.d(jl, s);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {

            case R.id.menu_new_event:
                restartActivity(PostEventActivity.class);
                handled = true;
                break;
            case R.id.menu_view_events:
                restartActivity(EventListActivity.class);
                handled = true;
                break;
            case R.id.menu_event_prefs:
                restartActivity(EventPreferenceActivity.class);
                handled = true;
                break;
            case R.id.menu_all_users:
                restartActivity(UserListActivity.class);
                handled = true;
                break;
            case R.id.menu_tutorial:
                restartActivity(WelcomeActivity.class);
                handled = true;
                break;
            case R.id.menu_logout:
                restartActivity(LoginActivity.class);
                handled = true;
                break;
            case android.R.id.home:
                restartActivity(EventListActivity.class);
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void restartActivity(Class activityClass) {
        if (activityClass == LoginActivity.class) {
            logoutSound();
            Intent intent = new Intent(this, activityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(jl, "restartActivity() Called");
            Intent intent = new Intent(this, activityClass);
            intent.putExtra("KEY_EMAIL", mCurrUser);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void logoutSound() {
        Track t = mRadio.getTrack("SignOut.mp3");
        mRadio.play(t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(jl, "onDestroy() called");
        mRadio.release();
    }

}
