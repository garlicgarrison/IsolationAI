import java.util.PriorityQueue;

public class AlphaBeta {

    public Node nextStep;

    //returns the position to which the player should move
    public int alphaBetaSearch(int[][] state){
        Node firstNode = new Node(state, true);
        nextStep = maxValue(firstNode, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return nextStep.position;
    }

    public Node maxValue(Node state, double a, double b){
        if(terminalTest(state)){
            return state;
        }
        double v = Double.NEGATIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node min = minValue(child, a, b);
            if(v < min.value){
                v = min.value;
                next = min;
            }
            if(v>=b) return next;
            a = Math.max(a, v);
        }
        return state;
    }

    public Node minValue(Node state, double a, double b){
        if(terminalTest(state)){
            return state;
        }
        double v = Double.POSITIVE_INFINITY;
        Node next = state;
        PriorityQueue<Node> children = state.getChildren();
        for (Node child: children) {
            Node max = maxValue(child, a, b);
            if(v > max.value){
                v = max.value;
                next = max;
            }
            if(v<=a) return next;
            b = Math.min(b, v);
        }
        return state;
    }

    public boolean terminalTest(Node state){
        if(state.getLegalMoves(state.position).isEmpty() || state.getLegalMoves(state.opponentPosition).isEmpty()){
            return true;
        }
        return false;
    }

}
