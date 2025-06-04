package kr.ac.hansung.cse.hellospringdatajpa.config;

import jakarta.annotation.PostConstruct;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            Role adminRole = roleRepository.findByRolename("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN 없음"));
            Role userRole = roleRepository.findByRolename("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER 없음"));

            User admin = User.builder()
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole, userRole))
                    .build();

            userRepository.save(admin);
            System.out.println("✅ admin@example.com 계정이 생성되었습니다.");
        }
    }
}
