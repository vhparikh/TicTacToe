package com.example.vp.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);
        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);
        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);

        for(int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                grid[x][y].setText(x + "," + y);
            }
        }

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                grid[x][y].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
         Button b = (Button)v;
         for (int x = 0; x < 3; x++){
             for (int y = 0; y < 3; y++){
                 if(b.equals(grid[x][y])){
                     if(board[x][y] == BLANK) {
                         b.setText("X");
                         b.setEnabled(false);
                         board[x][y] = X_MOVE;
                         //check win
                         aiMove();
                     }
                 }
             }
         }

    }

    public void aiMove() {
        //try to win
        if(checkSingleBlank(O_MOVE)) {
            return;
        }
        //try to block
        if (checkSingleBlank(O_MOVE)) {
            return;
        }
        //play randomly
    }

    public boolean checkSingleBlank(int player) {
        int playerCount = 0;
        int blankCount = 0;
        int blankX = 0;
        int blankY = 0;

        //check colums win
        for (int x = 0; x < 3; x++){
            playerCount = 0;
            blankCount = 0;
            blankX = 0;
            blankY = 0;
            for (int y = 0; y < 3; y++){
                if(board[x][y] == BLANK){
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player){
                    playerCount++;
                }
            }
            if(playerCount == 2 && blankCount == 1){
                board[blankX][blankY] = player;
                return true;
            }
        }

        //check row win
        for (int y = 0; y < 3; y++){
            playerCount = 0;
            blankCount = 0;
            blankX = 0;
            blankY = 0;
            for (int x = 0; x < 3; x++){
                if(board[x][y] == BLANK){
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player){
                    playerCount++;
                }
            }
            if(playerCount == 2 && blankCount == 1){
                board[blankX][blankY] = player;
                return true;
            }
        }

        //check top left to bottom right diagonal
        playerCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;
        if(board[0][0] == BLANK){
            blankCount++;
            blankX = 0;
            blankY = 0;
        }
        if (board[0][0] == player){
            playerCount++;
        }
        if(board[1][1] == BLANK){
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        if (board[1][1] == player){
            playerCount++;
        }
        if(board[2][2] == BLANK){
            blankCount++;
            blankX = 2;
            blankY = 2;
        }
        if (board[2][2] == player){
            playerCount++;
        }
        if(playerCount == 2 && blankCount == 1){
            board[blankX][blankY] = player;
            return true;
        }

        //check top right to bottom left
        playerCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;

        if(board[2][0] == BLANK){
            blankCount++;
            blankX = 2;
            blankY = 0;
        }
        if (board[2][0] == player){
            playerCount++;
        }
        if(board[1][1] == BLANK){
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        if (board[1][1] == player){
            playerCount++;
        }
        if(board[0][2] == BLANK){
            blankCount++;
            blankX = 0;
            blankY = 0;
        }
        if (board[0][2] == player){
            playerCount++;
        }
        if(playerCount == 2 && blankCount == 1){
            board[blankX][blankY] = player;
            return true;
        }

        return false;

    }

}
