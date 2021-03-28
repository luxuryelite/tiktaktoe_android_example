package itescam.isc.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[][] board = new int[3][3];
    private boolean winner;
    private boolean turn;
    private RadioGroup displayTurn;
    // 0 - vacio, 1 - x, 2 - o

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winner = false;
        turn = false;
        displayTurn = (RadioGroup)findViewById(R.id.displayTurn);
        displayTurn.setEnabled(false);
        RadioButton x = (RadioButton) findViewById(R.id.turnx);
        RadioButton o = (RadioButton) findViewById(R.id.turno);
        x.setEnabled(false);
        o.setEnabled(false);
        resetBoard();
    }

    private void resetBoard () {
        for (int i=0; i < 3; i++)
            for (int a=0; a < 3; a++)
                board[i][a] = 0;

        winner = false;
        updateDisplayTurn();
    }

    private void updateDisplayTurn () {
        // int selectedId = displayTurn.getCheckedRadioButtonId();
        if (turn)
            displayTurn.check(R.id.turnx);
        else
            displayTurn.check(R.id.turno);
    }

    public void rBoard (View v) {
        resetBoard();

        ImageButton b1 = (ImageButton)findViewById(R.id.b1);
        ImageButton b2 = (ImageButton)findViewById(R.id.b2);
        ImageButton b3 = (ImageButton)findViewById(R.id.b3);
        ImageButton b4 = (ImageButton)findViewById(R.id.b4);
        ImageButton b5 = (ImageButton)findViewById(R.id.b5);
        ImageButton b6 = (ImageButton)findViewById(R.id.b6);
        ImageButton b7 = (ImageButton)findViewById(R.id.b7);
        ImageButton b8 = (ImageButton)findViewById(R.id.b8);
        ImageButton b9 = (ImageButton)findViewById(R.id.b9);

        b1.setImageResource(R.mipmap.squere);
        b2.setImageResource(R.mipmap.squere);
        b3.setImageResource(R.mipmap.squere);
        b4.setImageResource(R.mipmap.squere);
        b5.setImageResource(R.mipmap.squere);
        b6.setImageResource(R.mipmap.squere);
        b7.setImageResource(R.mipmap.squere);
        b8.setImageResource(R.mipmap.squere);
        b9.setImageResource(R.mipmap.squere);
    }

    private void setMove (ImageButton buttonSelected) {
        int boardValue = (turn)? 1 : 2;
        int x = 0;
        int y = 0;

        switch (buttonSelected.getId()) {
            case R.id.b1:
                x = 0;
                y = 0;
                break;
            case R.id.b2:
                x = 0;
                y = 1;
                break;
            case R.id.b3:
                x = 0;
                y = 2;
                break;
            case R.id.b4:
                x = 1;
                y = 0;
                break;
            case R.id.b5:
                x = 1;
                y = 1;
                break;
            case R.id.b6:
                x = 1;
                y = 2;
                break;
            case R.id.b7:
                x = 2;
                y = 0;
                break;
            case R.id.b8:
                x = 2;
                y = 1;
                break;
            case R.id.b9:
                x = 2;
                y = 2;
                break;
        }
        board[x][y] = boardValue;
    }

    private void analyzeBoard () {
        int a = -1;
        int b = -2;
        int c = -3;

        // Comprueba todas filas
        if (!winner)
            for (int i = 0; i < 3; i++) {
                a = board[i][0];
                b = board[i][1];
                c = board[i][2];

                if (a == b && a == c && a != 0) {
                    winner = true;
                    i = 4;
                }
            }

        // Comprueba todas las columnas
        if (!winner)
            for (int i = 0; i < 3; i++) {
                a = board[0][i];
                b = board[1][i];
                c = board[2][i];

                if (a == b && a == c && a != 0) {
                    winner = true;
                    i = 4;
                }
            }

        // Comprobar diagonal derecha
        if (!winner) {
            a = board[0][0];
            b = board[1][1];
            c = board[2][2];

            if (a == b && a == c  && a != 0) {
                winner = true;
            }
        }

        // Comprobar diagonal izquierda
        if (!winner) {
            a = board[0][2];
            b = board[1][1];
            c = board[2][0];

            if (a == b && a == c && a != 0) {
                winner = true;
            }
        }
    }


    public void buttonClick (View v) {
        ImageButton buttonSelected = (ImageButton)v;

        Bitmap bmpButtonSelected = ((BitmapDrawable)buttonSelected.getDrawable()).getBitmap();
        Bitmap bmpButtonSquare = ((BitmapDrawable)getResources().getDrawable(R.mipmap.squere)).getBitmap();

        if (bmpButtonSelected == bmpButtonSquare && !winner) {
            if (turn){
                buttonSelected.setImageResource(R.mipmap.cruz);
            }
            else {
                buttonSelected.setImageResource(R.mipmap.cero);
            }
            setMove(buttonSelected);
            analyzeBoard();
        }
        if(winner){
            AlertDialog.Builder message = new AlertDialog.Builder(this);
            message.setTitle("Ganador");
            message.setMessage((turn)? "X" : "O");
            message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            message.show();
        } else {
            turn = !turn;
            updateDisplayTurn();
        }
    }
}