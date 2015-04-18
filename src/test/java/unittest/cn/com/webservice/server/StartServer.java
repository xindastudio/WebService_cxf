package unittest.cn.com.webservice.server;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.jms.spec.JMSSpecConstants;

import cn.com.webservice.server.Calculator;
import cn.com.webservice.server.CalculatorImpl;

/**
 * @author wuliwei
 *
 */
public class StartServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//method1();
		method2();
	}
	
	/**
	 * 
	 */
	public static void method1() {
		CalculatorImpl cal = new CalculatorImpl();
		JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
		serverFactory.setServiceClass(Calculator.class);
		serverFactory.setAddress("http://localhost/WebService_cxf/Calculator");
		serverFactory.setServiceBean(cal);
		serverFactory.getInInterceptors().add(new LoggingInInterceptor());
		serverFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		serverFactory.create();
	}
	
	/**
	 * 
	 */
	public static void method2() {
		startServiceBroker();
		
		CalculatorImpl cal = new CalculatorImpl();
		JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
		serverFactory.setServiceClass(Calculator.class);
		String address = "jms:jndi:dynamicQueues/test.cxf.jmstransport.queue3"
				+ "?jndiInitialContextFactory"
				+ "=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
				+ "&jndiConnectionFactoryName=ConnectionFactory&jndiURL=tcp://localhost:61500";
		serverFactory.setAddress(address);
		serverFactory.setServiceBean(cal);
		serverFactory.setTransportId(JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID);
		serverFactory.create();
	}
	
	/**
	 * 
	 */
	public static void startServiceBroker() {
		try {
			BrokerService service = new BrokerService();
			service.setPersistenceAdapter(new MemoryPersistenceAdapter());
			service.addConnector("tcp://localhost:61500");
			service.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
