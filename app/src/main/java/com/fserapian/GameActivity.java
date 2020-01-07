package com.fserapian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class GameActivity extends AppCompatActivity {
    private TextInputLayout inputLayoutPlayerX, inputLayoutPlayerO;
    private GridLayout gridLayout;
    private TextView lblScoreX, lblScoreO, lblWinner;

    private int xScore = 0, oScore = 0;

    String playerXName, playerOName;

    private enum State {
        X,
        O,
        EMPTY
    }

    private State[] gameState = {State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY};

    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private int roundsCount = 0;
    private boolean gameOver = false;
    private boolean xTurn = true;

    public void fadeIn(View view) {

        ImageView image = (ImageView) view;

        int imageTag = Integer.parseInt(image.getTag().toString());

        if (gameState[imageTag] == State.EMPTY && !gameOver) {

            if (xTurn) { // X's turn
                roundsCount++;
                image.setImageResource(R.drawable.x_mark);
                gameState[imageTag] = State.X;
                xTurn = false;

            } else { // O's turn
                roundsCount++;
                image.setImageResource(R.drawable.o_mark);
                gameState[imageTag] = State.O;
                xTurn = true;
            }

            // Fade in the image
            image.animate().alpha(1).setDuration(400);

            if (roundsCount == 9
                    && (gameState[0] != gameState[1] || gameState[1] != gameState[2])
                    && (gameState[3] != gameState[4] || gameState[4] != gameState[5])
                    && (gameState[6] != gameState[7] || gameState[7] != gameState[8])
                    && (gameState[0] != gameState[3] || gameState[3] != gameState[6])
                    && (gameState[1] != gameState[4] || gameState[4] != gameState[7])
                    && (gameState[2] != gameState[5] || gameState[5] != gameState[8])
                    && (gameState[0] != gameState[4] || gameState[4] != gameState[8])
                    && (gameState[2] != gameState[4] || gameState[4] != gameState[6])) {
                gameOver = true;
                lblWinner.setText("Égalité ⚖️");
                lblWinner.animate().alpha(1).setDuration(1000);
            } else {
                for (int[] position : winningPositions) {

                    if (gameState[position[0]] == gameState[position[1]] && gameState[position[1]] == gameState[position[2]] && gameState[position[0]] != State.EMPTY) {

                        // Determine the winner
                        if (gameState[position[0]] == State.X) {
                            gameOver = true;
                            xScore++;
                            lblScoreX.setText(String.valueOf(xScore));
                            if (playerXName.isEmpty()) {
                                lblWinner.setText(inputLayoutPlayerX.getHelperText() + " gagne 👏");
                            } else {
                                lblWinner.setText(playerXName + " gagne 👏");
                            }

                        } else {
                            gameOver = true;
                            oScore++;
                            lblScoreO.setText(String.valueOf(oScore));
                            if (playerOName.isEmpty()) {
                                lblWinner.setText(inputLayoutPlayerO.getHelperText() + " gagne 👏");
                            } else {
                                lblWinner.setText(playerOName + " gagne 👏");
                            }
                        }

                        lblWinner.animate().alpha(1).setDuration(1000);
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Retrieve the views
        findViews();

        readPlayerNames();
    }

    private void findViews() {
        inputLayoutPlayerX = findViewById(R.id.inputLayoutPlayerX);
        inputLayoutPlayerO = findViewById(R.id.inputLayoutPlayerO);
        gridLayout = findViewById(R.id.gridLayout);
        lblScoreX = findViewById(R.id.lblScoreX);
        lblScoreO = findViewById(R.id.lblScoreO);
        lblWinner = findViewById(R.id.lblWinner);
    }

    private void readPlayerNames() {
        Intent intent = getIntent();

        playerXName = intent.getStringExtra("playerXName");
        playerOName = intent.getStringExtra("playerOName");

        if (!playerXName.isEmpty()) {
            inputLayoutPlayerX.setHelperText(playerXName);
        }

        if (!playerOName.isEmpty()) {
            inputLayoutPlayerO.setHelperText(playerOName);
        }
    }

    //    Reset the game
    public void playAgain(View view) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) { // Fade out all children
            ImageView image = (ImageView) gridLayout.getChildAt(i);

            image.animate().alpha(0).setDuration(500);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = State.EMPTY;
        }

        lblWinner.animate().alpha(0).setDuration(1000);

        roundsCount = 0;
        xTurn = true;
        gameOver = false;
    }

    public void goToMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void goToSetup(View view) {
        Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
        startActivity(intent);
    }
}
