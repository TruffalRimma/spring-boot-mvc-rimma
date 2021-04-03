package ru.spring.boot.jm.springbootmvcrimma.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.boot.jm.springbootmvcrimma.model.Role;
import ru.spring.boot.jm.springbootmvcrimma.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    public User loadUserByUsername(String username) {
        return entityManager.createQuery(
                "SELECT u from User u WHERE u.username = :username", User.class).
                setParameter("username", username).getSingleResult();
    }

    public List<User> getUsers() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    public User getUserByID(Long id) {
        return entityManager.find(User.class, id);
    }

    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(entityManager.find(Role.class, 1L));
        user.setRoles(roles);
        entityManager.persist(user);
        String sql = "insert into security_boot.user_roles values (:id, 1)";
        Query q = entityManager.createNativeQuery(sql).setParameter("id", user.getId());
        q.executeUpdate();
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void delete(User user) {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

        String sql2 = "delete from security_boot.user_roles where user_id= :id";
        Query q2 = entityManager.createNativeQuery(sql2).setParameter("id", user.getId());
        q2.executeUpdate();

        String sql = "delete from security_boot.users where id= :id";
        Query q = entityManager.createNativeQuery(sql).setParameter("id", user.getId());
        q.executeUpdate();

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }
}
