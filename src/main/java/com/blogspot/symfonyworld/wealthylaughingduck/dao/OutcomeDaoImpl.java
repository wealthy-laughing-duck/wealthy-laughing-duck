package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;

public class OutcomeDaoImpl implements OutcomeDao {

    private SessionFactory sessionFactory;

    private Session session = null;

    private Transaction tx = null;
         
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Outcome outcome) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(outcome);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Outcome outcome) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(outcome);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Outcome outcome) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.delete(outcome);
        tx.commit();
        session.close();
    }

    @Override
    public List findAllOutcomes() {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List list = session.createQuery("FROM Outcome").list();
        tx.commit();
        session.close();
        return list;
    }

    @Override
    public Outcome findByOutcomeId(long id) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Outcome song = (Outcome) session.get(Outcome.class, new Long(id));
        tx.commit();
        session.close();
        return song;
    }

    @Override
    public List<Outcome> findByUserId(long id) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Query query = session.createQuery(
                "FROM Outcome WHERE created_by = :created_by");
        query.setParameter("created_by", id);
        List list = query.list();
        tx.commit();
        session.close();
        return list;
    }
}
