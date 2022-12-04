import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {

    public void request(String host, String file, String output) {
        try {
            String name = "Compute";
            System.setProperty("java.security.policy","file:./client.policy");
            Registry registry = LocateRegistry.getRegistry(host);
            Compute comp = (Compute) registry.lookup(name);
//            batch(file);
            Request task = new Request(file);
            ArrayList<Integer> out = comp.executeTask(task);
            FileWriter fw = new FileWriter(output);
            System.out.print("output: ");
            for (int i:out) {
                System.out.print(i+" ");
                fw.write(i+"\n");
            }
            fw.close();
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }
//    public void batch(String f){
//        File file = new File(f);
//        Scanner bat = null;
//        int ns[] = new int[2], count = 0;
//        String r;
//        ArrayList<ArrayList<String>> ops = new ArrayList<>();
//
//        try {
//            bat = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        while (bat.hasNextLine()) {
//            String data = bat.nextLine();
//            System.out.println(data);
//            if (data.equals("F"))
//                break;
//            ops.add(new ArrayList<>(3));
//            ops.get(count).add(data.split(" ")[0]);
//            ops.get(count).add(data.split(" ")[1]);
//            ops.get(count).add(data.split(" ")[2]);
//            count++;
//        }
//        System.out.println("ops.size:"+ops.size());
//        for (ArrayList<String> s:ops) {
//            System.out.println(s);
//        }
//    }
}