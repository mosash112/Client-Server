import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputeEngine implements Runnable, Compute  {

    private ArrayList<Edge> edges = new ArrayList<>();

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
        for (Edge e:edges) {
            System.out.println("edge ("+(edges.indexOf(e)+1)+"): "+e.getNodes()[0]+", "+e.getNodes()[1]);
        }
    }

    public void checkEdge(Edge e){
//        System.out.println(edges.contains(e));
        boolean exist = false;
        for (Edge ed:edges) {
            if (e.getNodes()[0]==ed.getNodes()[0])
                if (e.getNodes()[1]==ed.getNodes()[1]) {
                    exist = true;
                    break;
                }
        }
        if (!exist)
            edges.add(e);
    }
    
    public void removeEdge(Edge e){
        int id = 0;
        for (Edge ed:edges) {
            if (e.getNodes()[0]==ed.getNodes()[0])
                if (e.getNodes()[1]==ed.getNodes()[1]) {
                    id = edges.indexOf(ed);
                    break;
                }
        }
        if (id != -1) {
            System.out.println("id of removed edge: " + id);
            edges.remove(id);
        }
    }

    public void initiatGraph(){
        File f = new File("./initialInput.txt");
        Scanner in = null;
        int es[] = new int[2], i=0;

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
            for (String s : data.split(" ")) {
                es[i] = Integer.parseInt(s);
                i++;
            }
//            System.out.println(es[0]+", "+es[1]);
            Edge e = new Edge(es[0], es[1]);
            checkEdge(e);
            i = 0;
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
            Edge e = new Edge(ns[0], ns[1]);
            switch (r){
                case "A":
                    checkEdge(e);
                    break;
                case "Q":
                    break;
                case "D":
                    removeEdge(e);
                    break;
            }
            printEdges();
        }
    }

    @Override
    public void run() {
        System.out.println("running in the thread...");
        initiatGraph();
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