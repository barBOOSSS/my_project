package by.pleshkov.service.service;

import by.pleshkov.database.dao.UserDao;
import by.pleshkov.database.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public Optional<UserEntity> save(UserEntity user) {
        return userDao.create(user);
    }

    public UserEntity getById(Long id) {
        return userDao.findByID(id)
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("YYYY")
                        .build());
    }

    public Optional<UserEntity> getBy(String email, String password) {
        return userDao.getByEmailAndPass(email, password);
    }

    public Optional<UserEntity> update(UserEntity user) {
        return userDao.update(user);
    }

    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public List<UserEntity> getAll() {
        List<UserEntity> users = userDao.findAll();
        return users;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}

