/**
 * ProteinFeaturesFeatureType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public class ProteinFeaturesFeatureType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    public static final java.lang.String _SMART = "SMART";

    public static final java.lang.String _PFAM = "PFAM";
    public static final java.lang.String _INTRINSIC = "INTRINSIC";
    public static final ProteinFeaturesFeatureType SMART = new ProteinFeaturesFeatureType(_SMART);
    public static final ProteinFeaturesFeatureType PFAM = new ProteinFeaturesFeatureType(_PFAM);
    public static final ProteinFeaturesFeatureType INTRINSIC = new ProteinFeaturesFeatureType(_INTRINSIC);
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProteinFeaturesFeatureType.class);
    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">>>proteinFeatures>feature>type"));
    }
    public static ProteinFeaturesFeatureType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public static ProteinFeaturesFeatureType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ProteinFeaturesFeatureType enumeration = (ProteinFeaturesFeatureType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }
    // Constructor
    protected ProteinFeaturesFeatureType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }
    @Override
	public boolean equals(java.lang.Object obj) {return (obj == this);}
    public java.lang.String getValue() { return _value_;}
    @Override
	public int hashCode() { return toString().hashCode();}

    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    @Override
	public java.lang.String toString() { return _value_;}

}
