import java.util.ArrayList;

public class Node {
    private final int id;
    private ArrayList<Integer> edges = new ArrayList<>();

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getEdges() {
        return edges;
    }

    public void addEdge(int e) {
        this.edges.add(e);
    }

    public void setEdges(ArrayList<Integer> edges) {
        this.edges = edges;
    }
}
