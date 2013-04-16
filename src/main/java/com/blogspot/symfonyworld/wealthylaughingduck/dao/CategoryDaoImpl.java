package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;
import com.blogspot.symfonyworld.wealthylaughingduck.model.CategoryType;
import java.util.Date;

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
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        session.save(category);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Category category) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        category.setUpdatedAt(new Date());
        session.update(category);
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
    public void delete(long id) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        String hql = "DELETE FROM Category WHERE id = :id"; 
        session.createQuery(hql)
            .setString("id", Long.toString(id))
            .executeUpdate();
//        Category c = (Category) session.load(Category.class, id);
//        delete(c);
        tx.commit();
        session.close();
    }

    @Override
    public Category get(long id) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Category category =  (Category) session.get(Category.class, id);
        tx.commit();
        session.close();
        return category;
    }

    @Override
    public List<Category> findAllByType(CategoryType type) {
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

    @Override
    public Category reference(long id) {
        session = sessionFactory.openSession();
        Category category = (Category) session.load(Category.class, id);
        session.close();
        return category;
    }
}
