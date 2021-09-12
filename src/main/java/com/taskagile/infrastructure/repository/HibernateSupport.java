package com.taskagile.infrastructure.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;

public abstract class HibernateSupport<T> {

    final EntityManager entityManager;

    protected HibernateSupport (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void save(T object) {
        entityManager.persist(object);
        entityManager.flush();
    }
}
