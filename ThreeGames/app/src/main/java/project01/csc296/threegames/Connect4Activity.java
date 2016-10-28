package project01.csc296.threegames;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Connect4Activity extends Activity {

    private static final String jl = "JEFFLOG";
    private static final String KEY_C4 = "ThreeGames.csc296.myConnect4String";
    private static final String P1_NAME_KEY = "ThreeGames.csc296.myp1namekey";
    private static final String P2_NAME_KEY = "ThreeGames.csc296.myp2namekey";
    private static final String P1_SCORE_KEY = "ThreeGames.csc296.myp1scorekey";
    private static final String P2_SCORE_KEY = "ThreeGames.csc296.myp2scorekey";

    private int state[][] = new int[5][5];
    private ImageView image[][] = new ImageView[5][5];
    private ImageButton button[] = new ImageButton[5];
    private TextView whosTurn;
    private boolean p1turn = true;
    private boolean cancelReady = false;

    private Button cancelButton;

    Intent intent;

    ScoreFragment SF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect4);

        SF = new ScoreFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.frag_frame_c4, SF)
                .commit();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                state[i][j] = 0;

        cancelButton = (Button) findViewById(R.id.cancel_c4);

        image[0][0] = (ImageView) findViewById(R.id.box1);
        image[0][1] = (ImageView) findViewById(R.id.box2);
        image[0][2] = (ImageView) findViewById(R.id.box3);
        image[0][3] = (ImageView) findViewById(R.id.box4);
        image[0][4] = (ImageView) findViewById(R.id.box5);
        image[1][0] = (ImageView) findViewById(R.id.box6);
        image[1][1] = (ImageView) findViewById(R.id.box7);
        image[1][2] = (ImageView) findViewById(R.id.box8);
        image[1][3] = (ImageView) findViewById(R.id.box9);
        image[1][4] = (ImageView) findViewById(R.id.box10);
        image[2][0] = (ImageView) findViewById(R.id.box11);
        image[2][1] = (ImageView) findViewById(R.id.box12);
        image[2][2] = (ImageView) findViewById(R.id.box13);
        image[2][3] = (ImageView) findViewById(R.id.box14);
        image[2][4] = (ImageView) findViewById(R.id.box15);
        image[3][0] = (ImageView) findViewById(R.id.box16);
        image[3][1] = (ImageView) findViewById(R.id.box17);
        image[3][2] = (ImageView) findViewById(R.id.box18);
        image[3][3] = (ImageView) findViewById(R.id.box19);
        image[3][4] = (ImageView) findViewById(R.id.box20);
        image[4][0] = (ImageView) findViewById(R.id.box21);
        image[4][1] = (ImageView) findViewById(R.id.box22);
        image[4][2] = (ImageView) findViewById(R.id.box23);
        image[4][3] = (ImageView) findViewById(R.id.box24);
        image[4][4] = (ImageView) findViewById(R.id.box25);


        button[0] = (ImageButton) findViewById(R.id.button1);
        button[1] = (ImageButton) findViewById(R.id.button2);
        button[2] = (ImageButton) findViewById(R.id.button3);
        button[3] = (ImageButton) findViewById(R.id.button4);
        button[4] = (ImageButton) findViewById(R.id.button5);

        whosTurn = (TextView) findViewById(R.id.turn_indicator_c4);
        whosTurn.setTextColor(Color.RED);





        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(0);
            }
        });
        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1);
            }
        });
        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(2);
            }
        });
        button[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(3);
            }
        });
        button[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(4);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cancelReady) {
                    cancelReady = true;
                    Toast.makeText(Connect4Activity.this, "Click Cancel Again to Confirm", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Connect4Activity.this, "Game Cancelled", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.putExtra(KEY_C4, -1);
                    setResult(RESULT_OK, i);
                    Log.d(jl, "TTT finished with submit - data saved");
                    finish();
                }

            }
        });

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

    private void move(int x) {
        for (int i = 4; i >= 0; i--) {
            if (state[i][x] == 0) {
                if (p1turn) {
                    state[i][x] = 1;
                    image[i][x].setImageResource(R.mipmap.p1);
                    winCheck();
                    changePlayer();
                    p1turn = !p1turn;
                    return;
                }
                else if (!p1turn) {
                    state[i][x] = 2;
                    image[i][x].setImageResource(R.mipmap.p2);
                    winCheck();
                    changePlayer();
                    p1turn = !p1turn;
                    return;
                }
            }
        }
        Toast.makeText(Connect4Activity.this, "Cannot move here!", Toast.LENGTH_SHORT).show();

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

    private boolean win() {
        int q;
        if (p1turn)
            q = 1;
        else
            q = 2;

        int count = 0;
        for (int i = 0; i < state.length; i++) {
            count = 0;
            int j = 0;
            while (state[i][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;
                if (j < 4)
                    j++;
                else
                    break;
            }
        }
        for (int i = 0; i < state.length; i++) {
            count = 0;
            int j = 1;
            while (state[i][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;
                if (j < 4)
                    j++;
                else
                    break;
            }
        }
        for (int i = 0; i < state.length; i++) {
            count = 0;
            int j = 0;
            while (state[j][i] == q) {
                count = count + 1;
                if (count == 4)
                    return true;
                if (j < 4)
                    j++;
                else
                    break;
            }
        }
        for (int i = 0; i < state.length; i++) {
            count = 0;
            int j = 1;
            while (state[j][i] == q) {
                count = count + 1;
                if (count == 4)
                    return true;
                if (j < 4)
                    j++;
                else
                    break;
            }
        }

        for (int i = 0; i < 2; i++) {
            count = 0;
            int j = 0;
            int k = i;
            while (state [k][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;

                j++;
                k++;

            }

        }

        for (int i = 0; i < 2; i++) {
            count = 0;
            int j = 1;
            int k = i;
            while (state [k][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;

                j++;
                k++;

            }

        }

        for (int i = 4; i > 2; i--) {
            count = 0;
            int j = 0;
            int k = i;
            while (state [k][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;

                j++;
                k--;

            }

        }

        for (int i = 4; i > 2; i--) {
            count = 0;
            int j = 1;
            int k = i;
            while (state [k][j] == q) {
                count = count + 1;
                if (count == 4)
                    return true;

                j++;
                k--;

            }

        }
        return false;



    }

    private boolean draw() {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    private void winCheck() {
        if (win()) {
            if (p1turn) {
                Log.i(jl, "Player 1 wins!");
                winner(1);
                Intent i = new Intent();
                i.putExtra(KEY_C4, 1);
                setResult(RESULT_OK, i);
                Log.d(jl, "C4 finished with submit - data saved");
                finish();
            }
            else {
                Log.i(jl, "Player 2 wins!");
                winner(2);
                Intent i = new Intent();
                i.putExtra(KEY_C4, 2);
                setResult(RESULT_OK, i);
                Log.d(jl, "C4 finished with submit - data saved");
                finish();
            }

        }
        else if (draw()) {
            Log.i(jl, "Draw!");
            Toast.makeText(Connect4Activity.this, "Draw!", Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.putExtra(KEY_C4, 0);
            setResult(RESULT_OK, i);
            Log.d(jl, "C4 finished with submit - data saved");
            finish();
        }
    }

    private void winner(int x) {
        if (x == 1) {
            if (intent.getStringExtra(P1_NAME_KEY) != null) {
                Toast.makeText(Connect4Activity.this, intent.getStringExtra(P1_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Connect4Activity.this, "Player 1 Wins!", Toast.LENGTH_LONG).show();
            }
        }
        if (x == 2) {
            if (intent.getStringExtra(P2_NAME_KEY) != null) {
                Toast.makeText(Connect4Activity.this, intent.getStringExtra(P2_NAME_KEY) + " Wins!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Connect4Activity.this, "Player 2 Wins!", Toast.LENGTH_LONG).show();
            }
        }
    }


}
