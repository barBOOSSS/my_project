package by.pleshkov.database.repository;

import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long>, RoomRepositoryExtension {

    Optional<RoomEntity> findByNumber(Integer number);

    List<RoomEntity> findAllByUsersContains(UserEntity user);

    @Query(value = "SELECT r.* FROM rooms r "
            + "JOIN room_user ru ON r.id = ru.room_id "
            + "JOIN users u ON u.id = ru.user_id "
            + "WHERE u.name = ?", nativeQuery = true)
    List<RoomEntity> findAllBy(String userName);

    @Modifying
    @Query("UPDATE RoomEntity r SET r.number = :number WHERE r.id = :id")
    void setNumberById(@Param("number") Integer number, @Param("id") Long id);
}
