import java.util.Scanner;
public class main
{
    public static void main(String[] args)
    {
        Board b = new Board();
        boolean computerFirst = chooseFirstPlayer(); 
        
            System.out.println("\f");
            b.printBoard();
            String newPosition = b.inputMove(computerFirst);
            b.move(newPosition, false);
            b.printBoard();
        
    }

    private static boolean chooseFirstPlayer()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Who goes first, C for Computer, O for opponent: ");
        String firstPlayer = sc.nextLine();
        boolean entered = false;
        while (entered == false)
        {
            if (firstPlayer.equals("C"))
            {
                return true;
            }
            else if (firstPlayer.equals("O"))
            {
                return false;
            }
            else
            {
                System.out.println("Entry Invalid. Try again");
            }
        }
        return false;
    }
}
