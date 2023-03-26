package by.pleshkov.controller;


import by.pleshkov.constant.*;
import by.pleshkov.model.Number;
import by.pleshkov.model.Order;
import by.pleshkov.model.User;
import by.pleshkov.service_hotel.Service;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static by.pleshkov.constant.Role.*;

public class ControllerImpl implements Controller {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private Service service;

    public ControllerImpl(Service service) {
        this.service = service;
    }

    @Override
    public void start() {
        log.info("Запуск программы");
        while (true) {
            System.out.println("==Отель==");
            System.out.println("1 - Регистрация");
            System.out.println("2 - Войти в аккаунт");
            System.out.println("3 - Выход");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User opened the registration menu");
                    registration();
                    break;
                case "2":
                    log.info("User opened the login menu");
                    logIn();
                    break;
                case "3":
                    log.info("User left the program");
                    System.exit(0);
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void registration() {
        System.out.println("==Регистрация==");
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин");
        System.out.print("--> ");
        String login = sc.nextLine();
        System.out.println("Введите пароль");
        System.out.print("--> ");
        String password = sc.nextLine();
        Role role = USER;
        boolean isCreateUser = service.createUser(new User(login, password, role));
        if (Boolean.TRUE.equals(isCreateUser)) {
            log.info("User has registered under the login " + login);
            System.out.println("==Пользователь зарегистрирован==");
        } else {
            log.info("user not registered");
            System.out.println("==Пользователь не зарегистрирован==");
        }
    }

    public void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин");
        System.out.print("--> ");
        String login = sc.nextLine();
        log.info("User entered login " + login);
        System.out.println("Введите пароль");
        System.out.print("--> ");
        String password = sc.nextLine();
        log.info("User entered password " + password);
        User user = service.getUserLogIn(login, password);
        if (user != null) {
            if (USER.equals(user.getRole())) {
                menuUser(user);
            }
            if (MANAGER.equals(user.getRole())) {
                menuManager(user);
            }
            if (ADMIN.equals(user.getRole())) {
                menuAdmin(user);
            }
        } else {
            log.info("User entered an invalid number");
            System.out.println("Неверный логин или пароль");
        }
    }

    public void menuAdmin(User user) {
        while (true) {
            System.out.println("==Меню==");
            System.out.println("1 - Работа с пользователями");
            System.out.println("2 - Работа с номерами");
            System.out.println("3 - Работа с заказами");
            System.out.println("4 - Выход");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User opened the edit users menu");
                    editUserMenu();
                    break;
                case "2":
                    log.info("User opened the edit numbers menu");
                    editNumberMenu();
                    break;
                case "3":
                    log.info("User opened the edit orders menu");
                    editOrderMenu();
                    break;
                case "4":
                    log.info("User left the program");
                    System.exit(0);
                    break;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void editOrderMenu() {
        while (true) {
            System.out.println("==Меню работы с заказами==");
            System.out.println("1 - Посмотреть заказы");
            System.out.println("2 - Обработать заказы");
            System.out.println("3 - Сделать заказ");
            System.out.println("4 - Отменить заказ");
            System.out.println("5 - Вернуться в меню");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User views orders");
                    menuSeeOrders();
                    break;
                case "2":
                    log.info("User order processing");
                    menuOrderProcessing();
                    break;
                case "3":
                    log.info("User makes an order");
                    menuCreateOrders();
                    break;
                case "4":
                    log.info("User cancels the order");
                    menuDeleteOrders();
                    break;
                case "5":
                    log.info("User return menu");
                    return;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void editUserMenu() {
        while (true) {
            System.out.println("==Меню работы с пользователями==");
            System.out.println("1 - Посмотреть всех пользователей");
            System.out.println("2 - Создать нового пользователя");
            System.out.println("3 - Изменить пользователя");
            System.out.println("4 - Удалить пользователя");
            System.out.println("5 - Вернуться в меню");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User views users");
                    menuSeeUsers();
                    break;
                case "2":
                    log.info("User creates user");
                    menuCreateUsers();
                    break;
                case "3":
                    log.info("User updates user");
                    menuUpdateUsers();
                    break;
                case "4":
                    log.info("User deletes user");
                    menuDeleteUsers();
                    break;
                case "5":
                    log.info("User return menu");
                    return;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void menuManager(User user) {
        while (true) {
            System.out.println("==Меню==");
            System.out.println("1 - Посмотреть все номера");
            System.out.println("2 - Посмотреть заказы");
            System.out.println("3 - Обработать заказы");
            System.out.println("4 - Сделать заказ");
            System.out.println("5 - Отменить заказ");
            System.out.println("6 - Посмотреть всех пользователей");
            System.out.println("7 - Посмотреть пользователя");
            System.out.println("8 - exit");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User see all numbers");
                    menuSeeNumbers();
                    break;
                case "2":
                    log.info("User see all orders");
                    menuSeeOrders();
                    break;
                case "3":
                    log.info("User order processing");
                    menuOrderProcessing();
                    break;
                case "4":
                    log.info("User makes an order");
                    menuCreateOrders();
                    break;
                case "5":
                    log.info("User cancels the order");
                    menuDeleteOrders();
                    break;
                case "6":
                    log.info("User see user");
                    menuSeeUsers();
                    break;
                case "7":
                    log.info("User see users");
                    menuSeeUsers();
                    break;
                case "8":
                    log.info("User left the program");
                    System.exit(0);
                    break;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void menuOrderProcessing() {
        System.out.println("Не работает");
        return;
    }

    public void menuCreateOrders() {
        System.out.println("Введите логин пользователя");
        Scanner sc = new Scanner(System.in);
        System.out.print("--> ");
        String login = sc.nextLine();
        User user = service.getUser(login);
        if (user == null) {
            System.out.println("==Пользователь не найден==");
        } else {
            menuUserBuy(user);
        }
    }

    public void menuSeeOrders() {
        System.out.println(service.readOrderAll());
    }

    public void menuDeleteOrders() {
        System.out.println("Введите логин пользователя");
        Scanner sc = new Scanner(System.in);
        System.out.print("--> ");
        String login = sc.nextLine();
        User user = service.getUser(login);
        if (user == null) {
            System.out.println("==Пользователь не найден==");
        } else {
            System.out.println(user);
            menuUserReturn(user);
        }
    }

    public void menuSee() {
        System.out.println("Введите логин пользователя");
        Scanner sc = new Scanner(System.in);
        System.out.print("--> ");
        String login = sc.nextLine();
        User user = service.getUser(login);
        if (user != null) {
            menuUserSee(user);
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    public void menuSeeUsers() {
        System.out.println(service.readUserAll());
    }

    public void menuCreateUsers() {
        System.out.println("==Создание пользователя==");
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин");
        System.out.print("--> ");
        String login = sc.nextLine();
        System.out.println("Введите пароль");
        System.out.print("--> ");
        String password = sc.nextLine();
        System.out.println("Выберите роль");
        System.out.println("Менеджер - 1");
        System.out.println("Пользователь - 2");
        System.out.print("--> ");
        String res = sc.nextLine();
        Role role;
        switch (res) {
            case "1":
                role = MANAGER;
                break;
            case "2":
                role = USER;
                break;
            default:
                System.out.println("==Вы ввели неверное число==");
                return;
        }
        boolean isUpdate = service.createUser(new User(login, password, role));
        if (Boolean.TRUE.equals(isUpdate)) {
            log.info("User created new user " + login + " role " + role);
            System.out.println("==Пользователь создан==");
        } else {
            log.info("User created new user");
            System.out.println("==Пользователь не создан==");
        }
    }

    public void menuUpdateUsers() {
        System.out.println("==Изменение пользователя==");
        System.out.print("Логин пользователя --> ");
        Scanner sc = new Scanner(System.in);
        String login = sc.nextLine();
        User user = service.getUser(login);
        if (user == null) {
            System.out.println("==Пользователь не найден==");
            return;
        }
        System.out.println(user);
        System.out.println("1 - Изменить логин");
        System.out.println("2 - Не менять логин");
        System.out.print("--> ");
        String res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.print("Новый логин --> ");
                String newLogin = sc.nextLine();
                user.setLogin(newLogin);
                break;
            case "2":
                break;
            default:
                System.out.println("==Вы ввели неверное число==");
        }
        System.out.println("1 - Изменить пароль");
        System.out.println("2 - Не менять пароль");
        System.out.print("--> ");
        res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.print("Новый пароль --> ");
                String newPassword = sc.nextLine();
                user.setPassword(newPassword);
                break;
            case "2":
                break;
            default:
                System.out.println("==Вы ввели неверное число==");
        }
        System.out.println("1 - Изменить роль");
        System.out.println("2 - Не менять роль");
        System.out.print("--> ");
        res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.println("Админ - 1");
                System.out.println("Менеджер - 2");
                System.out.println("Пользователь - 3");
                System.out.print("--> ");
                String input = sc.nextLine();
                switch (input) {
                    case "1":
                        user.setRole(ADMIN);
                        break;
                    case "2":
                        user.setRole(MANAGER);
                        break;
                    case "3":
                        user.setRole(USER);
                        break;
                    default:
                        System.out.println("==Вы ввели неверное число==");
                        return;
                }
            case "2":
                break;
            default:
                System.out.println("==Вы ввели неверное число==");
        }
        boolean isUpdateUser = service.updateUser(user, login);
        if (Boolean.TRUE.equals(isUpdateUser)) {
            log.info("User changed user " + user.getLogin());
            System.out.println("==Пользователь изменен==");
        } else {
            log.info("User not changed user");
            System.out.println("==Пользователь не изменен==");
        }
    }

    public void menuDeleteUsers() {
        System.out.println("==Удаление пользователя==");
        System.out.print("Логин пользователя --> ");
        Scanner sc = new Scanner(System.in);
        String login = sc.nextLine();
        User user = service.getUser(login);
        if (user == null) {
            System.out.println("==Пользователь не найден");
            return;
        }
        boolean isDeleteUser = service.deleteUser(user);
        if (Boolean.TRUE.equals(isDeleteUser)) {
            log.info("User deleted user " + user.getLogin());
            System.out.println("==Пользователь удален==");
        } else {
            log.info("User not deleted user");
            System.out.println("==Пользователь не удален==");
        }
    }

    public void editNumberMenu() {
        while (true) {
            System.out.println("==Меню работы с номерами==");
            System.out.println("1 - Посмотреть все номера");
            System.out.println("2 - Изименить номер");
            System.out.println("3 - Вернуться в меню");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User watches numbers");
                    System.out.println(service.readNumberAll());
                    break;
                case "2":
                    log.info("User updates number");
                    menuUpdateNumber();
                    break;
                case "3":
                    log.info("User returned menu");
                    return;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void menuUpdateNumber() {
        System.out.println("==Изменение номера==");
        System.out.print("Номер коменаты --> ");
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        Number number = service.readNumber(num);
        if (number == null) {
            System.out.println("==Номер не найден==");
            return;
        }
        System.out.println(number);
        System.out.println("1 - Изменить класс номера");
        System.out.println("2 - Не менять класс номера");
        System.out.print("--> ");
        String res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.println("Стандарт - 1");
                System.out.println("Люкс - 2");
                System.out.print("--> ");
                String input = sc.nextLine();
                switch (input) {
                    case "1":
                        number.setClassRoom(ClassRoom.STANDART);
                        break;
                    case "2":
                        number.setClassRoom(ClassRoom.SUITE);
                        break;
                    default:
                        System.out.println("==Вы ввели неверное число==");
                        return;
                }
        }
        System.out.println("1 - Изменить статус номера");
        System.out.println("2 - Не менять статус номера");
        System.out.print("--> ");
        res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.println("Свободен - 1");
                System.out.println("Заенят - 2");
                System.out.print("--> ");
                String input = sc.nextLine();
                switch (input) {
                    case "1":
                        number.setStatus(Status.FREE);
                        break;
                    case "2":
                        number.setStatus(Status.NOT_FREE);
                        break;
                    default:
                        System.out.println("==Вы ввели неверное число==");
                        return;
                }
        }
        boolean isUpdateNumber = service.updateNumber(number);
        if (Boolean.TRUE.equals(isUpdateNumber)) {
            log.info("User changed number " + number.getNumberRoom());
            System.out.println("==Номер изменен==");
        } else {
            log.info("User not changed number");
            System.out.println("==Номер не изменен==");
        }
    }

    public void menuUser(User user) {
        while (true) {
            System.out.println("==Меню==");
            System.out.println("1 - Посмотреть номера");
            System.out.println("2 - Сделать заказ");
            System.out.println("3 - Отменить заказ");
            System.out.println("4 - Посмотреть мои заказы");
            System.out.println("5 - Выйти");
            Scanner sc = new Scanner(System.in);
            System.out.print("--> ");
            String res = sc.nextLine();
            switch (res) {
                case "1":
                    log.info("User watches numbers");
                    System.out.println(service.readNumberAll());
                    break;
                case "2":
                    log.info("User create a order");
                    menuUserBuy(user);
                    break;
                case "3":
                    log.info("User delete the order");
                    menuUserReturn(user);
                    break;
                case "4":
                    log.info("The user is viewing their orders");
                    menuUserSee(user);
                    break;
                case "5":
                    log.info("User left the program");
                    System.exit(0);
                    break;
                default:
                    log.info("User entered an invalid number");
                    System.out.println("==Вы ввели неверное число==");
            }
        }
    }

    public void menuUserBuy(User user) {
        while (true) {
            System.out.println("==Создание заказа==");
            System.out.print("Количество мест --> ");
            Scanner sc = new Scanner(System.in);
            int num = Integer.parseInt(sc.nextLine());
            System.out.println("");
            System.out.println("Класс номера стандарт - 1");
            System.out.println("Класс номера люкс - 2");
            System.out.print("--> ");
            String input = sc.nextLine();
            ClassRoom classRoom = null;
            switch (input) {
                case "1":
                    classRoom = ClassRoom.STANDART;
                    break;
                case "2":
                    classRoom = ClassRoom.SUITE;
                    break;
                default:
                    System.out.println("==Вы ввели неверное число==");
                    return;
            }
            List<Number> numbers = service.readNumTwo(num, String.valueOf(classRoom));
            if (numbers == null) {
                System.out.println("==Номер не найден==");
                return;
            } else {
                System.out.println("Свободных подходящих номеров " + numbers.size());
                System.out.print("Введите количество номеров --> ");
                int numRoom = Integer.parseInt(sc.nextLine());
                if (numRoom == 0 || num > numbers.size()) {
                    System.out.println("==Вы ввели неверное количество номеров==");
                    return;
                } else {
                    boolean isCreateOrder = service.createOrder(user.getLogin(), num, numRoom, classRoom);
                    if (Boolean.TRUE.equals(isCreateOrder)) {
                        System.out.println("==Заказ создан==");
                        log.info("User create order (orders)");
                    } else {
                        log.info("User not create order (orders)");
                        System.out.println("==Заказ не создан==");
                    }
                }
            }
            return;
        }
    }

    public void menuUserReturn(User user) {
        System.out.println("==Отмена заказа==");
        System.out.println(service.userOrders(user.getLogin()));
        Scanner sc = new Scanner(System.in);
        int id = Integer.parseInt(sc.nextLine());
        Order order = service.readOrder(id);
        if (order == null) {
            System.out.println("==Заказ не найден==");
            return;
        }
        boolean isDeleteOrder = service.deleteOrder(order);
        if (Boolean.TRUE.equals(isDeleteOrder)) {
            log.info("User delete order");
            System.out.println("==Заказ отменен==");
        } else {
            log.info("User not delete order");
            System.out.println("==Заказ не отменен==");
        }
    }

    public void menuUserSee(User user) {
        System.out.println(service.userOrders(user.getLogin()));
    }

    public void menuSeeNumbers() {
        System.out.println(service.readNumberAll());
    }
}
