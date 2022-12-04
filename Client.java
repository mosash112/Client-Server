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
                fw.write(i+"\r\n");
            }
            fw.close();
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }
}