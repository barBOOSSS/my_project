package by.pleshkov.database.entity;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
//@ToString(exclude = "rooms")
//@EqualsAndHashCode(exclude = "rooms", callSuper = false)
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity extends CreatableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @Builder.Default
//    @OneToMany(mappedBy = "order")
//    private List<RoomEntity> rooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusOrder statusOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "solution", nullable = false)
    private Solution solution;
}
