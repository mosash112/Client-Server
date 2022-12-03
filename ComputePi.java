import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;

public class ComputePi {
    public static void main(String args[]) {
//        SecurityManager sm = System.getSecurityManager();
//        if (sm != null) {
//            System.setSecurityManager(null);
//        }
        try {
            String name = "Compute";
            System.setProperty("java.security.policy","file:./client.policy");
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(Integer.parseInt(args[1]));
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}