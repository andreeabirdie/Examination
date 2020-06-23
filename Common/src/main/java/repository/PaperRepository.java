package repository;

import domain.Examiner;
import domain.Paper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaperRepository implements IPaperRepository {

    public PaperRepository() {
        System.out.println("Creating paper repository");
    }


    @Override
    public Paper findOne(Integer paperID) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Paper as p where p.paperId = ?";
                List<Paper> paper = session.createQuery(queryString, Paper.class)
                        .setParameter(0, paperID)
                        .list();
                tx.commit();
                if (paper.size() == 1)
                    return paper.get(0);
                else return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Paper> findAll(String username) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Paper as p where p.firstExaminer = ? or p.secondExaminer = ?";
                List<Paper> papers = session.createQuery(queryString, Paper.class)
                        .setParameter(0,username)
                        .setParameter(1,username)
                        .list();
                tx.commit();
                return papers;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Paper p) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Paper ppr = (Paper) session.load( Paper.class, p.getPaperId());
                ppr.setFirstGrade(p.getFirstGrade());
                ppr.setSecondGrade(p.getSecondGrade());
                ppr.setFinalGrade(p.getFinalGrade());
                ppr.setRe_evaluate(p.getRe_evaluate());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
