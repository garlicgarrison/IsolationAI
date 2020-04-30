package IsolationAI;

import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
public class Board
{
    public int[][] board = new int[8][8];
    private int currentPos1;
    private int currentPos2;
    private HashSet<Integer> legalMoves1;
    private HashSet<Integer> legalMoves2;
    //0 means empty, 1 means taken, 2 means the spot player1 is on, 3 is the
    //spot the computer is on
    public boolean winnerIsPlayer1;// true if player1 won, false if player2 won, null if game is not over

    public HashSet<Integer> getLegalMoves1(int[][] board){
        return legalMoves1;
    }

    public HashSet<Integer> getLegalMoves2(){
        return legalMoves2;
    }

    public void setUpBoard(boolean player1){
        if(player1){
            board[0][0] = 2;
            board[7][7] = 3;
            currentPos1 = 11;
            currentPos2 = 88;
        }
        else {
            board[0][0] = 3;
            board[7][7] = 2;
            currentPos1 = 88;
            currentPos2 = 11;
        }
        legalMoves1 = getLegalMoves(currentPos1);
        legalMoves2 = getLegalMoves(currentPos2);
    }


    public boolean isGameOver()
    {
        try{
            if(legalMoves1.isEmpty()) {
                winnerIsPlayer1 = true;
                return true;
            }
            if(legalMoves2.isEmpty()){
                winnerIsPlayer1 = false;
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }

   //ok
    public boolean move(int newPosition, boolean player1)
    {
        if(!moveIsValid(newPosition, player1))
            return false;
        movePiece(newPosition, player1);
        if(player1) legalMoves2 = getLegalMoves(currentPos2);
        else legalMoves1 = getLegalMoves(currentPos1);
        return true;
    }
//ok
    public void movePiece(int newPosition, boolean player1)
    {
        if (player1)
        {
            //moves piece to new position
            board[currentPos1/10 - 1][currentPos1%10 -1] = 1;
            board[newPosition/10 -1][newPosition%10 -1] = 2;
            //record new position
            currentPos1 = newPosition;
        }
        else
        {
            //moves piece to new position
            board[currentPos2/10 -1][currentPos2%10 -1] = 1;
            board[newPosition/10 -1][newPosition%10 -1] = 3;
            //record new position
            currentPos2 = newPosition;
        }
    }

    public HashSet<Integer> getLegalMoves(int position){
        HashSet<Integer> successors = new HashSet<>();
        int row = position/10 -1;
        int col = position%10 -1;
        for(int i=col+1; i<board.length; i++) {
            if (board[row][i] != 0) break;
            successors.add((row+1)*10 + (i+1));
        }
        for(int i=col-1; i>=0; i--){
            if(board[row][i] !=0)break;
            successors.add((row+1)*10 + (i+1));
        }
        for(int i=row+1; i<board.length; i++){
            if(board[i][col] != 0) break;
            successors.add((i+1)*10 + (col+1));
        }
        for(int i=row-1; i>=0; i--){
            if(board[i][col] !=0) break;
            successors.add((i+1)*10 + (col+1));
        }
        int x = row+1;
        int y= col+1;
        while(x<board.length && y<board.length && board[x][y]==0){
            successors.add((x+1)*10 + (y+1));
            x++; y++;
        }
        x = row-1;
        y= col+1;
        while(x>=0 && y<board.length && board[x][y] ==0){
            successors.add((x+1)*10 + (y+1));
            x--; y++;
        }
        x = row+1;
        y = col-1;
        while(x<board.length && y>=0 && board[x][y]==0){
            successors.add((x+1)*10 + (y+1));
            x++; y--;
        }
        x = row-1;
        y = col-1;
        while(x>=0 && y>=0 && board[x][y]==0){
            successors.add((x+1)*10 + (y+1));
            x--; y--;
        }
        return successors;
    }

    //1 is player 1, 0 is player 2
    //tells you if move is valid
    //ok
    public boolean moveIsValid(int inputPosition, boolean player1) {
        int currentCoordinates;
        currentCoordinates = player1 ? currentPos1 : currentPos2;

        if (inputPosition == currentCoordinates) {
            return false;
        }
        if(player1) return legalMoves1.contains(inputPosition);
        else return legalMoves2.contains(inputPosition);
    }

}
