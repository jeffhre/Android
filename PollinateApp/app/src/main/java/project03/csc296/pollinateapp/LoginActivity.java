package project03.csc296.pollinateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Database.DbDao;
import model.User;
import soundpool.Radio;
import soundpool.Track;

public class LoginActivity extends AppCompatActivity {

    private static final String jl = "JEFF_LOGIN_ACTIVITY";

    EditText mEmailEdit;
    EditText mPasswordEdit;
    Button mLogin;
    Button mRegister;
    ImageView mPollinate;
    DbDao DAO;
    ArrayList<String> mFavorites;
    Animation animation;
    Radio mRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailEdit = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit_text);
        mLogin = (Button) findViewById(R.id.login_button);
        mRegister = (Button) findViewById(R.id.register_button);
        mPollinate = (ImageView) findViewById(R.id.image_large_pollinate);
        mRadio = new Radio(getApplicationContext());

        DAO = DAO.get(getApplicationContext());
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
//        animation.setAnimationListener(LoginActivity.this);
        mPollinate.setVisibility(View.VISIBLE);
        mPollinate.startAnimation(animation);

        if (savedInstanceState != null) {
            mEmailEdit.setText(savedInstanceState.getString("KEY_EMAIL"));
            mPasswordEdit.setText(savedInstanceState.getString("KEY_PASSWORD"));

        }

        setTitle("Sign In");


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(mEmailEdit.getText().toString(), mPasswordEdit.getText().toString());
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void login(String email, String password) {
        User u = DAO.getUser(email);
        if (u == null) {
            Toast.makeText(this, "No Account Exists Under This Email!", Toast.LENGTH_SHORT).show();
        }
        else if (!u.getPassword().equals(password)) {
            Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
        }
        else {
            Track t = mRadio.getTrack("SignIn.mp3");
            mRadio.play(t);
            Toast.makeText(this, "Welcome " + u.getOrganization() + "!", Toast.LENGTH_LONG).show();
//            mFavorites = DAO.getFavorite(email);
//            boolean noFavorites = true;
//            for (String s : mFavorites) {
//                if (s != "" && s != "," && s != " " && s != ", " && s != " ,") {
//                    noFavorites = false;
//                }
//            }
//            if (noFavorites) {
//                Intent i = new Intent(this, WelcomeActivity.class);
//                i.putExtra("KEY_EMAIL", email);
//                startActivity(i);
//            }
            Intent i = new Intent(this, EventListActivity.class);
            i.putExtra("KEY_EMAIL", email);
            startActivity(i);
//            }
        }

    }

    private void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("KEY_EMAIL", mEmailEdit.getText().toString());
        savedInstanceState.putString("KEY_PASSWORD", mPasswordEdit.getText().toString());

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
