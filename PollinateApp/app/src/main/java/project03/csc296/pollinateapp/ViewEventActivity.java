package project03.csc296.pollinateapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Database.DbDao;
import model.Event;
import model.User;
import soundpool.Radio;
import soundpool.Track;

public class ViewEventActivity extends AppCompatActivity {

    public static final String jl = "JEFF_VIEW_EVENT";

    TextView mEventName;
    TextView mType;
    TextView mStartTime;
    TextView mStartDate;
    TextView mEndTime;
    TextView mEndDate;
    TextView mOrganization;
    ImageView mEventPic;
    TextView mLocation;
    TextView mDescription;
    Button mAddToCalendar;
    Button mCancel;
    Radio mRadio;

    Event e;

    ArrayList<String> mFavorites;


    private String mCurrUser;

    DbDao DAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        DAO = DAO.get(getApplicationContext());
        mRadio = new Radio(getApplicationContext());

        mEventName = (TextView) findViewById(R.id.view_event_name);
        mOrganization = (TextView) findViewById(R.id.view_event_organization);
        mType = (TextView) findViewById(R.id.view_event_type);
        mStartTime = (TextView) findViewById(R.id.text_start_time);
        mStartDate = (TextView) findViewById(R.id.text_start_date);
        mEndTime = (TextView) findViewById(R.id.text_end_time);
        mEndDate = (TextView) findViewById(R.id.text_end_date);
        mLocation = (TextView) findViewById(R.id.text_event_location);
        mDescription = (TextView) findViewById(R.id.text_event_description);
        mEventPic = (ImageView) findViewById(R.id.imageview_posted_pic);
        mAddToCalendar = (Button) findViewById(R.id.button_calendar);
        mCancel = (Button) findViewById(R.id.button_return);

        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");


 //       u = DAO.getUser(getIntent().getStringExtra("CLICKED_EMAIL"));
        e = DAO.getEvent(getIntent().getStringExtra("KEY_EVENT"));
//        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");
        User u = DAO.getUser(e.getEmail());

        if (e.getEventPic() != null)
            mEventPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(e.getEventPic()));
        mEventName.setText(e.getTitle());
        mOrganization.setText("Hosted by " + u.getOrganization());
        mType.setText("Event Type:  " + e.getType());
        mStartTime.setText(e.getStartTime());
        Log.d(jl, "start time : " + e.getStartTime());
        mStartDate.setText(e.getStartDate());
        mEndTime.setText(e.getEndTime());
        mEndDate.setText(e.getEndDate());
        mLocation.setText("Location:  " + e.getLocation());
        Log.d(jl, "location : " + e.getOrganization());
        mDescription.setText("Description:  " + e.getDetails());

        setTitle(e.getTitle());


        mAddToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadio.play(mRadio.getTrack("AddEvent.mp3"));
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, e.getTitle());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, e.getLocation());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, e.getDetails());
                intent.putExtra("beginTime", timeInMillis(e.getStartDate(), e.getStartTime()));
                intent.putExtra("endTime", timeInMillis(e.getEndDate(), e.getEndTime()));
                intent.putExtra("allDay", false);
                startActivity(intent);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity(EventListActivity.class);
            }
        });

    }

    private long timeInMillis(String date, String time) {
        String full = date + " " + time;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(full);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
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
