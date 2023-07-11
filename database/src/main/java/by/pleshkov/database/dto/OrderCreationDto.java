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
    Integer price;
    StatusOrder statusOrder;
    Solution solution;
    Long userId;
}
