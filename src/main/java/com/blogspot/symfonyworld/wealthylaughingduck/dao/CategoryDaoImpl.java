package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;

public class CategoryDaoImpl implements CategoryDao {

    private SessionFactory sessionFactory;

    private Session session = null;

    private Transaction tx = null;
         
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Category category) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(category);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Category category) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(category);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Category category) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.delete(category);
        tx.commit();
        session.close();
    }

    @Override
    public List<Category> findAllByType(String type) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Query query = session.createQuery(
                "FROM Category WHERE type = :type");
        query.setParameter("type", type);
        List list = query.list();
        tx.commit();
        session.close();
        return list;
    }
}
