package start;


import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import repository.JdbcUtils;

@ComponentScan("papers")
@ComponentScan("repository")
@SpringBootApplication
public class StartServer {
    public static void main(String[] args){
        JdbcUtils.initialize();
        SpringApplication.run(StartServer.class,args);
    }
}