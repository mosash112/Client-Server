public class Edge {
    private int nodes[] = new int[2];

    public Edge(int n1, int n2) {
        this.nodes[0] = n1;
        this.nodes[1] = n2;
    }

    public int[] getNodes() {
        return nodes;
    }

    public void setNodes(int[] nodes) {
        this.nodes = nodes;
    }
}
