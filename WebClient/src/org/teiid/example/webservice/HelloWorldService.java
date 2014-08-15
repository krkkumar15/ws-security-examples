package org.teiid.example.webservice;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.DispatchImpl;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;

/**
 * This class was generated by Apache CXF 2.6.6
 * 2014-08-13T07:45:27.030-05:00
 * Generated source version: 2.6.6
 * 
 */
@WebServiceClient(name = "HelloWorldService", 
                  wsdlLocation = "http://primary.example.com:8080/kerberos/HelloWorld?wsdl",
                  targetNamespace = "http://webservices.samples.jboss.org/") 
public class HelloWorldService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservices.samples.jboss.org/", "HelloWorldService");
    public final static QName HelloWorldPort = new QName("http://webservices.samples.jboss.org/", "HelloWorldPort");
    static {
        URL url = null;
        try {
            url = new URL("http://primary.example.com:8080/kerberos/HelloWorld?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HelloWorldService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://primary.example.com:8080/kerberos/HelloWorld?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public HelloWorldService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelloWorldService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloWorldService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloWorldService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloWorldService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloWorldService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldPort")
    public HelloWorld getHelloWorldPort() {
        return super.getPort(HelloWorldPort, HelloWorld.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldPort")
    public HelloWorld getHelloWorldPort(WebServiceFeature... features) {
        return super.getPort(HelloWorldPort, HelloWorld.class, features);
    }

    public static void main(String[] args) throws Exception {
    	
        SpringBusFactory bf = new SpringBusFactory();
        URL busFile = HelloWorldService.class.getResource("client.xml");

        Bus bus = bf.createBus(busFile.toString());

		HelloWorldService hws = new HelloWorldService();
		HelloWorld port = hws.getHelloWorldPort();
		port.sayHello("Ramesh Reddy..");
        
        
        JaxWsClientFactoryBean instance = new JaxWsClientFactoryBean();
        Configurer configurer = bus.getExtension(Configurer.class);
        configurer.configureBean(HelloWorldPort.toString() + ".jaxws-client.proxyFactory", instance); //$NON-NLS-1$
        
        SpringBusFactory.setDefaultBus(bus);
        SpringBusFactory.setThreadDefaultBus(bus);
        
        Service svc = Service.create(SERVICE);
        svc.addPort(HelloWorldPort, SOAPBinding.SOAP11HTTP_BINDING, "http://primary.example.com:8080/kerberos/HelloWorld");
        Dispatch<StreamSource> dispatch = svc.createDispatch(HelloWorldPort, StreamSource.class, Mode.PAYLOAD);

		Client client = ((DispatchImpl)dispatch).getClient();
		Endpoint ep = client.getEndpoint();
		ep.putAll(instance.getProperties());
		ep.getOutInterceptors().addAll(instance.getOutInterceptors());
		//ep.getActiveFeatures().addAll(instance.getFeatures());
		ep.getInFaultInterceptors().addAll(instance.getInFaultInterceptors());
		ep.getInInterceptors().addAll(instance.getInInterceptors());
		ep.getOutFaultInterceptors().addAll(instance.getOutFaultInterceptors());
		
		dispatch.getRequestContext().putAll(instance.getProperties());
		
		StreamSource source = new StreamSource(new StringReader("<tns:sayHello xmlns:tns=\"http://webservices.samples.jboss.org/\"><arg0 xmlns=\"\">ramesh</arg0></tns:sayHello>")); //$NON-NLS-1$
		StreamSource result = dispatch.invoke(source);
		source.getInputStream().close();

		bus.shutdown(true);    	
    }
   
}
