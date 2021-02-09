package journalProject;

import journalProject.Database.Entry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Main {
    public static void main(String[] args) {
//        Entry e = new Entry("11-y","","","");
//        Entry e2 = new Entry("11-y","1","1","1");
//        System.out.println(e.equals(e2));
        SpringApplication.run(Main.class, args);
    }
}
