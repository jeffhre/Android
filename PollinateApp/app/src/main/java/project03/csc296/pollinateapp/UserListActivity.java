package project03.csc296.pollinateapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import Database.DbDao;
import soundpool.Radio;
import soundpool.Track;
import user_recycler.UserRecyclerFragment;

public class UserListActivity extends AppCompatActivity {

    private static final String jl = "JEFF_USER_ACTIVITY";

    String mCurrUser;
    DbDao DAO;
    ArrayList<String> mFavorites;
    Radio mRadio;
    Fragment urf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");
        Bundle bundle = new Bundle();
        bundle.putString("KEY_EMAIL", mCurrUser);
        urf = new UserRecyclerFragment();
        urf.setArguments(bundle);
        mRadio = new Radio(getApplicationContext());

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.user_recycler_frame, urf)
                .commit();

        setTitle("All Users");

        DAO = DAO.get(getApplicationContext());

        mFavorites = DAO.getFavoriteUsers(mCurrUser);

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

    @Override
    public void onSaveInstanceState(Bundle b) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .remove(urf)
                .commit();
        super.onSaveInstanceState(b);
    }
}