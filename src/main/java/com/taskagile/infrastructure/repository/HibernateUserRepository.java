package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateUserRepository extends HibernateSupport<User> implements UserRepository {

    public HibernateUserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User findByUsername(String username) {
        Query<User> query = getSession().createQuery("select u from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        Query<User> query = getSession().createQuery("select u from User u where u.emailAddress = :emailAddress", User.class);
        query.setParameter("emailAddress", emailAddress);
        return query.uniqueResult();
    }

    @Override
    public User findById(UserId userId) {
        Query<User> query = getSession().createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", userId.value());
        return query.uniqueResult();
    }
}
