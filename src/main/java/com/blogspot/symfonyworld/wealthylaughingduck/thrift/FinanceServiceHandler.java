package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.thrift.TException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.FinanceService;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TIncome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TOutcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDaoImpl;

public class FinanceServiceHandler implements FinanceService.Iface {

    public FinanceServiceHandler() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    private SessionFactory sessionFactory;

    @Override
    public List<TOutcome> getUserOutcomes(int user_id) throws TException {
        System.out.println("> getUserOutcomes");
        OutcomeDaoImpl dao = new OutcomeDaoImpl();
        dao.setSessionFactory(sessionFactory);
        List<Outcome> outcomes = dao.findByUserId(user_id);
        System.out.println("> found: " + outcomes.size());
        List<TOutcome> result = new ArrayList<TOutcome>();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Outcome outcome = (Outcome) iterator.next();
            TOutcome t_outcome = new TOutcome(outcome.getTotalCash(), outcome.getUser().getName(), String.valueOf(outcome.getCategoryId()));
            t_outcome.setComment(outcome.getComment());
            result.add(t_outcome);
        }
        System.out.println("> results: " + result.size());
        return result;
    }

    @Override
    public List<TIncome> getUserIncomes(int user_id) throws TException {
        System.out.println("> getUserIncomes");
        IncomeDaoImpl dao = new IncomeDaoImpl();
        dao.setSessionFactory(sessionFactory);
        List<Income> outcomes = dao.findByUserId(user_id);
        System.out.println("> found: " + outcomes.size());
        List<TIncome> result = new ArrayList<TIncome>();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Income income = (Income) iterator.next();
            TIncome t_income = new TIncome(income.getAmount(), income.getUser().getName(), String.valueOf(income.getCategoryId()));
            t_income.setComment(income.getDescription());
            result.add(t_income);
        }
        System.out.println("> results: " + result.size());
        return result;
    }
}
