package by.pleshkov.service.service;

import by.pleshkov.database.dao.UserDao;
import by.pleshkov.database.entity.User;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public Optional<User> save(User user) {
        return userDao.create(user);
    }

    public User getById(Long id) {
        return userDao.findByID(id)
                .orElse(User.builder()
                        .name("XXX")
                        .surname("YYYY")
                        .build());
    }

    public Optional<User> getBy(String email, String password) {
        return userDao.getByEmailAndPass(email, password);
    }

    public Optional<User> update(User user) {
        return userDao.update(user);
    }

    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public List<User> getAll() {
        List<User> users = userDao.findAll();
        return users;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}

