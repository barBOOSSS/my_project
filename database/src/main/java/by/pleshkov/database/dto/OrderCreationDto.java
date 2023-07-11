package by.pleshkov.database.dto;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationDto {
    private Integer price;
    private StatusOrder statusOrder;
    private Solution solution;
    private Long userId;
}
