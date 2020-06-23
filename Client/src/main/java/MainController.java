import domain.Paper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static java.lang.System.exit;

public class MainController extends UnicastRemoteObject implements IObserver, Serializable {
    private IService server;
    private String username;
    ObservableList<Paper> papers= FXCollections.observableArrayList();
    @FXML
    TableView papersTV;
    @FXML
    Spinner gradeSpinner;
    @FXML
    Text confirmationText;

    public MainController() throws RemoteException {
    }


    public void setService(IService server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize(){
        confirmationText.setTextAlignment(TextAlignment.CENTER);
        papersTV.setItems(papers);
        gradeSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0,10,5,0.1));
    }

    public void populatePapersTable(){
        List<Paper> allPapers = null;
        papers.setAll();
        try {
            allPapers = server.getPapers(username);
        }catch (Exception e){
            alert(e.getMessage());
        }
        for(Paper p : allPapers) {
            if (p.getFirstExaminer().equals(username) && (p.getFirstGrade().equals(-1.D) || p.getRe_evaluate() == 1))
                    papers.add(p);
            else if(p.getSecondExaminer().equals(username) && (p.getSecondGrade().equals(-1.D) || p.getRe_evaluate() == 2))
                papers.add(p);
        }
    }

    @FXML
    public void logout() throws Exception {
        try{
            server.logout(username, this);
            exit(0);
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    @FXML
    public void grade(){
        try{
            Integer id = ((Paper)papersTV.getSelectionModel().getSelectedItem()).getPaperId();
            Double grade = (Double)gradeSpinner.getValue();
            if(id == null) throw new Exception("Please select a paper to grade");
            server.addGrade(username,id,grade);
        }catch (Exception e){
            alert(e.getMessage());
        }
    }

    @Override
    public void paperGraded(Paper p) {
        for(Paper ppr : papers){
            if(ppr.getPaperId().equals(p.getPaperId())){
                papers.set(papers.indexOf(ppr), p);
            }
        }
        if ((p.getFirstExaminer().equals(username) &&  p.getRe_evaluate() == 1)
                || (p.getSecondExaminer().equals(username) && p.getRe_evaluate() == 2)) {
            confirmationText.setText("RECORECTARE");
            confirmationText.setFill(Color.DARKRED);
        }
        else {
            papers.remove(p);
            confirmationText.setText("OK");
            confirmationText.setFill(Color.GREEN);
        }
    }

    public void alert(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }
}
