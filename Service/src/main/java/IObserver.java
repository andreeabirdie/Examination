import domain.Paper;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void paperGraded(Paper paper) throws RemoteException;
}
