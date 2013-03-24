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
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TUser;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDaoImpl;

public class FinanceServiceHandler implements FinanceService.Iface {

    public FinanceServiceHandler() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    private SessionFactory sessionFactory;

    @Override
    public List<TOutcome> getUserOutcomes(int user_id) throws TException {
        System.out.println(">>> getUserOutcomes");
        OutcomeDaoImpl dao = new OutcomeDaoImpl();
        dao.setSessionFactory(sessionFactory);
        List<Outcome> outcomes = dao.findByUserId(user_id);
        System.out.println("> found: " + outcomes.size());
        List<TOutcome> result = new ArrayList<TOutcome>();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Outcome outcome = (Outcome) iterator.next();
            TOutcome t_outcome = new TOutcome(
                    (double)(Math.round(outcome.getAmount() * 100 )) / 100,
                    outcome.getUser().getName(),
                    String.valueOf(outcome.getCategory().getName()));
            t_outcome.setComment(outcome.getComment());
            result.add(t_outcome);
            System.out.println(outcome.getCreatedAt());
        }
        System.out.println("> results: " + result.size());
        return result;
    }

    @Override
    public List<TIncome> getUserIncomes(int user_id) throws TException {
        System.out.println(">>> getUserIncomes");
        IncomeDaoImpl dao = new IncomeDaoImpl();
        dao.setSessionFactory(sessionFactory);
        List<Income> outcomes = dao.findByUserId(user_id);
        System.out.println("> found: " + outcomes.size());
        List<TIncome> result = new ArrayList<TIncome>();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Income income = (Income) iterator.next();
            TIncome t_income = new TIncome(
                    (double)(Math.round(income.getAmount() * 100 )) / 100,
                    income.getUser().getName(),
                    String.valueOf(income.getCategory().getName()));
            t_income.setComment(income.getComment());
            result.add(t_income);
            System.out.println(income.getCreatedAt());
        }
        System.out.println("> results: " + result.size());
        return result;
    }

    @Override
    public List<TUser> getAllUsers() throws TException {
        System.out.println(">>> getUserIncomes");
        UserDaoImpl dao = new UserDaoImpl();
        dao.setSessionFactory(sessionFactory);
        List<User> users = dao.findAllUsers();
        System.out.println("> found: " + users.size());
        List<TUser> result = new ArrayList<TUser>();
        for (Iterator iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            TUser t_user = new TUser(user.getUserName(), user.getName());
            result.add(t_user);
            System.out.println(user.getCreatedAt());
        }
        System.out.println("> results: " + result.size());
        return result;
    }
}
