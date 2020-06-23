package repository;

import domain.Paper;

import java.util.List;

public interface IPaperRepository {
    Paper findOne(Integer paperID);
    List<Paper> findAll(String username);
    void update(Paper p);

}
