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

//            ComputePi cp1 = new ComputePi();
//            ComputePi cp2 = new ComputePi();
//            ComputePi cp3 = new ComputePi();
//            cp1.request("127.0.0.1","5");
//            cp2.request("127.0.0.1","15");
//            cp3.request("127.0.0.1","25");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
