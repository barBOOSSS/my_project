package by.pleshkov.database.entity;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "users")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class RoomEntity implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "places", nullable = false)
    private Integer places;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_room", length = 15, nullable = false)
    private ClassRoom classRoom;

    @Column(name = "price")
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_room", length = 15, nullable = false)
    private StatusRoom statusRoom;

    @Builder.Default
    @ManyToMany(mappedBy = "rooms", cascade = CascadeType.REMOVE)
    private List<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity user) {
        this.getUsers().add(user);
        user.getRooms().add(this);
    }

    public void removeUser(UserEntity user) {
        this.getUsers().remove(user);
        user.getRooms().remove(this);
    }
}
