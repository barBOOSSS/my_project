package by.pleshkov.database.dto;


import by.pleshkov.database.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Role role;
    private String city;
    private String street;
    private String building;
    private String flat;
    private String passport;
}
