package project2.csc296.socialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import Database.DbDao;
import model.User;

public class UpdateProfileActivity extends AppCompatActivity {

    private static final String jl = "JEFF_UPDATE_PROFILE";

    String mCurrUser;

    EditText mEditFirstName;
    EditText mEditLastName;
    EditText mEditBDay;
    EditText mEditBMonth;
    EditText mEditBYear;
    EditText mEditHometown;
    EditText mEditBio;
    File mPictureFile;
    ImageView mProPic;
    Button mButtonPic;
    Button mButtonRegister;
    Bitmap mPhoto;

    User u;

    DbDao DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        DAO = DAO.get(getApplicationContext());
        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");
        u = DAO.getUser(mCurrUser);
        mEditFirstName = (EditText) findViewById(R.id.edit_first_name);
        mEditLastName = (EditText) findViewById(R.id.edit_last_name);
        mEditBDay = (EditText) findViewById(R.id.edit_bday);
        mEditBMonth = (EditText) findViewById(R.id.edit_bmonth);
        mEditBYear = (EditText) findViewById(R.id.edit_byear);
        mEditHometown = (EditText) findViewById(R.id.edit_hometown);
        mEditBio = (EditText) findViewById(R.id.edit_bio);
        mProPic = (ImageView) findViewById(R.id.image_propic);
        mButtonPic = (Button) findViewById(R.id.button_propic);
        mButtonRegister = (Button) findViewById(R.id.button_register);


        mEditFirstName.setText((u.getFirstName()));
        mEditLastName.setText(u.getLastName());
        String[] birth = u.getBirthday().split("/");
        String[] safeBirth = new String[3];
        for (int i = 0; i < birth.length; i++) {
            safeBirth[i] = birth[i];
            if (safeBirth[i] == null) {
                safeBirth[i] = "";
            }
        }
        mEditBMonth.setText(safeBirth[0]);
        mEditBDay.setText(safeBirth[1]);
        mEditBYear.setText(safeBirth[2]);
        mEditHometown.setText(u.getHometown());
        mEditBio.setText(u.getBio());

        if (u.getPhoto() != null) {
            mProPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(u.getPhoto()));
        }

        if (savedInstanceState != null) {

            mEditFirstName.setText(savedInstanceState.getString("KEY_FIRST_NAME"));
            mEditLastName.setText(savedInstanceState.getString("KEY_LAST_NAME"));
            mEditBDay.setText(savedInstanceState.getString("KEY_BDAY"));
            mEditBMonth.setText(savedInstanceState.getString("KEY_BMONTH"));
            mEditBYear.setText(savedInstanceState.getString("KEY_BYEAR"));
            mEditHometown.setText(savedInstanceState.getString("KEY_HOMETOWN"));
            mEditBio.setText(savedInstanceState.getString("KEY_BIO"));

            if (savedInstanceState.getByteArray("KEY_PHOTO") != null) {
                mPhoto = new PhotoUtils().getBitmapFromByte(savedInstanceState.getByteArray("KEY_PHOTO"));
                mProPic.setImageBitmap(mPhoto);
            }
        }




        setTitle("Update Profile");

        mButtonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeProPic();
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChanges();
            }
        });
    }

    private void submitChanges() {

        if (u != null) {

            u.setFirstName(mEditFirstName.getText().toString());
            u.setLastName(mEditLastName.getText().toString());
            u.setBirthday(mEditBMonth.getText().toString() + "/" + mEditBDay.getText().toString()
                    + "/" + mEditBYear.getText().toString());
            u.setHometown(mEditHometown.getText().toString());
            u.setBio(mEditBio.getText().toString());
            if (mPhoto != null)
                u.setPhoto(new PhotoUtils().getByteArray(mPhoto));

            DAO.updateUser(u);
            Toast.makeText(this, "Account Updated!", Toast.LENGTH_SHORT).show();
            restartActivity(UserListActivity.class);

        }
        else {
            Toast.makeText(this, "Could Not Update Account!", Toast.LENGTH_SHORT).show();
        }
    }


    private void takeProPic() {
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
                    mButtonPic.getWidth(), mButtonPic.getHeight());
            mProPic.setImageBitmap(mPhoto);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("KEY_FIRST_NAME", mEditFirstName.getText().toString());
        savedInstanceState.putString("KEY_LAST_NAME", mEditLastName.getText().toString());
        savedInstanceState.putString("KEY_BDAY", mEditBDay.getText().toString());
        savedInstanceState.putString("KEY_BMONTH", mEditBMonth.getText().toString());
        savedInstanceState.putString("KEY_BYEAR", mEditBYear.getText().toString());
        savedInstanceState.putString("KEY_HOMETOWN", mEditHometown.getText().toString());
        savedInstanceState.putString("KEY_BIO", mEditBio.getText().toString());
        if (mPhoto != null)
            savedInstanceState.putByteArray("KEY_PHOTO", new PhotoUtils().getByteArray(mPhoto));

        super.onSaveInstanceState(savedInstanceState);

        Log.i(jl, "onSaveInstanceState successful");
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
