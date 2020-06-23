package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Papers", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Paper implements Serializable {
    private Integer paperId;
    private Double firstGrade;
    private Double secondGrade;
    private Double finalGrade;
    private String firstExaminer;
    private String secondExaminer;
    private int re_evaluate;

    public Paper(Integer paperId, String firstExaminer, String secondExaminer) {
        this.paperId = paperId;
        this.firstExaminer = firstExaminer;
        this.secondExaminer = secondExaminer;
        this.re_evaluate = -1;
        this.firstGrade = -1.D;
        this.secondGrade = -1.D;
        this.finalGrade = -1.D;
    }

    public Paper() {
    }

    @Id
    @Column(name="id")
    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Column(name="firstGrade")
    public Double getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(double firstGrade) {
        this.firstGrade = firstGrade;
    }

    @Column(name="secondGrade")
    public Double getSecondGrade() {
        return secondGrade;
    }

    public void setSecondGrade(double secondGrade) {
        this.secondGrade = secondGrade;
    }

    @Column(name="finalGrade")
    public Double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    @Column(name="firstExaminer")
    public String getFirstExaminer() {
        return firstExaminer;
    }

    public void setFirstExaminer(String firstExaminer) {
        this.firstExaminer = firstExaminer;
    }

    @Column(name="secondExaminer")
    public String getSecondExaminer() {
        return secondExaminer;
    }

    public void setSecondExaminer(String secondExaminer) {
        this.secondExaminer = secondExaminer;
    }

    @Column(name="re_evaluate")
    public int getRe_evaluate() {
        return re_evaluate;
    }

    public void setRe_evaluate(int re_evaluate) {
        this.re_evaluate = re_evaluate;
    }
}
