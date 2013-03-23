package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import java.util.Iterator;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.FinanceService;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TIncome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TOutcome;

public class MyClient {

    private TTransport transport;
    private TProtocol protocol;
    private FinanceService.Client client;

    public MyClient() throws TTransportException {
        transport = new TSocket("localhost", 9090);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        client = new FinanceService.Client(protocol);
    }

    public void close() {
        transport.close();
    }

    public void processGetUserOutcomes() throws TException {
        List<TOutcome> list = client.getUserOutcomes(1);
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TOutcome t_outcome = (TOutcome) it.next();
            System.out.println(t_outcome.getAmount() + ", " + t_outcome.getCategory()
                    + ", " + t_outcome.getUser() + ", " + t_outcome.getComment());
        }
        System.out.println("Total results: " + list.size());
    }

    public void processGetUserIncomes() throws TException {
        List<TIncome> list = client.getUserIncomes(1);
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TIncome t_income = (TIncome) it.next();
            System.out.println(t_income.getAmount() + ", " + t_income.getCategory()
                    + ", " + t_income.getUser() + ", " + t_income.getComment());
        }
        System.out.println("Total results: " + list.size());
    }

    public static void main(String[] args) {
        try {
            MyClient myClient = new MyClient();
            myClient.processGetUserOutcomes();
            myClient.processGetUserIncomes();
            myClient.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
