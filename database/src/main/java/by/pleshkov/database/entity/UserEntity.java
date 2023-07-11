package by.pleshkov.database.entity;

import by.pleshkov.database.constant.Role;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Embedded;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orders", "rooms", "passport"})
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
    @AttributeOverride(name = "city", column = @Column(name = "city"))
    @AttributeOverride(name = "street", column = @Column(name = "street"))
    @AttributeOverride(name = "building", column = @Column(name = "building"))
    @AttributeOverride(name = "flat", column = @Column(name = "flat"))
    private Address address;
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PassportEntity passport;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "room_user",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "room_id")
            })
    private List<RoomEntity> rooms = new ArrayList<>();

    public void addOrder(OrderEntity order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public void setPassport(PassportEntity passport) {
        this.passport = passport;
        passport.setUser(this);
    }
}
