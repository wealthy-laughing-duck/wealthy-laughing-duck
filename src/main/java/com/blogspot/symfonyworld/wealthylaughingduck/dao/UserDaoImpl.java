package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.model.User;

public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    private Session session = null;

    private Transaction tx = null;
         
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
    }

    @Override
    public List findAllUsers() {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List list = session.createQuery("FROM User").list();
        tx.commit();
        session.close();
        return list;
    }
}
