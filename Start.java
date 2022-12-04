import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Start {
    static Registry r;
    public static void main(String[] args) throws RemoteException {
        try {
            r = LocateRegistry.createRegistry(0);
//            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"rmiregistry\"");
            ComputeEngine ce = new ComputeEngine();
            ce.setUp();

            Client cl1 = new Client();

            cl1.request("127.0.0.1","batch1.txt", "log1.txt");

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
