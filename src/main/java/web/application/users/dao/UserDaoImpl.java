package web.application.users.dao;

import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.application.users.models.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.unwrap(Session.class).persist(user);
    }

    @Override
    public void updateUserById(long id, User user) {
        user.setId(id);
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(getUserById(id));
    }
}
