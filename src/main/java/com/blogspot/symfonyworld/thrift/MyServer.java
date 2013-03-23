package com.blogspot.symfonyworld.thrift;

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

import com.blogspot.symfonyworld.thrift.server.MainService;
import com.blogspot.symfonyworld.model.Outcome;

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
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List outcomes = session.createQuery("from Outcome").list();
            for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
                Outcome outcome = (Outcome) iterator.next();
                System.out.println(outcome.getTotalCash());
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        testDatabase();
        StartsimpleServer(new MainService.Processor<MainServiceHandler>(new MainServiceHandler()));
    }
}
