package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.MainService;

public class MyClient {

	public static void main(String[] args) {

		try {
			TTransport transport;

			transport = new TSocket("localhost", 9090);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			MainService.Client client = new MainService.Client(protocol);

			System.out.println(client.add(100, 200));
			System.out.println(client.sub(634, 541));

			transport.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException x) {
			x.printStackTrace();
		}
	}
}
