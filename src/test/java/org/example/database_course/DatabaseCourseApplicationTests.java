package org.example.database_course;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DatabaseCourseApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("123456");
        boolean matches = passwordEncoder.matches("1234", "$2a$10$WCD7xp6lxrS.PvGmL86nhuFHMKJTc58Sh0dG1EQw0zSHjlLFyFvde");
        System.out.println(encode);
        System.out.println(encode2);
        System.out.println(matches);
    }

}
