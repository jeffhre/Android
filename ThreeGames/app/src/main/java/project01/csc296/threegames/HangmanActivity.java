package project01.csc296.threegames;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HangmanActivity extends Activity {

    private static final String jl = "JEFFLOG";
    private static final String KEY_HM = "ThreeGames.csc296.myHangmanString";
    private static final String P1_NAME_KEY = "ThreeGames.csc296.myp1namekey";
    private static final String P2_NAME_KEY = "ThreeGames.csc296.myp2namekey";
    private static final String P1_SCORE_KEY = "ThreeGames.csc296.myp1scorekey";
    private static final String P2_SCORE_KEY = "ThreeGames.csc296.myp2scorekey";

    private String answer;
    private char answerChar[];
    private TextView ans[] = new TextView[7];
    private boolean guessed[];
    private Button letter[] = new Button[26];
    private boolean clicked[] = new boolean[26];
    private TextView missCount;
    private TextView turnText;
    private boolean p1turn = true;
    private int p1score;
    private int p2score;
    private int misses = 0;
    ScoreFragment SF;
    Intent intent;
    private Button cancelButton;
    private boolean cancelReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        Log.i(jl, "content view set");

        SF = new ScoreFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.frag_frame_hm, SF)
                .commit();

        missCount = (TextView) findViewById(R.id.miss_count);
        turnText = (TextView) findViewById(R.id.turn_text);

        ans[0] = (TextView) findViewById(R.id.ans1);
        ans[1] = (TextView) findViewById(R.id.ans2);
        ans[2] = (TextView) findViewById(R.id.ans3);
        ans[3] = (TextView) findViewById(R.id.ans4);
        ans[4] = (TextView) findViewById(R.id.ans5);
        ans[5] = (TextView) findViewById(R.id.ans6);
        ans[6] = (TextView) findViewById(R.id.ans7);

        cancelButton = (Button) findViewById(R.id.cancel_hm);

        letter[0] = (Button) findViewById(R.id.a_button);
        letter[1] = (Button) findViewById(R.id.b_button);
        letter[2] = (Button) findViewById(R.id.c_button);
        letter[3] = (Button) findViewById(R.id.d_button);
        letter[4] = (Button) findViewById(R.id.e_button);
        letter[5] = (Button) findViewById(R.id.f_button);
        letter[6] = (Button) findViewById(R.id.g_button);
        letter[7] = (Button) findViewById(R.id.h_button);
        letter[8] = (Button) findViewById(R.id.i_button);
        letter[9] = (Button) findViewById(R.id.j_button);
        letter[10] = (Button) findViewById(R.id.k_button);
        letter[11] = (Button) findViewById(R.id.l_button);
        letter[12] = (Button) findViewById(R.id.m_button);
        letter[13] = (Button) findViewById(R.id.n_button);
        letter[14] = (Button) findViewById(R.id.o_button);
        letter[15] = (Button) findViewById(R.id.p_button);
        letter[16] = (Button) findViewById(R.id.q_button);
        letter[17] = (Button) findViewById(R.id.r_button);
        letter[18] = (Button) findViewById(R.id.s_button);
        letter[19] = (Button) findViewById(R.id.t_button);
        letter[20] = (Button) findViewById(R.id.u_button);
        letter[21] = (Button) findViewById(R.id.v_button);
        letter[22] = (Button) findViewById(R.id.w_button);
        letter[23] = (Button) findViewById(R.id.x_button);
        letter[24] = (Button) findViewById(R.id.y_button);
        letter[25] = (Button) findViewById(R.id.z_button);



        answer = randomAnswer();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cancelReady) {
                    cancelReady = true;
                    Toast.makeText(HangmanActivity.this, "Click Cancel Again to Confirm", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(HangmanActivity.this, "Game Cancelled", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.putExtra(KEY_HM, -1);
                    setResult(RESULT_OK, i);
                    Log.d(jl, "TTT finished with submit - data saved");
                    finish();
                }

            }
        });

        setup();

    }

    @Override
    public void onStart() {
        super.onStart();

        intent = getIntent();


        Log.d(jl, intent.getStringExtra(P1_NAME_KEY));
        if (intent != null) {
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                SF.setp1name(intent.getStringExtra(P1_NAME_KEY));
            }
            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                SF.setp2name(intent.getStringExtra(P2_NAME_KEY));

            }

            SF.setp1score(intent.getIntExtra(P1_SCORE_KEY, 0));
            SF.setp2score(intent.getIntExtra(P2_SCORE_KEY, 0));

            Log.d(jl, Integer.toString(intent.getIntExtra(P2_SCORE_KEY, 0)));

            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                turnText.setText(intent.getStringExtra(P1_NAME_KEY) + "'s Turn");
            }
            else {
                turnText.setText("Player 1's Turn");
            }
        }

    }


    private void setup() {


        answerChar = answer.toCharArray();
        guessed = new boolean[answerChar.length];

        for (int i = 0; i < answerChar.length; i++) {
            ans[i].setText("_");
            guessed[i] = false;
        }

        for(int i = 0; i < 26; i++) {

            clicked[i] = false;
            final int j = i;

            letter[i].setTextColor(Color.BLACK);
            letter[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    guess(j);
                }
            });
        }

    }

    private String randomAnswer() {
        double i = Math.random();
        if (i < .1)
            return "dolphin";
        else if (i < .2)
            return "guitar";
        else if (i < .3)
            return "igloo";
        else if (i < .4)
            return "monkey";
        else if (i < .5)
            return "radio";
        else if (i < .6)
            return "basket";
        else if (i < .7)
            return "blanket";
        else if (i < .8)
            return "truck";
        else if (i < .9)
            return "swan";
        else
            return "zebra";


    }

    private void guess(int i) {

        char guess = (char) (i + 97);
        Log.i(jl, Character.toString(guess));

        if (clicked[i]) {
            Toast.makeText(HangmanActivity.this, "Letter " + guess + " already guessed!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean hit = false;
        clicked[i] = true;

        for (int j = 0; j < answerChar.length; j++) {
            if (answerChar[j] == guess) {
                ans[j].setText(Character.toString(guess));
                guessed[j] = true;
                hit = true;
                letter[i].setTextColor(Color.GREEN);
            }
        }
        if (!hit) {
            misses = misses + 1;
            String m = Integer.toString(misses);
             missCount.setText(m);
            letter[i].setTextColor(Color.RED);
        }

        if (isComplete() || (misses >= 8)) {
            if (p1turn)
                p1score = misses;
            else {
                p2score = misses;
                if (p1score == p2score) {
                    Log.i(jl, "Draw!");
                    Toast.makeText(HangmanActivity.this, "Draw!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(KEY_HM, 0);
                    setResult(RESULT_OK, intent);
                    Log.d(jl, "HM finished with submit - data saved");
                    finish();
                }
                else if (p1score < p2score) {
                    Log.i(jl, "Player 1 wins!");
                    winner(1);
                    Intent intent = new Intent();
                    intent.putExtra(KEY_HM, 1);
                    setResult(RESULT_OK, intent);
                    Log.d(jl, "HM finished with submit - data saved");
                    finish();
                }
                else if (p1score > p2score) {
                    Log.i(jl, "Player 2 wins!");
                    winner(2);
                    Intent intent = new Intent();
                    intent.putExtra(KEY_HM, 2);
                    setResult(RESULT_OK, intent);
                    Log.d(jl, "HM finished with submit - data saved");
                    finish();
                }
            }

            setup();

            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                turnText.setText(intent.getStringExtra(P2_NAME_KEY) + "'s Turn");
            }
            else {
                turnText.setText("Player 2's Turn");
            }
            turnText.setTextColor(Color.BLUE);
            misses = 0;
            String m = Integer.toString(misses);
            missCount.setText(m);
            p1turn = false;

        }

    }

    private boolean isComplete() {
        for (int i = 0; i < answerChar.length; i ++) {
            if (!guessed[i])
                return false;
        }
        return true;
    }

    private void winner(int x) {
        if (x == 1) {
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                Toast.makeText(HangmanActivity.this, intent.getStringExtra(P1_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(HangmanActivity.this, "Player 1 Wins!", Toast.LENGTH_LONG).show();
            }
        }
        if (x == 2) {
            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                Toast.makeText(HangmanActivity.this, intent.getStringExtra(P2_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(HangmanActivity.this, "Player 2 Wins!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
