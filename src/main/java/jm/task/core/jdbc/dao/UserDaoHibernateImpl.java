package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String table = "create table if not exists user (" +
                "id BIGINT unsigned AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(15) NOT NULL," +
                "lastName VARCHAR(15) NOT NULL," +
                "age TINYINT NOT NULL);";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(table).executeUpdate();
            transaction.commit();
            System.out.println("Создана таблица.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Косяк при создании таблицы :( ...");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists user").executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно удалена");
        } catch (Exception e) {
            System.out.println("Есть проблемы при удалении таблицы...");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь с именем: " + name + " добавлен в БД");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении пользователя");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь с id = " + id + "удалён из БД");
        } catch (HibernateException ex) {
            System.out.println("Ошибка при удалении пользователя по id");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            System.out.println("Список всех пользователей получен.");
            return session.createQuery("from User").list();
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка пользователей.");
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
