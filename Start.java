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
            Thread th = new Thread(ce);
            th.start();

//            Client cl1 = new Client();
//            ComputePi cp2 = new ComputePi();
//            ComputePi cp3 = new ComputePi();
//            cl1.request("127.0.0.1","batch1.txt");
//            cp2.request("127.0.0.1","15");
//            cp3.request("127.0.0.1","25");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
