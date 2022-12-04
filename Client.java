import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {

    public void request(String host, String file) {
        try {
            String name = "Compute";
            System.setProperty("java.security.policy","file:./client.policy");
            Registry registry = LocateRegistry.getRegistry(host);
            Compute comp = (Compute) registry.lookup(name);
            Request task = new Request(file);
            int[] out = comp.executeTask(task);
            System.out.println(out);
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }

//    public HashMap<String, int[]> batch(String f){
//        File file = new File(f);
//        Scanner bat = null;
//        int ns[] = new int[2];
//        String r;
//        HashMap<String, int[]> ops = new HashMap<>();
//
//        try {
//            bat = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        while (bat.hasNextLine()) {
//            String data = bat.nextLine();
//            if (data.equals("F"))
//                break;
//            r = data.split(" ")[0];
//            ns[0] = Integer.parseInt(data.split(" ")[1]);
//            ns[1] = Integer.parseInt(data.split(" ")[2]);
//            ops.put(r, ns);
//        }
////        System.out.println(ops);
//        return ops;
//    }
}