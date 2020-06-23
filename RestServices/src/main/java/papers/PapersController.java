package papers;

import domain.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PaperRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/papers")
public class PapersController {
    private static final String template="TemplatePaper";

    @Autowired
    private PaperRepository paperRepository;

    @RequestMapping(value = "/reevaluate/{username}", method = RequestMethod.GET)
    public Iterable<Paper> getByTeacher(@PathVariable String username){
        List<Paper> toReturn = new ArrayList<>();
        for(Paper p : paperRepository.findAll(username)){
            if(p.getFirstExaminer().equals(username) && p.getRe_evaluate() == 1)
                toReturn.add(p);
            else if(p.getSecondExaminer().equals(username) && p.getRe_evaluate() == 2)
                toReturn.add(p);
        }
        return toReturn;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Paper paper=paperRepository.findOne(id);
        if(paper==null){
            return new ResponseEntity<String>("Paper not found!", HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(paper, HttpStatus.OK);
        }
    }
}
