import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
public class Board
{
    int[][] board = new int[8][8];
    String[] history = new String[62];
    String firstPlayer;
    String secondPlayer;
    int currentPos1;
    int currentPos2;
    int currentMove;
    HashSet<Integer> legalMoves1;
    HashSet<Integer> legalMoves2;
    //0 means empty, 1 means taken, 2 means the spot you are on, 3 is the 
    //spot the opponent is on
    boolean winner;// true is player 1
    public Board()
    {

    }
    public void play()
    {
        boolean player1 = chooseFirstPlayer();
        if(player1) {
            board[0][0] = 2;
            board[7][7] = 3;
            currentPos1 = 11;
            currentPos2 = 88;
        } else{
            board[0][0] = 3;
            board[7][7] = 2;
            currentPos1 = 88;
            currentPos2 = 11;
        }
        currentMove = 0;
        legalMoves1 = getLegalMoves(currentPos1);
        legalMoves2 = getLegalMoves(currentPos2);
        while(!gameOver())
        {
            System.out.println("\f");
            printBoard();
            String newPosition = inputMove(player1);
            move(newPosition, player1);
            player1 = !player1;
        }
        if(legalMoves2.isEmpty()){
            System.out.println("Player 1 is the Winner!");
        }
        else{
            System.out.println("Player 2 is the Winner!");
        }
    }

    private boolean chooseFirstPlayer()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Who goes first? 1 for Player 1, 2 for Player 2: ");
        String player = sc.nextLine();
            if (player.equals("1")){
                firstPlayer = "Player 1";
                secondPlayer = "Player 2";
                return true;
            }
            else if (player.equals("2")) {
                firstPlayer = "Player 2";
                secondPlayer = "Player 1";
                return false;
            }
            else
            {
                System.out.println("Entry Invalid. Try again");
                return chooseFirstPlayer();
            }
    }

    public boolean gameOver()
    {
        try{
            return legalMoves1.isEmpty() || legalMoves2.isEmpty();
        }
        catch (Exception e) {
            return false;
        }
    }

   //ok
    public void move(String inputPosition, boolean player1)
    {
        int newPosition = stringToDescart(inputPosition);
        if(!moveIsValid(newPosition, player1))
        {
            System.out.println("Move is not valid");
            move(inputMove(player1), player1);
        }
        else {
            history[currentMove] = inputPosition;
            movePiece(newPosition, player1);
            if(player1) legalMoves2 = getLegalMoves(currentPos2);
            else legalMoves1 = getLegalMoves(currentPos1);
            currentMove++;
        }
    }
//ok
    public String inputMove(boolean player1)
    {
        if(player1){
            System.out.println("Enter Player 1's move: ");
        }
        else System.out.println("Enter Player 2's move: ");
        Scanner sc = new Scanner(System.in);
        String move = sc.nextLine();
        if(move.length()!=2){
            System.out.println("Input invalid");
            inputMove(player1);
        }
        System.out.println();
        return move;
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
            board[currentPos2/10 -1][currentPos2%10 -1] = 1;
            board[newPosition/10 -1][newPosition%10 -1] = 3;
            //moves piece to new position
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

    //tells you the value of the position on board
    public int positionValue(int[] input)
    {
        int row = input[0];
        int col = input[1];
        return board[row][col];
    }

    //turns string into cartesian coordinates in a array of size 2
    public static int stringToDescart(String position)
    {
        String columnStr = position.substring(1); 
        String rowStr = position.substring(0,1);
        int column = Integer.parseInt(columnStr) - 1;
        char r = rowStr.charAt(0);
        int row = r - 65;
        int coordinates = (row+1)*10 + column+1;
        return coordinates;
    }

    public void printBoard()
    {
        System.out.print( "  1 2 3 4 5 6 7 8       "+ firstPlayer + " vs. "+secondPlayer +"\n");
        char letter;
        int moveCounter = 0;
        for (int i = 0; i < board.length; i++)
        {
            if (i == 0)
            {
                letter = 65;
            }
            else
            {
                letter = (char)(i + 65);
            }
            System.out.print(letter + " ");
            for (int j = 0; j < board.length; j++)
            {
                switch(board[i][j])
                {
                    case 0:
                    System.out.print("- ");
                    break;
                    case 1:
                    System.out.print("# ");
                    break;
                    case 2:
                    System.out.print("X ");
                    break;
                    case 3:
                    System.out.print("O ");
                    break;
                }

            }
            if(moveCounter+1<=currentMove) System.out.print("     " + (moveCounter+1) + ". " + history[moveCounter]);
            moveCounter++;
            if(moveCounter+1<=currentMove) System.out.print("          " + (moveCounter+1) + ". " + history[moveCounter]);
            moveCounter++;
            System.out.println();
        }
        System.out.println();
    }

    

}
