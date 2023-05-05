package by.pleshkov.database.entity;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Room {
    private Long id;
    private int number;
    private int places;
    private ClassRoom classRoom;
    private StatusRoom statusRoom;
    private User user;
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
}
