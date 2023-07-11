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
    Integer number;
    Integer places;
    ClassRoom classRoom;
    Integer price;
    StatusRoom statusRoom;
    List<Long> usersIds = new ArrayList<>();
}
