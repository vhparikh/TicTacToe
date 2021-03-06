/*
    created by Vatsal Parikh
    Date 1/31/2020
    this is a tictactoe for android devices it is human vs computer
 */
package com.example.vp.tictactoe;

//imports
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //setting up grid
    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting each grid box to a button
        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);
        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);
        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);

        //labeling the coordinates for each grid box
        for(int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                grid[x][y].setText(x + "," + y);
            }
        }

        //checking to see which button was clicked
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                grid[x][y].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
         Button b = (Button)v;
         //goes through grid if the button is empty will place X piece on the clicked button
         for (int x = 0; x < 3; x++){
             for (int y = 0; y < 3; y++){
                 if(b.equals(grid[x][y])){ //if it found the button player clicked then:
                     if(board[x][y] == BLANK) { //if the button clicked is empty then:
                         b.setText("X");
                         b.setEnabled(false);
                         board[x][y] = X_MOVE;
                         if(checkWin(X_MOVE)){ //if x won then:
                             Toast.makeText(this, "Player X won!", Toast.LENGTH_SHORT).show();
                             clearBoard();
                         }else if(checkTie()){ //if the game was a tie then:
                             Toast.makeText(this, "The game was a tie", Toast.LENGTH_SHORT).show();
                            clearBoard();
                         }
                         //calls the aiMove method
                         aiMove();
                     }
                 }
             }
         }

    }

    public void aiMove() {
        //try to win
        if(checkSingleBlank(O_MOVE)) {
            if(checkWin(O_MOVE)){ //if O won then:
                Toast.makeText(this, "Player O won!", Toast.LENGTH_SHORT).show();
                clearBoard(); //reset the board
            }else if(checkTie()){ //if the game was a tie then:
                Toast.makeText(this, "The game was a tie", Toast.LENGTH_SHORT).show();
                clearBoard(); //reset the board
            }
            return;
        }
        //try to block
        if (checkSingleBlank(X_MOVE)) {
            if(checkWin(O_MOVE)){ //if O won then:
                Toast.makeText(this, "Player O won!", Toast.LENGTH_SHORT).show();
                clearBoard(); //reset the board
            }else if(checkTie()){ //if the game was a tie then:
                Toast.makeText(this, "The game was a tie", Toast.LENGTH_SHORT).show();
                clearBoard(); //reset the board
            }
            return;
        }
        //play randomly
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(board[x][y] == BLANK){ //if the box is empty then:
                    list.add(x*10+y); //add button to array list
                }
            }
        }
        int choice = (int)(Math.random() * list.size()); //randomy picks a blank button
        board[list.get(choice) / 10][list.get(choice) % 10] = O_MOVE; //sets button to O_MOVE
        grid[list.get(choice) / 10][list.get(choice) % 10].setText("O"); //set button to O
        grid[list.get(choice) / 10][list.get(choice) % 10].setEnabled(false); //disable grid button

        if(checkWin(O_MOVE)){ //if O won then:
            Toast.makeText(this, "Player O won!", Toast.LENGTH_SHORT).show();
            clearBoard(); //reset the board
        }else if(checkTie()){ //if the game was a tie then:
            Toast.makeText(this, "The game was a tie", Toast.LENGTH_SHORT).show();
            clearBoard(); //reset the board
        }
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
                board[blankX][blankY] = O_MOVE;
                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
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
                board[blankX][blankY] = O_MOVE;
                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
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
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
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
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            return true;
        }

        return false;

    }

    //checks for a win by going through all possible wins
    public boolean checkWin(int player) {
        if(board[0][0] == player && board[0][1] == player && board[0][2] == player) {
            return true;
        }else if(board[1][0] == player && board[1][1] == player && board[1][2] == player) {
            return true;
        }else if(board[2][0] == player && board[2][1] == player && board[2][2] == player) {
            return true;
        }else if(board[0][0] == player && board[1][0] == player && board[2][0] == player) {
            return true;
        }else if(board[0][1] == player && board[1][1] == player && board[2][1] == player) {
            return true;
        }else if(board[0][2] == player && board[1][2] == player && board[2][2] == player) {
            return true;
        }else if(board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }else if(board[2][0] == player && board[1][1] == player && board[0][2] == player) {
            return true;
        }
        return false;
    }

    //checks for a tie
    public boolean checkTie() {

        //looks for a filled up board
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if(board[row][column] == BLANK) { //If button is blank then:
                    return false;
                }
            }
        }
        return true;
    }

    //resets the gameboard
    public void clearBoard() {

        //clears every box on grid and re enables the button
        for (int a = 0; a < board.length; a++) {
            for (int b = 0; b < board[0].length; b++) {
                board[a][b] = BLANK;
                grid[a][b].setText(a+ "," +b);
                grid[a][b].setEnabled(true);
            }
        }
    }

}
