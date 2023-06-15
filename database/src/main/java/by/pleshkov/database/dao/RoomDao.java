package by.pleshkov.database.dao;


import by.pleshkov.database.dto.RoomDto;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.RoomEntity_;

import by.pleshkov.database.entity.UserEntity_;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDao {

    private static final RoomDao INSTANCE = new RoomDao();

    public Optional<RoomEntity> create(Session session, RoomEntity room) {
        session.persist(room);
        return Optional.ofNullable(room);
    }

    public Optional<RoomEntity> findByID(Session session, Long id) {
        return Optional.ofNullable(session.get(RoomEntity.class, id));
    }

    public List<RoomEntity> findByFilter(Session session, RoomFilter filter) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<RoomEntity> query = cb.createQuery(RoomEntity.class);
        JpaRoot<RoomEntity> roomRoot = query.from(RoomEntity.class);
        query.select(roomRoot);
        JpaJoin<Object, Object> users = roomRoot.join(RoomEntity_.USERS);
        query.where(collectPredicates(filter, cb, roomRoot, users).toArray(Predicate[]::new));
        return session.createQuery(query)
                .setMaxResults(filter.getLimit())
                .setFirstResult(filter.getPage())
                .list();
    }

    public Optional<RoomEntity> update(Session session, RoomEntity room) {
        return Optional.ofNullable(session.merge(room));
    }

    public boolean delete(Session session, Long id) {
        return Optional.ofNullable(session.get(RoomEntity.class, id))
                .map(room -> {
                    session.remove(room);
                    return true;
                })
                .orElse(false);
    }

    public List<RoomEntity> findAll(Session session) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<RoomEntity> query = cb.createQuery(RoomEntity.class);
        JpaRoot<RoomEntity> roomRoot = query.from(RoomEntity.class);
        query.select(roomRoot);
        return session.createQuery(query).list();
    }

    public List<RoomDto> findAllDtos(Session session) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<RoomDto> query = cb.createQuery(RoomDto.class);
        JpaRoot<RoomEntity> roomRoot = query.from(RoomEntity.class);
        JpaJoin<Object, Object> users = roomRoot.join(RoomEntity_.USERS, JoinType.LEFT);
        query.multiselect(roomRoot.get(RoomEntity_.NUMBER), users.get(UserEntity_.NAME));
        return session.createQuery(query).list();
    }

    public List<RoomEntity> findByUser(Session session, String userName) {
        var cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<RoomEntity> query = cb.createQuery(RoomEntity.class);
        JpaRoot<RoomEntity> roomRoot = query.from(RoomEntity.class);
        query.select(roomRoot);
        JpaJoin<Object, Object> users = roomRoot.join(RoomEntity_.USERS);
        query.where(cb.equal(users.get(UserEntity_.NAME), userName));
        return session.createQuery(query).list();
    }

    private static List<Predicate> collectPredicates(RoomFilter filter, HibernateCriteriaBuilder cb, JpaRoot<RoomEntity> roomRoot, JpaJoin<Object, Object> users) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getPlaces() != null) {
            predicates.add(cb.equal(roomRoot.get(RoomEntity_.PLACES), filter.getPlaces()));
        }
        if (filter.getClassRoom() != null) {
            predicates.add(cb.equal(roomRoot.get(RoomEntity_.CLASS_ROOM), filter.getClassRoom()));
        }
        if (filter.getStatusRoom() != null) {
            predicates.add(cb.equal(roomRoot.get(RoomEntity_.STATUS_ROOM), filter.getStatusRoom()));
        }
        if (filter.getUserName() != null) {
            predicates.add(cb.like(users.get(UserEntity_.NAME), "%" + filter.getUserName() + "%"));
        }
        return predicates;
    }

    public static RoomDao getInstance() {
        return INSTANCE;
    }
}
