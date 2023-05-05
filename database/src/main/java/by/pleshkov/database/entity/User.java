package by.pleshkov.database.entity;

import by.pleshkov.database.constant.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Role role;
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
}
