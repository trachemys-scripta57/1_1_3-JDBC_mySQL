package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Dave", "Minion", (byte) 44);
        userService.saveUser("Stuart", "Minion", (byte) 37);
        userService.saveUser("Kevin", "Minion", (byte) 42);
        userService.saveUser("Иван", "Андреев", (byte) 15);

        userService.getAllUsers();
        for(User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
