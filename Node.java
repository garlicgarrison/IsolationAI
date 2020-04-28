import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Node {

    public int[][] board;
    boolean computerIsPlayer;
    int opponentPosition;
    int position;
    double value;
    public int depth;

    public Node(int[][] board, boolean computerIsPlayer, int depth){
        this.board = board;
        this.computerIsPlayer = computerIsPlayer;
        for(int i=0; i<board.length; i++){
            for (int j=0; j<board.length; j++){
                if(board[i][j]==2){
                    if(!computerIsPlayer) position = (i+1)*10 + j+1;
                    else opponentPosition = (i+1)*10 + j+1;
                }
                if(board[i][j]==3){
                    if(computerIsPlayer) position = (i+1)*10 + j+1;
                    else opponentPosition = (i+1)*10 + j+1;
                }
            }
        }
        value = heur();
        this.depth = depth;
    }

    public double heur(){
       /* int[][] weight = {{0, 1, 2, 3, 3, 2, 1, 0},
            {1, 2, 3, 4, 4, 3, 2, 1},
            {2, 3, 4, 5, 5, 4, 3, 2},
            {3, 4, 5, 6, 6, 5, 4, 3},
            {3, 4, 5, 6, 6, 5, 4, 3},
            {2, 3, 4, 5, 5, 4, 3, 2},
            {1, 2, 3, 4, 4, 3, 2, 1},
            {0, 1, 2, 3, 3, 2, 1, 0}}; */
        int row = position/10-1;
        int col = position%10-1;
        int orow = opponentPosition/10-1;
        int ocol = opponentPosition%10-1;
        if(computerIsPlayer){
            return ((getLegalMoves(position).size() - 1.5*getLegalMoves(opponentPosition).size() ) );
            //* 0.1
            //+ ((weight[row][col] - weight[orow][ocol]) / 6.0) * 0.5;
        }
        else{
            return ((getLegalMoves(opponentPosition).size() - 1.5*getLegalMoves(position).size() ) );
            //* 0.1
            //+ ((weight[row][col] - weight[orow][ocol]) / 6.0) * 0.5;
        }
    }

    public PriorityQueue<Node> getChildren(){
        PriorityQueue<Node> children = new PriorityQueue<>(new NodeComparator());
        HashSet<Integer> legalMoves = getLegalMoves(position);
        for (int newPosition: legalMoves) {
            int[][] newBoard = moveTo(newPosition);
            children.add(new Node(newBoard, !computerIsPlayer, depth+1));
        }
        return children;
    }

    public int[][] moveTo(int x){
        int[][] newBoard = copy(board);
        newBoard[position/10 - 1][position%10 -1] = 1;
        newBoard[x/10 -1][x%10 -1] = computerIsPlayer ? 3 : 2;
        return newBoard;

    }

    public HashSet<Integer> getLegalMoves(int position){
        if(position<10){
            for(int i=0; i<board.length; i++){
                System.out.println(Arrays.toString(board[i]));
            }
            throw new ArrayIndexOutOfBoundsException();
        }
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

    private int[][] copy(int[][] arr){
        int[][] copy = new int[arr.length][arr.length];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

}
