package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String email, String rawPassword) {
        Role userRole = roleRepository.findByRolename("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("기본 역할이 존재하지 않습니다."));

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .roles(new HashSet<>())
                .build();

        user.getRoles().add(userRole);
        userRepository.save(user);
    }
}
