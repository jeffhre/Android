package project03.csc296.pollinateapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import Database.DbDao;
import model.Event;
import model.EventTypes;
import model.User;
import soundpool.Radio;
import soundpool.Track;

public class PostEventActivity extends AppCompatActivity {

    DbDao DAO;

    String mCurrUser;
    EditText mEditTitle;
    EditText mEditLocation;
    EditText mEditContent;

    Radio mRadio;
    String mOrganization;
    ImageView mPostedPic;
    Button mTakePicture;
    Button mPost;
    File mPictureFile;
    Bitmap mPhoto;
    private static final String jl = "JEFF_NEW_POST_ACT";

    static TextView mStartTime;
    static TextView mStartDate;
    static TextView mEndTime;
    static TextView mEndDate;

    Button mStartTimeButton;
    Button mStartDateButton;
    Button mEndTimeButton;
    Button mEndDateButton;

    Spinner mEventType;

    static boolean mIsStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");

        setTitle("New Post");

        mRadio = new Radio(getApplicationContext());
        DAO = DAO.get(getApplicationContext());

        User u = DAO.getUser(mCurrUser);
        mOrganization = u.getOrganization();

        mEditTitle = (EditText) findViewById(R.id.edit_event_name);
        mEditLocation = (EditText) findViewById(R.id.edit_event_location);
        mEditContent = (EditText) findViewById(R.id.edit_event_content);
        mPostedPic = (ImageView) findViewById(R.id.image_posted_pic);
        mTakePicture = (Button) findViewById(R.id.button_picture);
        mEventType = (Spinner) findViewById(R.id.event_type_spinner);

        mStartTime = (TextView) findViewById(R.id.text_event_start_time);
        mStartDate = (TextView) findViewById(R.id.text_event_start_date);
        mEndTime = (TextView) findViewById(R.id.text_event_end_time);
        mEndDate = (TextView) findViewById(R.id.text_event_end_date);
        mStartTimeButton = (Button) findViewById(R.id.button_event_start_time);
        mStartDateButton = (Button) findViewById(R.id.button_event_start_date);
        mEndTimeButton = (Button) findViewById(R.id.button_event_end_time);
        mEndDateButton = (Button) findViewById(R.id.button_event_end_date);

        Spinner dropdown = (Spinner)findViewById(R.id.event_type_spinner);
        String[] items = new EventTypes().getEventTypeArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        mPost = (Button) findViewById(R.id.button_post);

        if (savedInstanceState != null) {

            mStartTime.setText(savedInstanceState.getString("KEY_START_TIME"));
            mStartDate.setText(savedInstanceState.getString("KEY_START_DATE"));
            mEndTime.setText(savedInstanceState.getString("KEY_END_TIME"));
            mEndDate.setText(savedInstanceState.getString("KEY_END_DATE"));

            mEditContent.setText(savedInstanceState.getString("KEY_CONTENT"));
            if (savedInstanceState.getByteArray("KEY_PHOTO") != null) {
                mPhoto = new PhotoUtils().getBitmapFromByte(savedInstanceState.getByteArray("KEY_PHOTO"));
                mPostedPic.setImageBitmap(mPhoto);
            }
        }


        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsStart = true;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsStart = false;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsStart = true;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsStart = false;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void submitPost() {
        if (!mEditTitle.getText().equals("") && !mStartTime.getText().equals("") && !mStartDate.getText().equals("")) {
            Event e = new Event();
            e.setTitle(mEditTitle.getText().toString());
            e.setEmail(mCurrUser);
            e.setOrganization(mOrganization);
            e.setDetails(mEditContent.getText().toString());
            e.setPostDate(Long.toString(System.currentTimeMillis()));
            e.setStartTime(mStartTime.getText().toString());
            e.setEndTime(mEndTime.getText().toString());
            e.setStartDate(mStartDate.getText().toString());
            e.setEndDate(mEndDate.getText().toString());
            e.setLocation(mEditLocation.getText().toString());
            e.setType(mEventType.getSelectedItem().toString());

            Log.d(jl, mEventType.getSelectedItem().toString());

            if (mPhoto != null)
                e.setEventPic(new PhotoUtils().getByteArray(mPhoto));

            DAO.addEvent(e);
            Toast.makeText(this, "Event Successfully Submitted!", Toast.LENGTH_SHORT).show();
            restartActivity(EventListActivity.class);
            finish();
        }
        else {
            Toast.makeText(this, "Must enter a title and start time/date!", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String filename =
                "IMG_" + UUID.randomUUID().toString() + ".jpg";
        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mPictureFile = new File(picturesDir, filename);
        Uri photoUri = Uri.fromFile(mPictureFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            updatePhoto();
        }
    }

    private void updatePhoto() {
        if (mPictureFile == null || !mPictureFile.exists()) {

        } else {
            mPhoto = new PhotoUtils().getScaledBitmap(mPictureFile.getPath(),
                    mPostedPic.getWidth(), mPostedPic.getHeight());
            mPostedPic.setImageBitmap(mPhoto);

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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("KEY_START_TIME", mStartTime.getText().toString());
        savedInstanceState.putString("KEY_START_DATE", mStartDate.getText().toString());
        savedInstanceState.putString("KEY_END_TIME", mEndTime.getText().toString());
        savedInstanceState.putString("KEY_END_DATE", mEndDate.getText().toString());

        savedInstanceState.putString("KEY_CONTENT", mEditContent.getText().toString());
        if (mPhoto != null)
            savedInstanceState.putByteArray("KEY_PHOTO", new PhotoUtils().getByteArray(mPhoto));

        super.onSaveInstanceState(savedInstanceState);

        Log.i(jl, "onSaveInstanceState successful");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (mIsStart) {
                mStartDate.setText((month+1) + "/" + day + "/" + year);
            }
            else {
                mEndDate.setText((month+1) + "/" + day + "/" + year);
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String min = Integer.toString(minute);
            if (minute < 10) {
                min = "0" + Integer.toString(minute);
            }
            if (mIsStart) {
                mStartTime.setText(hourOfDay + ":" + min);
            }

            else {
                mEndTime.setText(hourOfDay + ":" + min);
            }
        }
    }

    private void showDate(int year, int month, int day, boolean start) {
        if (start) {
            mStartDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
        else {
            mEndDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }

}
