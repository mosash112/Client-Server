import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ComputeEngine implements Compute  {

    private Graph g = new Graph();
    private ArrayList<Integer> out = null;
    private boolean flag = false;

    @Override
    public <T> ArrayList<Integer> executeTask(Task<T> t) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                out = request(t.execute());
                flag = true;
            }
        });
        t1.start();
        while(!flag){
            System.out.print(".");
        }
        return out;
    }

    public void setUp() {
        try {
            String name = "Compute";
            Compute engine = this;
            System.setProperty("java.security.policy","file:./server.policy");
//            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
        g.initiateGraph();
        System.out.println("R");
    }

    public ArrayList<Integer> request(ArrayList<ArrayList<String>> req){
        int ns[] = new int[2], dist = 0;
        ArrayList<Integer> qs = new ArrayList<>();
        String r = null;
        for (ArrayList<String> s:req) {
            r = s.get(0);
            ns[0] = Integer.parseInt(s.get(1));
            ns[1] = Integer.parseInt(s.get(2));
            System.out.println(r+" "+ns[0]+", "+ns[1]);
            switch (r){
                case "A":
                    g.checkNode(ns[0],ns[1]);
                    break;
                case "Q":
                    dist = g.query(ns[0], ns[1]);
                    System.out.println("dist: "+dist);
                    qs.add(dist);
                    break;
                case "D":
                    g.removeNode(ns[0], ns[1]);
                    break;
            }
            g.printEdges();
        }
        return qs;
    }
}