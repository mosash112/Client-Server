import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ComputeEngine implements Runnable, Compute  {

//    private HashMap<Integer,ArrayList<Integer>> nodes = new HashMap<>();
//    private Graph g = new Graph(nodes);
    private Graph g = new Graph();

    @Override
    public <T> T executeTask(Task<T> t) {
        return t.execute();
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
    }

//    public int[] request(HashMap<String,int[]> req){
//        int ns[] = new int[2], qs[] = new int[0];
//        String r = null;
//        for (String s:req.keySet()) {
//            r = s;
//            ns = req.get(s);
//        }
//
//        System.out.println(r+" "+ns[0]+", "+ns[1]);
//        switch (r){
//            case "A":
//                g.checkNode(ns[0],ns[1]);
//                break;
//            case "Q":
//                g.query(nodes,ns[0],ns[1],nodes.size());
//                break;
//            case "D":
//                g.removeNode(ns[0], ns[1]);
//                break;
//
//        }
//        g.printEdges();
//        return qs;
//    }

    public void request(String file){
        File f = new File(file);
        Scanner bat = null;
        int ns[] = new int[2], dist = 0;
        String r;

        try {
            bat = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (bat.hasNextLine()) {
            String data = bat.nextLine();
//            System.out.println(data);
            if (data.equals("F"))
                break;
            r = data.split(" ")[0];
            ns[0] = Integer.parseInt(data.split(" ")[1]);
            ns[1] = Integer.parseInt(data.split(" ")[2]);
            System.out.println(r + " " + ns[0] + ", " + ns[1]);
            switch (r) {
                case "A":
                    g.checkNode(ns[0], ns[1]);
                    break;
                case "Q":
                    dist = g.query(ns[0], ns[1]);
                    System.out.println("dist: "+dist);
                    break;
                case "D":
                    g.removeNode(ns[0], ns[1]);
                    break;

            }
            g.printEdges();
        }
    }

    @Override
    public void run() {
        System.out.println("running in the thread...");
        g.initiateGraph();
        System.out.println("R");
        System.out.println("batch:");
        Scanner sc = new Scanner(System.in);
        String file = null, s = sc.next();

        while (!s.equals("x")){
            switch (s){
                case "1":
                    file = "batch1.txt";
                    break;
                case "2":
                    file = "batch2.txt";
                    break;
            }
            request(file);
            s = sc.next();
        }
    }
}