package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    public static final String CREATE_USERS_TABLE = "create table if not exists user (" +
            "id BIGINT unsigned AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(15) NOT NULL," +
            "lastName VARCHAR(15) NOT NULL," +
            "age TINYINT NOT NULL);";
    public static final String DROP_USERS_TABLE = "drop table if exists user;";
    public static final String SAVE_USER = "insert into user (name, lastName, age) values (?, ?, ?);";
    public static final String REMOVE_USER = "delete from user where id = ?";
    public static final String GET_ALL_USERS = "select * from user";
    public static final String CLEAN_USERS_TABLE = "truncate table user";

    private Connection connection;

    public UserDaoJDBCImpl() {
    }

    public UserDaoJDBCImpl(Connection connection) {

        this.connection = connection;
    }

    public void createUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USERS_TABLE)) {
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(DROP_USERS_TABLE)) {
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException te) {
                te.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.execute();

            connection.commit();

            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(REMOVE_USER)) {
            ps.setLong(1, id);
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException te) {
                te.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            rs.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException re) {
                re.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CLEAN_USERS_TABLE)) {
            ps.execute();
            ps.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException re) {
                re.printStackTrace();
            }
        }
    }
}