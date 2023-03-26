package by.pleshkov.service_hotel;

import by.pleshkov.constant.ClassRoom;
import by.pleshkov.constant.Status;
import by.pleshkov.constant.StatusOrder;
import by.pleshkov.model.Number;
import by.pleshkov.model.Order;
import by.pleshkov.model.User;
import by.pleshkov.repository.INumberRepository;
import by.pleshkov.repository.IOrderRepository;
import by.pleshkov.repository.IUserRepository;
import by.pleshkov.util.HashPassword;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Service {
    private final IUserRepository<User> userRepository;
    private final INumberRepository<Number> numberRepository;
    private final IOrderRepository<Order> orderRepository;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());


    public Service(IUserRepository userRepository, INumberRepository numberRepository, IOrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.numberRepository = numberRepository;
        this.orderRepository = orderRepository;
    }

    public boolean createUser(User user) {
        try {
            if (getUser(user.getLogin()) == null) {
                user.setPassword(HashPassword.hashPassword(user.getPassword()));
                if (userRepository.create(user)) {
                    log.log(Level.INFO, "Пользователь не создан");
                }
            } else {
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public boolean deleteUser(User user) {
        try {
            if (userRepository.delete(user)) {
                log.log(Level.INFO, "User не удален");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserLogIn(String login, String password) {
        User user = userRepository.read(login);
        try {
            password = HashPassword.hashPassword(password);
            if (user.getLogin() == null || !user.getPassword().equals(password)) {
                return null;
            }
        } catch (
                NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User getUser(String login) {
        User user = userRepository.read(login);
        if (user.getLogin() == null) {
            return null;
        } else {
            return user;
        }
    }


    public boolean updateUser(User user, String login) {
        try {
            String newPassword = HashPassword.hashPassword(user.getPassword());
            user.setPassword(newPassword);
            if (userRepository.update(user)) {
                log.log(Level.INFO, "Пользователь не обновлен");
            }
            List<Number> numbers = userNumbers(login);
            for (Number number : numbers) {
                number.setUser(user.getLogin());
                if (numberRepository.update(number)) {
                    log.log(Level.INFO, "Номер не обновлен");
                }
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public List<User> readUserAll() {
        return userRepository.readAll();
    }

//    public boolean createMovie(Movie movie) {
//        try {
//            if (movieRepository.create(movie)) {
//                log.log(Level.INFO, "Фильм не создан");
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public Number readNumber(int input) {
        Number number = numberRepository.read(input);
        if (number.getNumberRoom() == 0) {
            log.log(Level.INFO, "Номер не найден");
            return null;
        } else {
            return number;
        }
    }

    public boolean updateNumber(Number number) {
        try {
            if (numberRepository.update(number)) {
                log.log(Level.INFO, "Номер не обновлен");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public boolean deleteMovie(Movie movie) {
//        try {
//            for (int i = 0; i < movie.getNumberTickets(); i++) {
//                Ticket ticket = ticketRepository.read(movie.getTitle());
//                if (ticketRepository.delete(ticket)) {
//                    log.log(Level.INFO, "Билет не удален");
//                }
//            }
//            if (movieRepository.delete(movie)) {
//                log.log(Level.INFO, "Фильм не удален");
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public List<Number> readNumberAll() {
        return numberRepository.readAll();
    }

    public List<Number> readNumTwo(int num, String classRoom) {
        List<Number> numbers = numberRepository.readAll();
        final List<Number> filterNumbers = numbers.stream()
                .filter(number -> number.getClassRoom().equals(classRoom))
                .filter(number -> number.getPlaces() == num)
                .collect(Collectors.toList());
        return filterNumbers;
    }

    public boolean createOrder(String login, int num, int numRoom, ClassRoom classRoom) {
        try {
            for (int i = 0; i < numRoom; i++) {
                List<Order> orders = orderRepository.readAll();
                Order order = new Order(login, num, classRoom, StatusOrder.NEW);
                if (orderRepository.update(order)) {
                    log.log(Level.INFO, "Ордер не создан");
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public int freeTickets(String input) {
//        List<Ticket> tickets = readMovie(input).getTicket();
//        final List<Ticket> filterTickets = tickets.stream()
//                .filter(ticket -> ticket.getStatus().equals(Status.FREE))
//                .collect(Collectors.toList());
//        return filterTickets.size();
//    }

    public List<Number> userNumbers(String input) {
        List<Number> numbers = numberRepository.readAll();
        final List<Number> filterNumbers = numbers.stream()
                .filter(number -> number.getUser().equals(input))
                .collect(Collectors.toList());
        return filterNumbers;
    }

    public boolean updateOrder(Order order) {
        try {
            if (orderRepository.update(order)) {
                log.log(Level.INFO, "Заказ не обновлен");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Order readOrder(int id) {
        List<Order> orders = orderRepository.readAll();
        Order order = (Order) orders.stream()
                .filter(order1 -> order1.getId() == id)
                .findFirst()
                .orElse(null);
        return order;
    }


    public boolean deleteOrder(Order order) {
        try {
            if (orderRepository.delete(order)) {
                log.log(Level.INFO, "Заказ не удален");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Order> userOrders(String input) {
        List<Order> orders = orderRepository.readAll();
        final List<Order> filterOrders = orders.stream()
                .filter(order -> order.getUser().equals(input))
                .collect(Collectors.toList());
        return filterOrders;
    }

    public List<Order> readOrderAll() {
        return orderRepository.readAll();
    }


//    public boolean returnTickets(String login, String title, int place) {
//        try {
//            List<Ticket> tickets = ticketRepository.readAll();
//            Ticket ticket = (Ticket) tickets.stream()
//                    .filter(ticket1 -> ticket1.getUser().equals(login))
//                    .filter(ticket1 -> ticket1.getMovie().equals(title))
//                    .filter(ticket1 -> ticket1.getPlaceNumber() == place)
//                    .findFirst()
//                    .orElse(null);
//            if (ticket != null) {
//                ticket.setUser("-");
//                ticket.setStatus(Status.FREE);
//                if (ticketRepository.update(ticket)) {
//                    log.log(Level.INFO, "Билет не обновлен");
//                }
//                return true;
//            } else {
//                System.out.println("wrong place");
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
