package by.pleshkov.database.repository;


import by.pleshkov.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    List<UserEntity> findAllByIdIn(List<Long> ids);
}
