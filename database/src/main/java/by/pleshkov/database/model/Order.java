package by.pleshkov.database.model;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private long id;
    private String user;
    private int places;
    private ClassRoom classRoom;
    private StatusOrder statusOrder;
    private Solution solution;
}
