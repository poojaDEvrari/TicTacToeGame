package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int player; // 0 for Player X, 1 for Player O
    private Button[][] buttons = new Button[3][3]; // 3x3 grid
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeGame();
    }

    private void initializeGame() {
        // Initialize buttons
        buttons[0][0] = findViewById(R.id.button0);
        buttons[0][1] = findViewById(R.id.button1);
        buttons[0][2] = findViewById(R.id.button2);
        buttons[1][0] = findViewById(R.id.button3);
        buttons[1][1] = findViewById(R.id.button4);
        buttons[1][2] = findViewById(R.id.button5);
        buttons[2][0] = findViewById(R.id.button6);
        buttons[2][1] = findViewById(R.id.button7);
        buttons[2][2] = findViewById(R.id.button8);

        statusTextView = findViewById(R.id.statusTextView);
        resetGame(null);
    }

    public void playerTap(View view) {
        Button button = (Button) view;
        if (!button.getText().toString().equals("")) return; // Button already tapped

        button.setText(player == 0 ? "X" : "O"); // Set player mark
        player = 1 - player; // Switch player
        statusTextView.setText(player == 0 ? "Player X's Turn" : "Player O's Turn");
        checkForWinner();
    }

    public void resetGame(View view) {
        player = 0; // Reset player
        statusTextView.setText("Player X's Turn"); // Reset status text
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText(""); // Clear button text
                button.setEnabled(true); // Enable buttons
            }
        }
    }

    private void checkForWinner() {
        // Check for winner
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return; // Return on winner found
            }
        }
        // Diagonal checks
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) {
            return;
        }
        // Check for draw
        if (isDraw()) {
            statusTextView.setText("It's a Draw!");
        }
    }

    private boolean checkLine(Button b1, Button b2, Button b3) {
        if (!b1.getText().toString().equals("") &&
                b1.getText().toString().equals(b2.getText().toString()) &&
                b1.getText().toString().equals(b3.getText().toString())) {
            announceWinner(b1.getText().toString());
            return true;
        }
        return false;
    }

    private boolean isDraw() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().toString().equals("")) return false; // There is at least one empty button
            }
        }
        return true; // All buttons are filled
    }

    private void announceWinner(String winner) {
        statusTextView.setText("Player " + winner + " Wins!");
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setEnabled(false); // Disable all buttons
            }
        }
    }
}
