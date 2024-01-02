package web.application.users.dao;

import web.application.users.models.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    User getUserById(long id);
    void addUser(User user);
    void updateUserById(long id, User user);
    void removeUserById(long id);
}
