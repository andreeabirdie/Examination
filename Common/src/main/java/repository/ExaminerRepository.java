package repository;

import domain.Examiner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ExaminerRepository implements IExaminerRepository {

    public ExaminerRepository() {
        System.out.println("Creating examiner repository");
    }

    @Override
    public boolean findOne(String username, String password) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Examiner as e where e.username = ? and e.password = ?";
                List<Examiner> user = session.createQuery(queryString, Examiner.class)
                        .setParameter(0, username)
                        .setParameter(1, password)
                        .list();
                tx.commit();
                if (user.size() == 1)
                    return true;
                else return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
