package by.pleshkov.database.dto;


import by.pleshkov.database.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    Long id;
    String email;
    String name;
    String surname;
    Role role;
    String city;
    String street;
    String building;
    String flat;
    String passport;
}
