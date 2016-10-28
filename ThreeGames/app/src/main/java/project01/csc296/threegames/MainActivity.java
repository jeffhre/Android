package project01.csc296.threegames;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String jl = "JEFFLOG";
    private static final String KEY_TTT = "project01.csc296.myTTTkey";
    private static final String KEY_C4 = "ThreeGames.csc296.myConnect4String";
    private static final String KEY_HM = "ThreeGames.csc296.myHangmanString";

    private static final int RC_TTT = 2;
    private static final int RC_HM = 3;
    private static final int RC_C4 = 4;

    private int p1score = 0;
    private int p2score = 0;
    private static final String P1_SCORE_KEY = "ThreeGames.csc296.myp1scorekey";
    private static final String P2_SCORE_KEY = "ThreeGames.csc296.myp2scorekey";

    Button mTTTButton;
    Button mHMButton;
    Button mC4Button;

    ScoreFragment SF;

    EditText p1edit;
    EditText p2edit;

    private String p1name = "Player 1";
    private String p2name = "Player 2";
    private static final String P1_NAME_KEY = "ThreeGames.csc296.myp1namekey";
    private static final String P2_NAME_KEY = "ThreeGames.csc296.myp2namekey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTTTButton = (Button) findViewById(R.id.ttt_button);
        mHMButton = (Button) findViewById(R.id.hangman_button);
        mC4Button = (Button) findViewById(R.id.connect4_button);

        p1edit = (EditText) findViewById(R.id.p1edit);
        p2edit = (EditText) findViewById(R.id.p2edit);

        SF = new ScoreFragment();

        Bundle b = new Bundle();
        b.putString(P1_NAME_KEY, p1name);
        b.putString(P2_NAME_KEY, p2name);
        b.putInt(P1_SCORE_KEY, p1score);
        b.putInt(P2_SCORE_KEY, p2score);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.frag_frame_main, SF)
                .commit();



        p1edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                p1name = p1edit.getText().toString();
                Log.i(jl, p1name);
                SF.setp1name(p1name);
            }
        });

        p2edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                p2name = p2edit.getText().toString();
                SF.setp2name(p2name);
            }
        });

        mTTTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TicTacToeActivity.class);
                i.putExtra(P1_NAME_KEY, p1name);
                i.putExtra(P2_NAME_KEY, p2name);
                i.putExtra(P1_SCORE_KEY, p1score);
                i.putExtra(P2_SCORE_KEY, p2score);
                startActivityForResult(i, RC_TTT);

            }
        });

        mHMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(MainActivity.this, HangmanActivity.class);
                i.putExtra(P1_NAME_KEY, p1name);
                i.putExtra(P2_NAME_KEY, p2name);
                i.putExtra(P1_SCORE_KEY, p1score);
                i.putExtra(P2_SCORE_KEY, p2score);
                startActivityForResult(i, RC_HM);

            }
        });

        mC4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Connect4Activity.class);
                i.putExtra(P1_NAME_KEY, p1name);
                i.putExtra(P2_NAME_KEY, p2name);
                i.putExtra(P1_SCORE_KEY, p1score);
                i.putExtra(P2_SCORE_KEY, p2score);
                startActivityForResult(i, RC_C4);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up ImageButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int i = -1;
        if (requestCode == RC_TTT) {
            if (resultCode == RESULT_OK) {
                i = data.getIntExtra(KEY_TTT, -1);

            }
        }
        else if (requestCode == RC_C4) {
            if (resultCode == RESULT_OK) {
                i = data.getIntExtra(KEY_C4, -1);

            }
        }
        else if (requestCode == RC_HM) {
            if (resultCode == RESULT_OK) {
                i = data.getIntExtra(KEY_HM, -1);

            }
        }
        if (i == 0) {
            p1score = p1score + 500;
            p2score = p2score + 500;
        }
        else if (i == 1) {
            p1score = p1score + 1000;
        }
        else if (i == 2) {
            p2score = p2score + 1000;
        }


        SF.setp1name(p1name);
        SF.setp2name(p2name);
        SF.setp1score(p1score);
        SF.setp2score(p2score);

    }
}
