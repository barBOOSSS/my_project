package by.pleshkov.database;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Role;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.UserEntity;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

@UtilityClass
public class TestDataImporter {

    public static void importTestData(Session session) {

        UserEntity pit = UserEntity.builder()
                .name("Bred")
                .surname("Pit")
                .email("pit@gmail.com")
                .password(String.valueOf(111))
                .role(Role.USER)
                .build();

        UserEntity vin = UserEntity.builder()
                .name("Vin")
                .surname("Diesel")
                .email("vin@gmail.com")
                .password(String.valueOf(222))
                .role(Role.USER)
                .build();

        RoomEntity room1 = RoomEntity.builder()
                .number(1)
                .places(1)
                .price(100)
                .classRoom(ClassRoom.STANDARD)
                .statusRoom(StatusRoom.FREE)
                .build();

        RoomEntity room2 = RoomEntity.builder()
                .number(2)
                .places(2)
                .price(200)
                .classRoom(ClassRoom.STANDARD)
                .statusRoom(StatusRoom.FREE)
                .build();

        RoomEntity room3 = RoomEntity.builder()
                .number(3)
                .places(3)
                .price(300)
                .classRoom(ClassRoom.SUITE)
                .statusRoom(StatusRoom.FREE)
                .build();

        RoomEntity room4 = RoomEntity.builder()
                .number(4)
                .places(4)
                .price(400)
                .classRoom(ClassRoom.SUITE)
                .statusRoom(StatusRoom.FREE)
                .build();

        OrderEntity order1 = OrderEntity.builder()
                .price(200)
                .statusOrder(StatusOrder.NEW)
                .solution(Solution.DENIED)
                .build();

        OrderEntity order2 = OrderEntity.builder()
                .price(300)
                .statusOrder(StatusOrder.NEW)
                .solution(Solution.UNPROCESSED)
                .build();

        OrderEntity order3 = OrderEntity.builder()
                .price(400)
                .statusOrder(StatusOrder.CLOSE)
                .solution(Solution.UNPROCESSED)
                .build();

        session.persist(pit);
        session.persist(vin);

        pit.addOrder(order1);
        vin.addOrder(order2);
        vin.addOrder(order3);

        session.persist(order1);
        session.persist(order2);
        session.persist(order3);

        room1.addUser(pit);
        room2.addUser(pit);
        room3.addUser(vin);

        session.persist(room1);
        session.persist(room2);
        session.persist(room3);
        session.persist(room4);
    }
}
