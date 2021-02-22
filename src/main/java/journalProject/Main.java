package journalProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication

public class Main {
    public static void main(String[] args) {
//        String ui = UUID.randomUUID().toString();
//        String ui2 = UUID.randomUUID().toString();
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(ui);
//        System.out.println(ui2);
//        System.out.println(encoder.encode("password"));

        SpringApplication.run(Main.class, args);
    }
}
