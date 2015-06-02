/**
 * SMARTFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public class SMARTFault  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private int faultcode;

    private java.lang.String faultstring;

    private java.lang.Object __equalsCalc = null;

    private boolean __hashCodeCalc = false;


    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SMARTFault.class, true);


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">SMARTFault"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faultcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultstring");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faultstring"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }


    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }


    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }
    public SMARTFault() {
    }

    public SMARTFault(
           int faultcode,
           java.lang.String faultstring) {
        this.faultcode = faultcode;
        this.faultstring = faultstring;
    }
    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SMARTFault)) return false;
        SMARTFault other = (SMARTFault) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.faultcode == other.getFaultcode() &&
            ((this.faultstring==null && other.getFaultstring()==null) || 
             (this.faultstring!=null &&
              this.faultstring.equals(other.getFaultstring())));
        __equalsCalc = null;
        return _equals;
    }

    /**
     * Gets the faultcode value for this SMARTFault.
     * 
     * @return faultcode
     */
    public int getFaultcode() {
        return faultcode;
    }

    /**
     * Gets the faultstring value for this SMARTFault.
     * 
     * @return faultstring
     */
    public java.lang.String getFaultstring() {
        return faultstring;
    }

    @Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getFaultcode();
        if (getFaultstring() != null) {
            _hashCode += getFaultstring().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    /**
     * Sets the faultcode value for this SMARTFault.
     * 
     * @param faultcode
     */
    public void setFaultcode(int faultcode) {
        this.faultcode = faultcode;
    }

    /**
     * Sets the faultstring value for this SMARTFault.
     * 
     * @param faultstring
     */
    public void setFaultstring(java.lang.String faultstring) {
        this.faultstring = faultstring;
    }


    /**
     * Writes the exception data to the faultDetails
     */
    @Override
	public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
