package by.pleshkov.database.dto;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreationDto {
    private Integer number;
    private Integer places;
    private ClassRoom classRoom;
    private Integer price;
    private StatusRoom statusRoom;
    private List<Long> usersIds = new ArrayList<>();
}
