package by.pleshkov.database.model;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {
    private long id;
    private int numberRoom;
    private int places;
    private ClassRoom classRoom;
    private Status status;
    private String user;
    private List<Order> orders;
}
