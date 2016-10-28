package project03.csc296.pollinateapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import Database.DbDao;
import model.User;
import soundpool.Radio;
import soundpool.Track;

public class RegisterActivity extends AppCompatActivity {

    private static final String jl = "JEFF_REGISTER_ACTIVITY";

    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditConfirmPassword;
    EditText mEditOrganization;
    EditText mEditLocation;
    EditText mEditBio;
    File mPictureFile;
    ImageView mProPic;
    Button mButtonPic;
    Button mButtonRegister;
    Bitmap mPhoto;
    Radio mRadio;

    DbDao DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEditPassword = (EditText) findViewById(R.id.edit_password);
        mEditConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        mEditOrganization = (EditText) findViewById(R.id.edit_organization);
        mEditLocation = (EditText) findViewById(R.id.edit_location);
        mEditBio = (EditText) findViewById(R.id.edit_bio);
        mProPic = (ImageView) findViewById(R.id.image_propic);
        mButtonPic = (Button) findViewById(R.id.button_propic);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mRadio = new Radio(getApplicationContext());

        if (savedInstanceState != null) {
            mEditEmail.setText(savedInstanceState.getString("KEY_EMAIL"));
            mEditPassword.setText(savedInstanceState.getString("KEY_PASSWORD"));
            mEditConfirmPassword.setText(savedInstanceState.getString("KEY_CONFIRM_PASSWORD"));
            mEditOrganization.setText(savedInstanceState.getString("KEY_ORGANIZATION"));
            mEditLocation.setText(savedInstanceState.getString("KEY_LOCATION"));
            mEditBio.setText(savedInstanceState.getString("KEY_BIO"));
            mEditEmail.setText(savedInstanceState.getString("KEY_EMAIL"));
            if (savedInstanceState.getByteArray("KEY_PHOTO") != null) {
                mPhoto = new PhotoUtils().getBitmapFromByte(savedInstanceState.getByteArray("KEY_PHOTO"));
                mProPic.setImageBitmap(mPhoto);
            }
        }

        DAO = DAO.get(getApplicationContext());

        setTitle("Register");

        mButtonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeProPic();
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegister();
            }
        });
    }

    private void submitRegister() {
        User r = DAO.getUser(mEditEmail.getText().toString());
        if (r == null) {
            if (!mEditEmail.getText().toString().equals("") && !mEditPassword.getText().toString().equals("") && !mEditOrganization.getText().toString().equals("")) {
                if (mEditPassword.getText().toString().equals(mEditConfirmPassword.getText().toString())) {

                    Log.d(jl, "mEditEmail = " + mEditEmail.getText().toString());
                    Log.d(jl, "mEditPassword = " + mEditPassword.getText().toString());

                    Track t = mRadio.getTrack("SignIn.mp3");
                    mRadio.play(t);

                    User u = new User();
                    u.setEmail(mEditEmail.getText().toString());
                    u.setPassword(mEditPassword.getText().toString());
                    u.setOrganization(mEditOrganization.getText().toString());
                    u.setLocation(mEditLocation.getText().toString());
                    u.setBio(mEditBio.getText().toString());
                    if (mPhoto != null)
                        u.setPhoto(new PhotoUtils().getByteArray(mPhoto));

                    DAO.addUser(u);
                    Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, WelcomeActivity.class);
                    i.putExtra("KEY_EMAIL", u.getEmail());
                    startActivity(i);

                    finish();
                }
                else {
                    Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a valid email, password and organization/name!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "An Account Already Exists Under This Email!", Toast.LENGTH_SHORT).show();
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

        savedInstanceState.putString("KEY_EMAIL", mEditEmail.getText().toString());
        savedInstanceState.putString("KEY_PASSWORD", mEditPassword.getText().toString());
        savedInstanceState.putString("KEY_CONFIRM_PASSWORD", mEditConfirmPassword.getText().toString());
        savedInstanceState.putString("KEY_ORGANIZATION", mEditOrganization.getText().toString());
        savedInstanceState.putString("KEY_LOCATION", mEditLocation.getText().toString());
        savedInstanceState.putString("KEY_BIO", mEditBio.getText().toString());
        if (mPhoto != null)
            savedInstanceState.putByteArray("KEY_PHOTO", new PhotoUtils().getByteArray(mPhoto));

        super.onSaveInstanceState(savedInstanceState);

        Log.i(jl, "onSaveInstanceState successful");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(jl, "onDestroy() called");
        mRadio.release();
    }



}
