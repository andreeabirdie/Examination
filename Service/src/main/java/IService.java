import domain.Paper;

import java.rmi.RemoteException;
import java.util.List;

public interface IService {
    boolean login(String username, String password, IObserver client) throws Exception;
    void logout(String username, IObserver client) throws Exception;
    void addGrade(String username, Integer paperId, Double grade);
    List<Paper> getPapers(String username);
}
