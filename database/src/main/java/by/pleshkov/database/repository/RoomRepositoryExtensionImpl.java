package by.pleshkov.database.repository;

import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.RoomEntity_;
import by.pleshkov.database.entity.UserEntity_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryExtensionImpl implements RoomRepositoryExtension {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<RoomEntity> findByFilter(RoomFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoomEntity> query = cb.createQuery(RoomEntity.class);
        Root<RoomEntity> roomRoot = query.from(RoomEntity.class);
        query.select(roomRoot);
        Join<Object, Object> users = roomRoot.join(RoomEntity_.USERS, JoinType.LEFT);
        query.where(collectPredicates(filter, cb, roomRoot, users).toArray(Predicate[]::new));
        return entityManager.createQuery(query)
                .setMaxResults(filter.getLimit())
                .setFirstResult(filter.getPage())
                .getResultList();
    }

    private static List<Predicate> collectPredicates(RoomFilter filter, CriteriaBuilder cb, Root<RoomEntity> roomRoot, Join<Object, Object> users) {
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
        if (filter.getUserName() != null && !filter.getUserName().isBlank()) {
            predicates.add(cb.like(users.get(UserEntity_.NAME), "%" + filter.getUserName() + "%"));
        }
        return predicates;
    }
}
