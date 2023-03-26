package by.pleshkov;

import by.pleshkov.controller.Controller;
import by.pleshkov.controller.ControllerImpl;
import by.pleshkov.model.Number;
import by.pleshkov.model.Order;
import by.pleshkov.model.User;
import by.pleshkov.repository.*;
import by.pleshkov.service_hotel.Service;

public class Main {
//    static Logger LOGGER;

//    static {
//        try (FileInputStream ins = new FileInputStream("C:\\IdeaProjects\\Final_Proekt\\src\\log.config")) {
//            LogManager.getLogManager().readConfiguration(ins);
//            LOGGER = Logger.getLogger(Main.class.getName());
//        } catch (Exception ignore) {
//            ignore.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        IUserRepository<User> userRepository = new UserRepository();
        INumberRepository<Number> numberRepository = new NumberRepository();
        IOrderRepository<Order> orderIOrderRepository = new OrderRepository();
        Service service = new Service(userRepository, numberRepository, orderIOrderRepository);
        Controller controller = new ControllerImpl(service);
        controller.start();
    }
}
