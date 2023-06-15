package by.pleshkov.database.dto;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoomFilter {
    private Integer places;
    private ClassRoom classRoom;
    private StatusRoom statusRoom;
    private String userName;
    private Integer limit;
    private Integer page;

    public Integer getLimit() {
        return limit == null ? 10 : limit;
    }

    public Integer getPage() {
        return page == null ? 0 : limit * (page - 1);
    }
}
