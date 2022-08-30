package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
//        List<User> users = new ArrayList<>();
//        users.add(new User("Dave", "Minion", (byte) 44));
        userService.saveUser("Dave", "Minion", (byte) 44);
        userService.saveUser("Stuart", "Minion", (byte) 37);
        userService.saveUser("Kevin", "Minion", (byte) 42);
        userService.saveUser("Иван", "Андреев", (byte) 15);

        userService.removeUserById(3);
        userService.getAllUsers();
        for (User user: userService.getAllUsers()) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
        }
        for (User user: userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
