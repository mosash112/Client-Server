import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ComputeEngine implements Runnable, Compute  {

    private HashMap<Integer,ArrayList<Integer>> nodes = new HashMap<>();

    public ComputeEngine() {
        super();
    }

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

    public void printEdges(){
        for (int n:nodes.keySet()) {
            System.out.println("node ("+n+"): "+nodes.get(n));
        }
    }

    public void checkNode(int n, int e) {
        ArrayList<Integer> old = new ArrayList<>(), es = new ArrayList<>();
        old = nodes.get(n);
        System.out.println(old);
        if (old == null){
            es.add(e);
        nodes.put(n, es);
        }else {
            es.addAll(old);
            es.add(e);
            nodes.replace(n, old, es);
        }
        System.out.println(nodes);
    }

    public void removeNode(int n, int e){
        ArrayList<Integer> es = new ArrayList<>(), old = new ArrayList<>();
        es = nodes.get(n);
//        System.out.println(es);
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
//            System.out.println(data);
            if (data.equals("S"))
                break;
            ns[0] = Integer.parseInt(data.split(" ")[0]);
            ns[1] = Integer.parseInt(data.split(" ")[1]);
            checkNode(ns[0],ns[1]);
        }
        printEdges();
    }

    public void request(String file){
        File f = new File(file);
        Scanner bat = null;
        int ns[] = new int[2];
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
            System.out.println(r+" "+ns[0]+", "+ns[1]);
            switch (r){
                case "A":
                    checkNode(ns[0],ns[1]);
                    break;
                case "Q":
                    break;
                case "D":
                    removeNode(ns[0], ns[1]);
                    break;
            }
            printEdges();
        }
    }

    @Override
    public void run() {
        System.out.println("running in the thread...");
        initiateGraph();
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