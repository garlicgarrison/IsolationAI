import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
public class Board
{
    int[][] board = new int[8][8];
    String[] history = new String[62];
    int[] currentPos1;
    int[] currentPos2;
    int currentMove;
    HashSet<int[]> legalMoves1 = new HashSet<int[]>();
    HashSet<int[]> legalMoves2 = new HashSet<int[]>();
    //0 means empty, 1 means taken, 2 means the spot you are on, 3 is the 
    //spot the opponent is on
    boolean winner;// true is player 1
    public Board()
    {
        board[0][0] = 2;
        board[7][7] = 3;
        currentPos1 = new int[] {0, 0};
        currentPos2 = new int[] {7, 7};
        currentMove = 0;
    }
    
    
    public void play(boolean computerFirst)
    {
        if (computerFirst)
        {
            
        }
    }
    
    public boolean gameOver()
    {
        
    }
    
    public boolean pieceStuck(boolean player1)
    {
        
    }
    
    public void move(String inputPosition, boolean player1)
    {
        if (!moveIsValid(inputPosition, player1))
        {
            System.out.println("Move is not valid");
        }
        else
        {
            int[] newPosition = stringToDescart(inputPosition);
            movePiece(newPosition, player1);
        }
        currentMove++;
    }
    
    public String inputMove(boolean player1)
    {
        System.out.print("Enter opponent's move: ");
        Scanner sc = new Scanner(System.in);
        String move = sc.nextLine();
        System.out.println();
        return move;
    }
    
    
    public void movePiece(int[] newPosition, boolean player1)
    {
        if (player1)
        {
            board[currentPos1[0]][currentPos2[1]] = 1;
            board[newPosition[0]][newPosition[1]] = 2;
                    //moves piece to new position
            currentPos1[0] = newPosition[0]; //record new position
            currentPos1[1] = newPosition[1];
        }
        else
        {
            board[currentPos2[0]][currentPos2[1]] = 1;
            board[newPosition[0]][newPosition[1]] = 3;
                    //moves piece to new position
            currentPos2[0] = newPosition[0]; //record new position
            currentPos2[1] = newPosition[1];
        }
    }
   
    //1 is player 1, 0 is player 2
    //tells you if move is valid
    public boolean moveIsValid(String inputPosition, boolean player1)
    {
        int[] inputCoordinates = stringToDescart(inputPosition);
        int[] currentCoordinates;
        
    }
    
    public void getLegalMoves(int[] position, boolean player1)
    {
        int row = position[0];
        int col = position[1];
        int counterRow = row - 1;
        int counterCol = col - 1;
        while (counterRow >= 0 && board[counterRow][counterCol] == 0 )
        {
            
        }
        
    }
    //tells you the value of the position on board
    public int positionValue(int[] input)
    {
        int row = input[0];
        int col = input[1];
        return board[row][col];
    }
    
    //turns string into cartesian coordinates in a array of size 2
    public static int[] stringToDescart(String position)
    {
        String columnStr = position.substring(1); 
        String rowStr = position.substring(0,1);
        int column = Integer.parseInt(columnStr) - 1;
        char r = rowStr.charAt(0);
        int row = r - 65;
        int[] coordinates = {row, column};
        return coordinates;
    }
    
    public void printBoard()
    {
        System.out.print( "  1 2 3 4 5 6 7 8\n");
        char letter;
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
            System.out.println();
        }
        System.out.println();
    }
    
    
    
    

    
}
