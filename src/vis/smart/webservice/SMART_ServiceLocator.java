/**
 * SMART_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public class SMART_ServiceLocator extends org.apache.axis.client.Service implements vis.smart.webservice.SMART_Service {

// Use to get a proxy class for SMART
private java.lang.String SMART_address = "http://smart.embl.de/webservice/handler.cgi";


    // The WSDD service name defaults to the port name.
    private java.lang.String SMARTWSDDServiceName = "SMART";

    private java.util.HashSet ports = null;

    /**
	 * SMART webservice
	 */
	
	    public SMART_ServiceLocator() {
	    }

    public SMART_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public SMART_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (vis.smart.webservice.SMART_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	vis.smart.webservice.SMART_serviceStub _stub = new vis.smart.webservice.SMART_serviceStub(new java.net.URL(SMART_address), this);
                _stub.setPortName(getSMARTWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SMART".equals(inputPortName)) {
            return getSMART();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", "SMART"));
        }
        return ports.iterator();
    }

    @Override
	public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", "SMART");
    }

    public vis.smart.webservice.SMART_PortType getSMART() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SMART_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSMART(endpoint);
    }

    public vis.smart.webservice.SMART_PortType getSMART(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            vis.smart.webservice.SMART_serviceStub _stub = new vis.smart.webservice.SMART_serviceStub(portAddress, this);
            _stub.setPortName(getSMARTWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public java.lang.String getSMARTAddress() {
        return SMART_address;
    }

    public java.lang.String getSMARTWSDDServiceName() {
        return SMARTWSDDServiceName;
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SMART".equals(portName)) {
            setSMARTEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

    public void setSMARTEndpointAddress(java.lang.String address) {
        SMART_address = address;
    }

    public void setSMARTWSDDServiceName(java.lang.String name) {
        SMARTWSDDServiceName = name;
    }

}
