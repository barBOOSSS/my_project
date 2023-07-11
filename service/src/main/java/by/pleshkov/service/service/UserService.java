package by.pleshkov.service.service;

import by.pleshkov.database.constant.Role;
import by.pleshkov.database.dto.RegistrationDto;
import by.pleshkov.database.dto.UserReadDto;
import by.pleshkov.database.entity.Address;
import by.pleshkov.database.entity.PassportEntity;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserReadDto> getById(Long id) {
        return userRepository.findById(id)
                .map(this::toReadDto);
    }

    public List<UserReadDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    public Optional<UserEntity> createUser(RegistrationDto registrationDto) {
        UserEntity newUser = UserEntity.builder()
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .name(registrationDto.getName())
                .surname(registrationDto.getSurname())
                .address(Address.builder()
                        .city(registrationDto.getCity())
                        .street(registrationDto.getStreet())
                        .building(registrationDto.getBuilding())
                        .flat(registrationDto.getFlat())
                        .build())
                .build();
        newUser.setPassport(PassportEntity.builder()
                .number(registrationDto.getPassport())
                .build());
        return Optional.of(userRepository.save(newUser));
    }

    public Optional<UserReadDto> update(Long id, RegistrationDto update) {
        Optional<UserEntity> existedUser = userRepository.findById(id);
        if (existedUser.isPresent()) {
            UserEntity user = existedUser.get();
            user.setEmail(update.getEmail());
            user.setRole(update.getRole());
            user.setName(update.getName());
            user.setSurname(update.getSurname());
            user.setAddress(Address.builder()
                    .city(update.getCity())
                    .street(update.getStreet())
                    .building(update.getBuilding())
                    .flat(update.getFlat())
                    .build());
            user.setPassport(PassportEntity.builder()
                    .id(user.getId())
                    .number(update.getPassport())
                    .build());
            return Optional.of(toReadDto(userRepository.save(user)));
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(userEntity -> User.builder()
                        .username(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .authorities(userEntity.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserReadDto toReadDto(UserEntity user) {
        return new UserReadDto(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getAddress().getCity(),
                user.getAddress().getStreet(),
                user.getAddress().getBuilding(),
                user.getAddress().getFlat(),
                user.getPassport().getNumber());
    }

}

