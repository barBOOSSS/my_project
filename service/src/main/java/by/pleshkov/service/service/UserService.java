package by.pleshkov.service.service;

import by.pleshkov.database.dao.UserDao;
import by.pleshkov.database.model.User;
import by.pleshkov.service.util.HashPassword;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService implements IService<User> {
    private static final UserService INSTANCE = new UserService();
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final UserDao userDao = UserDao.getInstance();


    public User create(User user) {
        try {
            user.setPassword(HashPassword.hashPassword(user.getPassword()));
            if (userDao.create(user).equals(null)) {
                log.log(Level.INFO, "Пользователь не создан");
                return null;
            } else {
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User read(long id) {
        try {
            User user = userDao.read(id);
            if (user.equals(null)) {
                log.log(Level.INFO, "Пользователь не найден");
                return null;
            } else {
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User readLoginAndPassword(String login, String password) {
        try {
            User user = userDao.readByLoginAndPassword(login, HashPassword.hashPassword(password));
            if (user.getId() == 0) {
                log.log(Level.INFO, "Пользователь не найден");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(User user) {
        try {
            String newPassword = HashPassword.hashPassword(user.getPassword());
            user.setPassword(newPassword);
            if (userDao.update(user)) {
                log.log(Level.INFO, "Пользователь не обновлен");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(long id) {
        try {
            if (userDao.delete(id)) {
                log.log(Level.INFO, "User не удален");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> readAll() {
        return userDao.readAll();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}

