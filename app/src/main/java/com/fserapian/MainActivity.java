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

    State[] gameState = {State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameOver = false;
    boolean macsTurn = true;

    public void fadeIn(View view) {

        ImageView image = (ImageView) view;

        int imageTag = Integer.parseInt(image.getTag().toString());

        if (gameState[imageTag] == State.EMPTY && !gameOver) {

            if (macsTurn) { // Mac's turn
                image.setImageResource(R.drawable.apple_logo);

                gameState[imageTag] = State.MAC;

                macsTurn = false;

            } else { // Windows' turn
                image.setImageResource(R.drawable.windows_logo);

                gameState[imageTag] = State.WINDOWS;

                macsTurn = true;
            }

            // Fade in the image
            image.animate().alpha(1).setDuration(400);

            for (int[] position : winningPositions) {

                if (gameState[position[0]] == gameState[position[1]] && gameState[position[1]] == gameState[position[2]] && gameState[position[0]] != State.EMPTY) {

                    gameOver = true;
                    buttonPlayAgain.animate().alpha(1).setDuration(2000);

                    // Determine the winner
                    if (gameState[position[0]] == State.MAC) {

                        textViewWinner.setText("Mac Wins!");
                        textViewWinner.animate().alpha(1).setDuration(200);
                    } else {
                        textViewWinner.setText("Windows Wins!");
                        textViewWinner.animate().alpha(1).setDuration(1000);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {

        textViewWinner.animate().alpha(0).setDuration(200);
        buttonPlayAgain.animate().alpha(0).setDuration(200);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView image = (ImageView) gridLayout.getChildAt(i);

            image.animate().alpha(0).setDuration(500); // Fade out all children
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = State.EMPTY;
        }

        macsTurn = true;
        gameOver = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Custom Title
        setTitle("Mac vs Windows");

        // Retrieve the views
        textViewWinner = findViewById(R.id.textViewWinner);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        gridLayout = findViewById(R.id.gridLayout);
    }
}
