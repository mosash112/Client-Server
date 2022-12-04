import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Start {
    static Registry r;
    public static void main(String[] args) throws RemoteException {
        try {
            r = LocateRegistry.createRegistry(0);
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"rmiregistry\"");
            ComputeEngine ce = new ComputeEngine();
            ce.setUp();

            Scanner sc = new Scanner(System.in);
            String IP, in, out;
            int count = 0;
            System.out.println("Enter the server IP address:");
            IP = sc.next();
            System.out.println("Enter the input batch file name: (or 'x' to close)");
            in = sc.next();
            while(!in.equals("x")) {
                count++;
                Client c = new Client();
                c.request(IP, in, "log"+count+".txt");
                System.out.println("Enter the input batch file name: (or 'x' to close)");
                in = sc.next();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
