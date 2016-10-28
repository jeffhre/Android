package project2.csc296.socialnetwork;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import recycler_feed_items.RecyclerFragment2;


public class FeedItemActivity extends AppCompatActivity {

    String mCurrUser;
    private static final String jl = "JEFF_FEED_ITEM_ACT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item);

        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");

        Bundle bundle = new Bundle();
        bundle.putString("KEY_EMAIL", mCurrUser);
        RecyclerFragment2 rf2 = new RecyclerFragment2();
        rf2.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.recycler_frame, rf2)
                .commit();

        setTitle("My Post Feed");

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
            Toast.makeText(this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
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
