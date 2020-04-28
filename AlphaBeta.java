import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Timer;

public class AlphaBeta {
    public Node nextStep;
    double startTime;

    //returns the position to which the player should move
    public int alphaBetaSearch(int[][] state){
        Node firstNode = new Node(state, true, 1);
        startTime = System.nanoTime();
//        for(int distance = 0; distance<MAX_DISTANCE)
//        {
//            nextStep = maxValue(firstNode, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, distance);
//        }
        nextStep = maxValue(firstNode, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        return nextStep.opponentPosition;
    }
    long timeLimit = (long)Math.pow(10,10);

    public Node maxValue(Node state, double a, double b, int distance){
        distance++;
        if(terminalTest(state) || System.nanoTime() - startTime >= timeLimit){
            return state;
        }
        double v = Double.NEGATIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node min = minValue(child, a, b, distance);
            if(v < min.value){
                v = min.value;
                next = min;
            }
            if(v>=b) return next;
            a = Math.max(a, v);
        }
        return next;
    }

    public Node minValue(Node state, double a, double b, int distance){
        distance++;
        if(terminalTest(state) || System.nanoTime() - startTime >= timeLimit){
            return state;
        }
        double v = Double.POSITIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node max = maxValue(child, a, b, distance);
            if(v > max.value){
                v = max.value;
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
