package com.example.loadinglistview;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;

import com.ms.service.TestService;

public class aa {
public static void main(String[] args) throws Exception{
	THttpClient thc = null;
	TProtocol loPFactory = null;
//	String formalUrl = "http://192.168.1.103:8080/thrifttest/androidServlet";
	String formalUrl = "http://192.168.1.103:8080/thrifttest/androidServlet";
	thc = new THttpClient(formalUrl);
	loPFactory = new TCompactProtocol(thc);
	TestService.Client client = new TestService.Client(loPFactory);
	System.out.println(client.doLogin("111"));
}
}
