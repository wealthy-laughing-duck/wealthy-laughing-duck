package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.FinanceService;
import com.blogspot.symfonyworld.wealthylaughingduck.bo.DataProvider;
import com.blogspot.symfonyworld.wealthylaughingduck.bo.RealDataProvider;
import com.blogspot.symfonyworld.wealthylaughingduck.bo.FakeDataProvider;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDaoImpl;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDaoImpl;

public class MyServer {

    public static void StartsimpleServer(FinanceService.Processor<FinanceServiceHandler> processor) {
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

    public static void main(String[] args) {
        // construct session factory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // construct data access objects
        OutcomeDao outcomeDao = new OutcomeDaoImpl();
        outcomeDao.setSessionFactory(sessionFactory);
        IncomeDao incomeDao = new IncomeDaoImpl();
        incomeDao.setSessionFactory(sessionFactory);
        UserDao userDao = new UserDaoImpl();
        userDao.setSessionFactory(sessionFactory);

        // construct data provider and set daos
        DataProvider dataProvider = new RealDataProvider();
        dataProvider.setDaos(outcomeDao, incomeDao, userDao);

        StartsimpleServer(new FinanceService.Processor<FinanceServiceHandler>(
                new FinanceServiceHandler(dataProvider)));
    }
}
