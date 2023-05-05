package by.pleshkov.database.entity;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Order {
    private long id;
    private String user;
    private int places;
    private ClassRoom classRoom;
    private StatusOrder statusOrder;
    private Solution solution;
}
