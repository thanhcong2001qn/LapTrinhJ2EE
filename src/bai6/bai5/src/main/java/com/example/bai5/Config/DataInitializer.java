package com.example.bai5.Config;



import com.example.bai5.Repository.UserRepository;
import com.example.bai5.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra nếu chưa có user nào thì tạo mới
        if (userRepository.count() == 0) {

            // Tạo Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setFullName("Administrator");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✓ Admin created: username=admin, password=admin123");

            // Tạo User thường
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@example.com");
            user.setFullName("Normal User");
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("✓ User created: username=user, password=user123");

            // Tạo thêm 1 user khác
            User john = new User();
            john.setUsername("john");
            john.setPassword(passwordEncoder.encode("john123"));
            john.setEmail("john@example.com");
            john.setFullName("John Doe");
            john.setRole("ROLE_USER");
            john.setEnabled(true);
            userRepository.save(john);
            System.out.println("✓ User created: username=john, password=john123");

            System.out.println("========================================");
            System.out.println("Sample users have been created!");
            System.out.println("========================================");
        }
    }
}
