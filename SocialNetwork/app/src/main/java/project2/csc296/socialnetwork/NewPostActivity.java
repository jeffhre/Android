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
import model.FeedItem;

public class NewPostActivity extends AppCompatActivity {

    DbDao DAO;

    String mCurrUser;
    EditText mEditContent;
    ImageView mPostedPic;
    Button mTakePicture;
    Button mPost;
    File mPictureFile;
    Bitmap mPhoto;
    private static final String jl = "JEFF_NEW_POST_ACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mCurrUser = getIntent().getStringExtra("KEY_EMAIL");

        setTitle("New Post");

        DAO = DAO.get(getApplicationContext());

        mEditContent = (EditText) findViewById(R.id.edit_content);
        mPostedPic = (ImageView) findViewById(R.id.image_posted_pic);
        mTakePicture = (Button) findViewById(R.id.button_picture);

        mPost = (Button) findViewById(R.id.button_post);

        if (savedInstanceState != null) {

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
    }

    private void submitPost() {
        FeedItem f = new FeedItem();
        f.setEmail(mCurrUser);
        f.setContent(mEditContent.getText().toString());
        f.setPostDate(Long.toString(System.currentTimeMillis()));
        if (mPhoto != null)
            f.setPostedPic(new PhotoUtils().getByteArray(mPhoto));
        DAO.addFeedItem(f);
        Toast.makeText(this, "Post Successfully Submitted!", Toast.LENGTH_SHORT).show();
        restartActivity(FeedItemActivity.class);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("KEY_CONTENT", mEditContent.getText().toString());
        if (mPhoto != null)
            savedInstanceState.putByteArray("KEY_PHOTO", new PhotoUtils().getByteArray(mPhoto));

        super.onSaveInstanceState(savedInstanceState);

        Log.i(jl, "onSaveInstanceState successful");
    }
}
