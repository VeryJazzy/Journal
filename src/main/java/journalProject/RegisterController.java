//package journalProject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.sql.DataSource;
//import java.util.UUID;
//
//public class RegisterController {
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private DataSource dataSource;
//
//    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//
//
//
//    @PostMapping("/register")
//    public String registerNewUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
//        String userID = UUID.randomUUID().toString();
//        jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?, ?)",
//                userID,
//                username,
//                passwordEncoder.encode(password),
//                1);
//        jdbcTemplate.update("INSERT INTO user_authorities VALUES (?, ?, ?)",
//                UUID.randomUUID().toString(),
//                userID,
//                "USER");
//
//        return "/login";
//    }
//
//}
