package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import java.util.Iterator;
import java.util.List;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.MainService;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDaoImpl;

public class MyServer {

    public static void StartsimpleServer(MainService.Processor<MainServiceHandler> processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(
                    new Args(serverTransport).processor(processor));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new
            // TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testDatabase() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        OutcomeDaoImpl out_dao = new OutcomeDaoImpl();
        out_dao.setSessionFactory(sessionFactory);
        List<Outcome> outcomes = out_dao.findAllOutcomes();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Outcome outcome = (Outcome) iterator.next();
            System.out.println(outcome);
        }

        IncomeDaoImpl in_dao = new IncomeDaoImpl();
        in_dao.setSessionFactory(sessionFactory);
        List<Outcome> incomes = in_dao.findAllIncomes();
        for (Iterator iterator = incomes.iterator(); iterator.hasNext();) {
            Income income = (Income) iterator.next();
            System.out.println(income);
        }
    }

    public static void main(String[] args) {
        testDatabase();
        StartsimpleServer(new MainService.Processor<MainServiceHandler>(new MainServiceHandler()));
    }
}
