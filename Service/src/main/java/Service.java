import domain.Paper;
import repository.IExaminerRepository;
import repository.IPaperRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.abs;

public class Service implements IService {
    private IExaminerRepository examinerRepository;
    private IPaperRepository paperRepository;
    private Map<String,IObserver> loggedClients;

    public Service(IExaminerRepository examinerRepository, IPaperRepository paperRepository) {
        this.examinerRepository = examinerRepository;
        this.paperRepository = paperRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public boolean login(String username, String password, IObserver client) throws Exception {
        boolean valid = examinerRepository.findOne(username, password);
        if(valid) {
            if (loggedClients.get(username) != null) {
                throw new Exception("User is already Logged in!");
            }
            loggedClients.put(username, client);
            System.out.println("Client " + username + " connected");
        }
        return valid;
    }

    @Override
    public void logout(String username, IObserver client) throws Exception {
        IObserver localClient = loggedClients.remove(username);
        if (localClient == null)
            throw new Exception("User "+ username + " is not logged in");
        System.out.println("Client " + username + " disconnected");
    }

    @Override
    public void addGrade(String username, Integer paperId, Double grade) {
        Paper p = paperRepository.findOne(paperId);
        Integer order = 0;
        if(p.getFirstExaminer().equals(username)){
            p.setFirstGrade(grade);
            order = 1;
        }
        else if(p.getSecondExaminer().equals(username)){
            p.setSecondGrade(grade);
            order = 2;
        }
        if(abs(p.getFirstGrade() - p.getSecondGrade())>1 && p.getFirstGrade() != -1 && p.getSecondGrade() != -1){
            p.setRe_evaluate(order);
        }
        else{
            p.setRe_evaluate(-1);
            p.setFinalGrade((p.getFirstGrade()+p.getSecondGrade())/2);
        }
        paperRepository.update(p);
        notifyExaminer(username, p);
    }

    private final int defaultThreadsNo=5;
    private void notifyExaminer(String username, Paper p) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                System.out.println("notifying examiner...");
                loggedClients.get(username).paperGraded(p);
            }catch (Exception e){
                System.out.println("error notifying examiner...");
            }
        });

        executor.shutdown();
    }

    @Override
    public List<Paper> getPapers(String username) {
        return paperRepository.findAll(username);
    }
}
