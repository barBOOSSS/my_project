package by.pleshkov.database.entity;

import by.pleshkov.database.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString(exclude = "orders")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends CreatableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    @Embedded
    @AttributeOverride(name = "tel", column = @Column(name = "tel"))
    @AttributeOverride(name = "address", column = @Column(name = "address"))
    private Contact contact;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoomEntity> roomEntities = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PassportEntity passport;

    public void addOrder(OrderEntity order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public void setPassport(PassportEntity passport) {
        this.passport = passport;
        passport.setUser(this);
    }
}
