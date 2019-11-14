package com.fserapian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewWinner;
    private Button buttonPlayAgain;
    private GridLayout gridLayout;

    private enum State {
        MAC,
        WINDOWS,
        EMPTY
    }

    private State[] gameState = {State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY};

    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;
    private int roundsCount = 0;
    private boolean macsTurn = true;

    public void fadeIn(View view) {

        ImageView image = (ImageView) view;

        int imageTag = Integer.parseInt(image.getTag().toString());

        if (gameState[imageTag] == State.EMPTY && !gameOver) {

            if (macsTurn) { // Mac's turn
                roundsCount++;
                image.setImageResource(R.drawable.apple_logo);
                gameState[imageTag] = State.MAC;
                macsTurn = false;

            } else { // Windows' turn
                roundsCount++;
                image.setImageResource(R.drawable.windows_logo);
                gameState[imageTag] = State.WINDOWS;
                macsTurn = true;
            }

            // Fade in the image
            image.animate().alpha(1).setDuration(400);

            if (roundsCount == 9) {
                gameOver("Draw!");
            } else {
                for (int[] position : winningPositions) {

                    if (gameState[position[0]] == gameState[position[1]] && gameState[position[1]] == gameState[position[2]] && gameState[position[0]] != State.EMPTY) {

                        // Determine the winner
                        if (gameState[position[0]] == State.MAC) {
                            gameOver("Mac Wins!");
                        } else {
                            gameOver("Windows Wins!");
                        }
                    }
                }
            }
        }
    }

    private void gameOver(String msg) {
        gameOver = true;
        // Enable the button
        buttonPlayAgain.setEnabled(true);
        // Fade in the button
        buttonPlayAgain.animate().alpha(1).setDuration(3000);

        textViewWinner.setText(msg);
        textViewWinner.animate().alpha(1).setDuration(500);
    }

    public void playAgain(View view) {

        textViewWinner.animate().alpha(0).setDuration(200);
        buttonPlayAgain.animate().alpha(0).setDuration(200);

        for (int i = 0; i < gridLayout.getChildCount(); i++) { // Fade out all children
            ImageView image = (ImageView) gridLayout.getChildAt(i);

            image.animate().alpha(0).setDuration(500);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = State.EMPTY;
        }

        roundsCount = 0;
        buttonPlayAgain.setEnabled(false);
        macsTurn = true;
        gameOver = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the views
        textViewWinner = findViewById(R.id.textViewWinner);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        gridLayout = findViewById(R.id.gridLayout);

        // Disable the button
        buttonPlayAgain.setEnabled(false);

        // Custom Title
        setTitle("Mac vs Windows");
    }
}
