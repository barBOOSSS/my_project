package by.pleshkov.database.model;

import by.pleshkov.database.constant.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long id;
    private String login;
    private String password;
    private Role role;
}
