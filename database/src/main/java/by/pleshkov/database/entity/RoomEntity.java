package by.pleshkov.database.entity;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class RoomEntity extends CreatableEntity<Long> {

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
