import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;

public class ComputePi {

    public void request(String host, String digits) {
        try {
            String name = "Compute";
            System.setProperty("java.security.policy","file:./client.policy");
            Registry registry = LocateRegistry.getRegistry(host);
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(Integer.parseInt(digits));
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}