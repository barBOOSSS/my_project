package by.pleshkov.database.dto;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.entity.UserEntity;

import java.util.List;

public record RoomReadDto(Integer number, Integer places, ClassRoom classRoom, Integer price, StatusRoom statusRoom, List<String> users) {
}
