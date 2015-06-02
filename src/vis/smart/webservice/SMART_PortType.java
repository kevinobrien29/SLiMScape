/**
 * SMART_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public interface SMART_PortType extends java.rmi.Remote {
    public vis.smart.webservice.ProteinFeatures doSMART(vis.smart.webservice.Input_protein SMARTAnalysis) throws java.rmi.RemoteException, vis.smart.webservice.SMARTFault;
}
