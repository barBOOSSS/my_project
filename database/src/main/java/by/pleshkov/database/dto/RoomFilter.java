package by.pleshkov.database.dto;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;

public record RoomFilter(int places, ClassRoom classRoom, StatusRoom statusRoom, int limit, int page) {
}
