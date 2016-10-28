package project01.csc296.threegames;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends Activity {

    private static final String jl = "JEFFLOG";

    private static final String KEY_TTT = "project01.csc296.myTTTkey";
    private static final String P1_NAME_KEY = "ThreeGames.csc296.myp1namekey";
    private static final String P2_NAME_KEY = "ThreeGames.csc296.myp2namekey";
    private static final String P1_SCORE_KEY = "ThreeGames.csc296.myp1scorekey";
    private static final String P2_SCORE_KEY = "ThreeGames.csc296.myp2scorekey";

    private TextView whosTurn;
    private boolean cancelReady = false;

    Intent intent;

    ImageButton box1, box2, box3, box4, box5, box6, box7, box8, box9;
    int state[][] = new int[3][3];
    boolean p1turn = true;
    Button cancelButton;

    TextView p1n;
    TextView p2n;
    TextView p1s;
    TextView p2s;

    ScoreFragment SF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        SF = new ScoreFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag_frame_ttt, SF);
        ft.commit();

        whosTurn = (TextView) findViewById(R.id.turn_indication_ttt);

        box1 = (ImageButton) findViewById(R.id.box1);
        box2 = (ImageButton) findViewById(R.id.box2);
        box3 = (ImageButton) findViewById(R.id.box3);
        box4 = (ImageButton) findViewById(R.id.box4);
        box5 = (ImageButton) findViewById(R.id.box5);
        box6 = (ImageButton) findViewById(R.id.box6);
        box7 = (ImageButton) findViewById(R.id.box7);
        box8 = (ImageButton) findViewById(R.id.box8);
        box9 = (ImageButton) findViewById(R.id.box9);
        cancelButton = (Button) findViewById(R.id.cancel_ttt);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = 0;
            }
        }

        setListeners();


    }

    @Override
    public void onStart() {
        super.onStart();

        intent = getIntent();


        Log.d(jl, intent.getStringExtra(P1_NAME_KEY));
        if (intent != null) {
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                SF.setp1name(intent.getStringExtra(P1_NAME_KEY));
                whosTurn.setText(intent.getStringExtra(P1_NAME_KEY) + "'s Turn");
            }
            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                SF.setp2name(intent.getStringExtra(P2_NAME_KEY));

            }

            SF.setp1score(intent.getIntExtra(P1_SCORE_KEY, 0));
            SF.setp2score(intent.getIntExtra(P2_SCORE_KEY, 0));

            Log.d(jl, Integer.toString(intent.getIntExtra(P2_SCORE_KEY, 0)));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tic_tac_toe, menu);
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

    private void setListeners() {
        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state[0][0] == 0) {

                    if (p1turn) {
                        box1.setImageResource(R.mipmap.dog);
                        state[0][0] = 1;
                    }
                    else {
                        state[0][0] = 2;
                        box1.setImageResource(R.mipmap.bear);

                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state[0][1] == 0) {
                    if (p1turn) {
                        box2.setImageResource(R.mipmap.dog);
                        state[0][1] = 1;
                    }
                    else {
                        state[0][1] = 2;
                        box2.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [0][2] == 0) {
                    if (p1turn) {
                        box3.setImageResource(R.mipmap.dog);
                        state[0][2] = 1;
                    }
                    else {
                        state[0][2] = 2;
                        box3.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [1][0] == 0) {
                    if (p1turn) {
                        box4.setImageResource(R.mipmap.dog);
                        state[1][0] = 1;
                    }
                    else {
                        state[1][0] = 2;
                        box4.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [1][1] == 0) {
                    if (p1turn) {
                        box5.setImageResource(R.mipmap.dog);
                        state[1][1] = 1;
                    }
                    else {
                        state[1][1] = 2;
                        box5.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [1][2] == 0) {
                    if (p1turn) {
                        box6.setImageResource(R.mipmap.dog);
                        state[1][2] = 1;
                    }
                    else {
                        state[1][2] = 2;
                        box6.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [2][0] == 0) {
                    if (p1turn) {
                        box7.setImageResource(R.mipmap.dog);
                        state[2][0] = 1;
                    }
                    else {
                        state[2][0] = 2;
                        box7.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [2][1] == 0) {
                    if (p1turn) {
                        box8.setImageResource(R.mipmap.dog);
                        state[2][1] = 1;
                    }
                    else {
                        state[2][1] = 2;
                        box8.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        box9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state [2][2] == 0) {
                    if (p1turn) {
                        box9.setImageResource(R.mipmap.dog);
                        state[2][2] = 1;
                    }
                    else {
                        state[2][2] = 2;
                        box9.setImageResource(R.mipmap.bear);
                    }
                    winCheck();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Invalid Move!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cancelReady) {
                    cancelReady = true;
                    Toast.makeText(TicTacToeActivity.this, "Click Cancel Again to Confirm", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TicTacToeActivity.this, "Game Cancelled", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.putExtra(KEY_TTT, -1);
                    setResult(RESULT_OK, i);
                    Log.d(jl, "TTT finished with submit - data saved");
                    finish();
                }

            }
        });
    }

    private void winCheck() {
        changePlayer();
        if (win()) {
            if (p1turn) {
                winner(1);
                Intent i = new Intent();
                i.putExtra(KEY_TTT, 1);
                setResult(RESULT_OK, i);
                Log.d(jl, "TTT finished with submit - data saved");
                finish();
            } else {
                winner(2);
                Intent i = new Intent();
                i.putExtra(KEY_TTT, 2);
                setResult(RESULT_OK, i);
                Log.d(jl, "TTT finished with submit - data saved");
                finish();
            }
        }
        else if (draw()) {
            Toast.makeText(TicTacToeActivity.this, "Draw!", Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.putExtra(KEY_TTT, 0);
            setResult(RESULT_OK, i);
            Log.d(jl, "TTT finished with submit - data saved");
            finish();
        }

        p1turn = !p1turn;
    }

    private boolean win() {
        int p;
        if (p1turn)
            p = 1;
        else
            p = 2;
        for (int i = 0; i < 3; i++) {
            if (state[i][0] == p && state[i][1] == p && state[i][2] == p)
                return true;
            if (state[0][i] == p && state[1][i] == p && state[2][i] == p)
                return true;
        }
        if (state[0][0] == p && state[1][1] == p && state[2][2] == p)
            return true;
        if (state[2][0] == p && state[1][1] == p && state[0][2] == p)
            return true;

        return false;
    }
    private void setBox(int i) {

        if (p1turn)
            i = 1;
        else if (!p1turn)
            i = 2;
        Log.i("WINLOG", Integer.toString(i));
    }

    private boolean draw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (state[i][j] == 0)
                    return false;
        return true;
    }

    private void changePlayer() {
        if (p1turn) {
            whosTurn.setTextColor(Color.BLUE);

            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                whosTurn.setText(intent.getStringExtra(P2_NAME_KEY) + "'s Turn");
            }
            else {
                whosTurn.setText("Player 2's Turn");
            }
        }
        else if (!p1turn) {
            whosTurn.setTextColor(Color.RED);
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                whosTurn.setText(intent.getStringExtra(P1_NAME_KEY) + "'s Turn");
            }
            else {
                whosTurn.setText("Player 1's Turn");
            }
        }
    }

    private void winner(int x) {
        if (x == 1) {
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                Toast.makeText(TicTacToeActivity.this, intent.getStringExtra(P1_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(TicTacToeActivity.this, "Player 1 Wins!", Toast.LENGTH_LONG).show();
            }
        }
        if (x == 2) {
            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                Toast.makeText(TicTacToeActivity.this, intent.getStringExtra(P2_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(TicTacToeActivity.this, "Player 2 Wins!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
