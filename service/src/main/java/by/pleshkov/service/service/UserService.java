package by.pleshkov.service.service;

import by.pleshkov.database.dao.UserDao;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public Optional<UserEntity> save(UserEntity user) {
        Optional<UserEntity> newUser;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newUser = userDao.create(session, user);
            transaction.commit();
        }
        return newUser;
    }

    public UserEntity getById(Long id) {
        UserEntity user;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            user = userDao.findByID(session, id)
                    .orElse(UserEntity.builder()
                            .name("XXX")
                            .surname("YYYY")
                            .build());
            transaction.commit();
        }
        return user;
    }

    public Optional<UserEntity> getBy(String email, String password) {
        Optional<UserEntity> newUser;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newUser = userDao.getByEmailAndPass(session, email, password);
            transaction.commit();
        }
        return newUser;
    }

    public Optional<UserEntity> update(UserEntity user) {
        Optional<UserEntity> newUser;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newUser = userDao.update(session, user);
            transaction.commit();
        }
        return newUser;
    }

    public boolean delete(Long id) {
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            if (userDao.delete(session, id)) {
                transaction.commit();
                return true;
            } else {
                transaction.commit();
                return false;
            }
        }
    }

    public List<UserEntity> getAll() {
        List<UserEntity> users;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            users = userDao.findAll(session);
            transaction.commit();
        }
        return users;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}

