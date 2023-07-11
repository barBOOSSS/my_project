package by.pleshkov.database.repository;

import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepositoryExtension {

    List<RoomEntity> findByFilter(RoomFilter filter);
}
