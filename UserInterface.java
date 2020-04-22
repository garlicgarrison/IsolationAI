import java.util.Scanner;

public class UserInterface {

    private String firstPlayer;
    private String secondPlayer;
    private String[] history = new String[62];
    private int currentMove;
    private boolean player1;


    public void play(){
        Board b = new Board();
        player1 = chooseFirstPlayer();
        b.setUpBoard(player1);
        currentMove = 0;
        while(!b.isGameOver())
        {
            System.out.println("\f");
            printBoard(b.board);
            String input = inputMove();
            checkMove(input, b);
            player1 = !player1;
        }
        if(b.winnerIsPlayer1){
            System.out.println("Player 1 is the Winner!");
        }
        else{
            System.out.println("Player 2 is the Winner!");
        }
    }

    public void checkMove(String input, Board b){
        int newPosition = stringToDescart(input);
        if(b.move(newPosition, player1)){
            history[currentMove] = input;
            currentMove++;
        } else {
            System.out.println("Move is not valid");
            checkMove(inputMove(), b);
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

    public String inputMove()
    {
        if(player1){
            System.out.println("Enter Player 1's move: ");
        }
        else System.out.println("Enter Player 2's move: ");
        Scanner sc = new Scanner(System.in);
        String move = sc.nextLine();
        if(move.length()!=2){
            System.out.println("Input invalid");
            inputMove();
        }
        System.out.println();
        return move;
    }

    public void printBoard(int[][] board)
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
            if(moveCounter+1<=currentMove) System.out.print("     " + (i+1) + ". " + history[moveCounter]);
            moveCounter++;
            if(moveCounter+1<=currentMove) System.out.print("          " + (i+1) + ". " + history[moveCounter]);
            moveCounter++;
            System.out.println();
        }
        System.out.println();
    }

}
