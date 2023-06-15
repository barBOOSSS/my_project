package by.pleshkov.database.dao;


import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.entity.UserEntity_;
import org.hibernate.Session;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public Optional<UserEntity> create(Session session, UserEntity user) {
        session.persist(user);
        return Optional.ofNullable(user);
    }

    public Optional<UserEntity> findByID(Session session, Long id) {
        return Optional.ofNullable(session.get(UserEntity.class, id));
    }

    public Optional<UserEntity> getByEmailAndPass(Session session, String email, String password) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        JpaRoot<UserEntity> userRoot = query.from(UserEntity.class);
        query.select(userRoot);
        query.where(cb.and(cb.equal(userRoot.get(UserEntity_.EMAIL), email), cb.equal(userRoot.get(UserEntity_.PASSWORD), password)));
        return session.createQuery(query).uniqueResultOptional();
    }

    public Optional<UserEntity> update(Session session, UserEntity user) {
        return Optional.ofNullable(session.merge(user));
    }

    public boolean delete(Session session, Long id) {
        return Optional.ofNullable(session.get(UserEntity.class, id))
                .map(user -> {
                    session.remove(user);
                    return true;
                })
                .orElse(false);
    }

    public List<UserEntity> findAll(Session session) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        JpaRoot<UserEntity> userRoot = query.from(UserEntity.class);
        query.select(userRoot);
        return session.createQuery(query).list();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
