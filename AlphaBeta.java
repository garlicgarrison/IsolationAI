
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Timer;

public class AlphaBeta {
    double startTime;
    int maxDepth = 62;
    int timeLimit = 20000;
    int lastDepth = 0;

    public int goDeeper(int[][] state)
    {
        Node firstNode = new Node(state, true, 1);
        int bestMove = 0;
        startTime = System.currentTimeMillis();
        for (int depth = 1; depth < maxDepth; depth++) {
            if (System.currentTimeMillis() - startTime >= timeLimit) break;
            double val = Double.NEGATIVE_INFINITY;
            for (Node child:firstNode.getChildren()) {
                Node newNode = alphaBetaSearch(child, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth);
                double tempVal = newNode.heur();
                if(tempVal > val){
                    val = tempVal;
                    bestMove = child.opponentPosition;
                }
            }
        }
        System.out.println(lastDepth);
        return bestMove;
    }

    //returns the position to which the player should move
    public Node alphaBetaSearch(Node state, double alpha, double beta, int depth) {
        lastDepth = Math.max(state.depth, lastDepth);
        if(terminalTest(state) ||
                System.currentTimeMillis() - startTime >= timeLimit ||
                depth<=0)
        {
            return state;
        }
        Node nextStep;
        if(state.computerIsPlayer){
            nextStep = maxValue(state,alpha, beta, depth);
        }
        else{
            nextStep = minValue(state, alpha, beta, depth);
        }
        return nextStep; //might have to be changed to position
    }

    public Node maxValue(Node state, double a, double b, int depth){
        lastDepth = Math.max(state.depth, lastDepth);
        double v = Double.NEGATIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node min = alphaBetaSearch(child, a, b, depth);
            double tempVal = min.heur();
            if(v < tempVal){
                v = tempVal;
                next = min;
            }
            if(v>=b) return next;
            a = Math.max(a, v);
        }
        return next;
    }

    public Node minValue(Node state, double a, double b, int depth){
        lastDepth = Math.max(state.depth, lastDepth);
        double v = Double.POSITIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node max = alphaBetaSearch(child, a, b, depth-1);
            double tempMax = max.heur();
            if(v > tempMax){
                v = tempMax;
                next = max;
            }
            if(v<=a) return next;
            b = Math.min(b, v);
        }
        return next;
    }

    public boolean terminalTest(Node state){
        if(state.getLegalMoves(state.position).isEmpty() || state.getLegalMoves(state.opponentPosition).isEmpty()){
            return true;
        }
        return false;
    }

}
