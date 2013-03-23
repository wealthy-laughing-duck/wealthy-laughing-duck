package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;

public class IncomeDaoImpl implements IncomeDao {

    private SessionFactory sessionFactory;

    private Session session = null;

    private Transaction tx = null;
         
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Income income) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(income);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Income income) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(income);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Income income) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.delete(income);
        tx.commit();
        session.close();
    }

    @Override
    public List findAllIncomes() {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List list = session.createQuery("FROM Income").list();
        tx.commit();
        session.close();
        return list;
    }

    @Override
    public Income findByIncomeId(Long id) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Income song = (Income) session.get(Income.class, new Long(id));
        tx.commit();
        session.close();
        return song;
    }
}
