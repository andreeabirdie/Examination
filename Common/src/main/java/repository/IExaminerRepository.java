package repository;

public interface IExaminerRepository {
    boolean findOne(String username, String password);
}
