
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Compute extends Remote {
    <T> ArrayList<Integer> executeTask(Task<T> t) throws RemoteException;
}