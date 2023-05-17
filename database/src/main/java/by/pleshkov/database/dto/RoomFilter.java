package by.pleshkov.database.dto;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomFilter {
    private int places;
    private ClassRoom classRoom;
    private StatusRoom statusRoom;
    int limit;
    int page;
}
