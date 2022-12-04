import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private HashMap<Integer,ArrayList<Integer>> nodes = new HashMap<>();

//    public Graph(HashMap<Integer, ArrayList<Integer>> nodes) {
//        this.nodes = nodes;
//    }

    public void printEdges(){
        for (int n:nodes.keySet()) {
            System.out.println("node ("+n+"): "+nodes.get(n));
        }
    }

    public void checkNode(int n, int e) {
        ArrayList<Integer> old, es = new ArrayList<>(), n2, en = new ArrayList<>();
        old = nodes.get(n);
        n2 = nodes.get(e);
//        System.out.println(old);
        System.out.println("node "+e+" : n2: "+n2);
        if (n2 == null)
            nodes.put(e, en);
        if (old == null){
            es.add(e);
            nodes.put(n, es);
        }else {
            es.addAll(old);
            es.add(e);
            nodes.replace(n, old, es);
        }

//        System.out.println(nodes);
    }

    public void removeNode(int n, int e){
        ArrayList<Integer> es = new ArrayList<>(), old = new ArrayList<>();
        es = nodes.get(n);
        if(es.contains(e)) {
            old = es;
            es.remove(es.indexOf(e));
        }
        nodes.replace(n, old, es);
    }

    public void initiateGraph(){
        File f = new File("./initialInput.txt");
        Scanner in = null;
        int ns[] = new int[2];

        try {
            in = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNextLine()) {
            String data = in.nextLine();
            if (data.equals("S"))
                break;
            ns[0] = Integer.parseInt(data.split(" ")[0]);
            ns[1] = Integer.parseInt(data.split(" ")[1]);
            checkNode(ns[0],ns[1]);
        }
        printEdges();
    }

    public int query(int src, int dest){
        return BFS(src,dest)[dest];
    }

    private int[] BFS(int src, int dest){
        int v = nodes.size();
        int[] pred = new int[v],dist = new int[v];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[v];

        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < nodes.get(u).size(); i++) {
//                System.out.println(i);
                if (!visited[nodes.get(u).get(i)]) {
                    visited[nodes.get(u).get(i)] = true;
                    dist[nodes.get(u).get(i)] = dist[u] + 1;
                    pred[nodes.get(u).get(i)] = u;
                    queue.add(nodes.get(u).get(i));
                    if (nodes.get(u).get(i) == dest)
                        return dist;
                }
            }
        }
        return dist;
    }
}
