package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.blogspot.symfonyworld.wealthylaughingduck.dao.CategoryDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.CategoryDaoImpl;
import org.apache.thrift.TProcessor;

public class Server {

    private Logger logger;

    private Server() {
        logger = LoggerFactory.getLogger("Server");
    }

    private void start() {
        try {
            // construct session factory
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            // construct data access objects
            OutcomeDao outcomeDao = new OutcomeDaoImpl();
            outcomeDao.setSessionFactory(sessionFactory);
            IncomeDao incomeDao = new IncomeDaoImpl();
            incomeDao.setSessionFactory(sessionFactory);
            UserDao userDao = new UserDaoImpl();
            userDao.setSessionFactory(sessionFactory);
            CategoryDao categoryDao = new CategoryDaoImpl();
            categoryDao.setSessionFactory(sessionFactory);

            // construct data provider and set daos
            DataProvider dataProvider = new RealDataProvider();
            dataProvider.setDaos(outcomeDao, incomeDao, userDao, categoryDao);

            TProcessor processor = new FinanceService.Processor<>(
                    new FinanceServiceHandler(dataProvider));
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new
            // TThreadPoolServer.Args(serverTransport).processor(processor));

            logger.info("Starting simple server");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
