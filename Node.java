import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Node {

    public int[][] board;
    public Node parent;
    boolean computerIsPlayer;
    int opponentPosition;
    int position;
    int value;

    public Node(int[][] board, boolean computerIsPlayer){
        this.board = board;
        for(int i=0; i<board.length; i++){
            for (int j=0; j<board.length; j++){
                if(board[i][j]==2){
                    if(computerIsPlayer) position = (i+1)*10 + j+1;
                    else opponentPosition = (i+1)*10 + j+1;
                }
                if(board[i][j]==3){
                    if(!computerIsPlayer) position = (i+1)*10 + j+1;
                    else opponentPosition = (i+1)*10 + j+1;
                }
            }
        }
        value = heur();
    }

    public int heur(){
        return getLegalMoves(position).size() - getLegalMoves(opponentPosition).size();
    }

    public PriorityQueue<Node> getChildren(){
        PriorityQueue<Node> children = new PriorityQueue<>();
        HashSet<Integer> legalMoves = getLegalMoves(position);
        for (int newPosition: legalMoves) {
            children.add(new Node(moveTo(newPosition), computerIsPlayer));
        }
        return children;
    }

    public int[][] moveTo(int x){
        int[][] newBoard = Arrays.copyOf(board, board.length);
        newBoard[position/10 - 1][position%10 -1] = 1;
        newBoard[x/10 -1][x%10 -1] = 2;
        return newBoard;

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

}
