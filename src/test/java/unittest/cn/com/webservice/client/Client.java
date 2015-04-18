package unittest.cn.com.webservice.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.jms.spec.JMSSpecConstants;

import cn.com.webservice.server.Calculator;

/**
 * @author wuliwei
 *
 */
public class Client {
	
	/**
	 * @return Calculator 
	 */
	public static Calculator method1() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(Calculator.class);
		factory.setAddress("http://localhost/WebService_cxf/Calculator");
		return (Calculator)factory.create();
	}
	
	/**
	 * @return Calculator 
	 */
	public static Calculator method2() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Calculator.class);
		String address = "jms:jndi:dynamicQueues/test.cxf.jmstransport.queue3"
				+ "?jndiInitialContextFactory"
				+ "=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
				+ "&jndiConnectionFactoryName=ConnectionFactory&jndiURL=tcp://localhost:61500";
		factory.setAddress(address);
		factory.setTransportId(JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID);
		return (Calculator)factory.create();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calculator cal = method2();//method1();

		System.out.println("1 + 2 = " + cal.addInteger(1, 2));
		System.out.println("null + 2 = " + cal.addInteger(null, 2));
		System.out.println("1 + null = " + cal.addInteger(1, null));
		System.out.println("abc + def = " + cal.addString("abc", "def"));
		System.out.println("null + def = " + cal.addString(null, "def"));
		System.out.println("abc + null = " + cal.addString("abc", null));
		List<String> ss = new ArrayList<String>(), sa = new ArrayList<String>();
		ss.add("abc");
		sa.add("def");
		ss.add("ABCabc");
		sa.add("DEFdef");
		sa.add("DEFdefGHI");
		System.out.println(Arrays.toString(ss.toArray()) + " + " + Arrays.toString(sa.toArray()) + " = " + Arrays.toString(cal.addStringArray(ss.toArray(new String[0]), sa.toArray(new String[0]))));
		ss = new ArrayList<String>();
		sa = new ArrayList<String>();
		ss.add("abc");
		sa.add(null);
		ss.add("ABCabc");
		sa.add("DEFdef");
		ss.add("DEFdefGHI");
		System.out.println(Arrays.toString(ss.toArray()) + " + " + Arrays.toString(sa.toArray()) + " = " + Arrays.toString(cal.addStringArray(ss.toArray(new String[0]), sa.toArray(new String[0]))));
		ss = new ArrayList<String>();
		sa = new ArrayList<String>();
		ss.add("abc");
		sa.add("def");
		ss.add(null);
		sa.add("DEFdef");
		ss.add("DEFdefGHI");
		System.out.println(Arrays.toString(ss.toArray()) + " + " + Arrays.toString(sa.toArray()) + " = " + Arrays.toString(cal.addStringArray(ss.toArray(new String[0]), sa.toArray(new String[0]))));
		List<Integer> is = new ArrayList<Integer>(), ia = new ArrayList<Integer>();
		is.add(5);
		ia.add(6);
		is.add(1);
		ia.add(7);
		ia.add(8);
		System.out.println(Arrays.toString(is.toArray()) + " + " + Arrays.toString(ia.toArray()) + " = " + Arrays.toString(cal.addIntegerArray(is.toArray(new Integer[0]), ia.toArray(new Integer[0]))));
		is = new ArrayList<Integer>();
		ia = new ArrayList<Integer>();
		is.add(null);
		ia.add(6);
		is.add(1);
		ia.add(7);
		is.add(8);
		System.out.println(Arrays.toString(is.toArray()) + " + " + Arrays.toString(ia.toArray()) + " = " + Arrays.toString(cal.addIntegerArray(is.toArray(new Integer[0]), ia.toArray(new Integer[0]))));
		is = new ArrayList<Integer>();
		ia = new ArrayList<Integer>();
		is.add(5);
		ia.add(6);
		is.add(1);
		ia.add(null);
		ia.add(8);
		System.out.println(Arrays.toString(is.toArray()) + " + " + Arrays.toString(ia.toArray()) + " = " + Arrays.toString(cal.addIntegerArray(is.toArray(new Integer[0]), ia.toArray(new Integer[0]))));
	}

}
