package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import org.apache.thrift.TException;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.MainService;

public class MainServiceHandler implements MainService.Iface {

	@Override
	public int add(int n1, int n2) throws TException {
		System.out.println("I'm adding " + n1 + " and " + n2);
		return n1 + n2;
	}

	@Override
	public int sub(int n1, int n2) throws TException {
		System.out.println("I'm subtracting " + n1 + " and " + n2);
		return n1 - n2;
	}
}
