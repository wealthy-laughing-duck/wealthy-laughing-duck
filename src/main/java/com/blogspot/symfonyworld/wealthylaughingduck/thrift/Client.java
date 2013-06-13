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
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TCategoryType;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TCategory;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TIncome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TOutcome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TUser;

public class Client {

    private TTransport transport;
    private TProtocol protocol;
    private FinanceService.Client client;

    public Client() throws TTransportException {
        transport = new TSocket("localhost", 9090);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        client = new FinanceService.Client(protocol);
    }

    public void close() {
        transport.close();
    }

    protected void processGetUserOutcomes() throws TException {
        List<TOutcome> list = client.getUserOutcomes(1);
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TOutcome t_outcome = (TOutcome) it.next();
            System.out.println(t_outcome.getAmount() + ", " + t_outcome.getCategory()
                    + ", " + t_outcome.getUser() + ", " + t_outcome.getComment());
        }
        System.out.println("Total results: " + list.size());
    }

    protected void processGetUserIncomes() throws TException {
        List<TIncome> list = client.getUserIncomes(1);
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TIncome t_income = (TIncome) it.next();
            System.out.println(t_income.getAmount() + ", " + t_income.getCategory()
                    + ", " + t_income.getUser() + ", " + t_income.getComment());
        }
        System.out.println("Total results: " + list.size());
    }

    protected void processGetAllUsers() throws TException {
        List<TUser> list = client.getAllUsers();
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TUser t_user = (TUser) it.next();
            System.out.println(t_user.getUsername() + " - " + t_user.getFullname());
        }
        System.out.println("Total results: " + list.size());
    }

    protected void processGetIncomeCategoryTree() throws TException {
        List<TCategory> list = client.getCategoryTree(TCategoryType.INCOME);
        processGetCategoryTree(list);
    }

    protected void processGetOutcomeCategoryTree() throws TException {
        List<TCategory> list = client.getCategoryTree(TCategoryType.OUTCOME);
        processGetCategoryTree(list);
    }

    private  void processGetCategoryTree(List<TCategory> list) throws TException {
        System.out.println("Thrift has returned following result:");
        for (Iterator it = list.iterator(); it.hasNext();) {
            TCategory t_category = (TCategory) it.next();
            System.out.println(t_category.getId() + " - " + t_category.getName());
        }
        System.out.println("Total results: " + list.size());
    }

    public static void main(String[] args) {
        try {
            Client myClient = new Client();
            myClient.processGetUserOutcomes();
            myClient.processGetUserIncomes();
            myClient.processGetAllUsers();
            myClient.processGetOutcomeCategoryTree();
            myClient.processGetIncomeCategoryTree();
            myClient.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
