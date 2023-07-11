package by.pleshkov.service.service;

import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserEntity save(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .build());
    }

    public Optional<UserEntity> getBy(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty();
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}

