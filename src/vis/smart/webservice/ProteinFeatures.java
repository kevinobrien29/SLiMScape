/**
 * ProteinFeatures.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public class ProteinFeatures  implements java.io.Serializable {
    private java.lang.String protein_id;

    private int protein_length;

    private vis.smart.webservice.ProteinFeaturesFeature[] feature;

    /* HTTP link which can be used to generate the SMART
     *                                     protein schematic in PNG format. */
    private org.apache.axis.types.URI protein_schematic;

    private java.lang.Object __equalsCalc = null;

    private boolean __hashCodeCalc = false;


    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProteinFeatures.class, true);


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">proteinFeatures"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protein_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protein_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protein_length");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protein_length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feature");
        elemField.setXmlName(new javax.xml.namespace.QName("", "feature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">>proteinFeatures>feature"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protein_schematic");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protein_schematic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        elemField.setMinOccurs(0);
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


    public ProteinFeatures() {
    }

    public ProteinFeatures(
           java.lang.String protein_id,
           int protein_length,
           vis.smart.webservice.ProteinFeaturesFeature[] feature,
           org.apache.axis.types.URI protein_schematic) {
           this.protein_id = protein_id;
           this.protein_length = protein_length;
           this.feature = feature;
           this.protein_schematic = protein_schematic;
    }

    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProteinFeatures)) return false;
        ProteinFeatures other = (ProteinFeatures) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.protein_id==null && other.getProtein_id()==null) || 
             (this.protein_id!=null &&
              this.protein_id.equals(other.getProtein_id()))) &&
            this.protein_length == other.getProtein_length() &&
            ((this.feature==null && other.getFeature()==null) || 
             (this.feature!=null &&
              java.util.Arrays.equals(this.feature, other.getFeature()))) &&
            ((this.protein_schematic==null && other.getProtein_schematic()==null) || 
             (this.protein_schematic!=null &&
              this.protein_schematic.equals(other.getProtein_schematic())));
        __equalsCalc = null;
        return _equals;
    }


    /**
     * Gets the feature value for this ProteinFeatures.
     * 
     * @return feature
     */
    public vis.smart.webservice.ProteinFeaturesFeature[] getFeature() {
        return feature;
    }


    public vis.smart.webservice.ProteinFeaturesFeature getFeature(int i) {
        return this.feature[i];
    }

    /**
     * Gets the protein_id value for this ProteinFeatures.
     * 
     * @return protein_id
     */
    public java.lang.String getProtein_id() {
        return protein_id;
    }
    /**
     * Gets the protein_length value for this ProteinFeatures.
     * 
     * @return protein_length
     */
    public int getProtein_length() {
        return protein_length;
    }

    /**
     * Gets the protein_schematic value for this ProteinFeatures.
     * 
     * @return protein_schematic   * HTTP link which can be used to generate the SMART
     *                                     protein schematic in PNG format.
     */
    public org.apache.axis.types.URI getProtein_schematic() {
        return protein_schematic;
    }
    @Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getProtein_id() != null) {
            _hashCode += getProtein_id().hashCode();
        }
        _hashCode += getProtein_length();
        if (getFeature() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFeature());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFeature(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProtein_schematic() != null) {
            _hashCode += getProtein_schematic().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    /**
     * Sets the feature value for this ProteinFeatures.
     * 
     * @param feature
     */
    public void setFeature(vis.smart.webservice.ProteinFeaturesFeature[] feature) {
        this.feature = feature;
    }

    public void setFeature(int i, vis.smart.webservice.ProteinFeaturesFeature _value) {
        this.feature[i] = _value;
    }

    /**
     * Sets the protein_id value for this ProteinFeatures.
     * 
     * @param protein_id
     */
    public void setProtein_id(java.lang.String protein_id) {
        this.protein_id = protein_id;
    }

    /**
     * Sets the protein_length value for this ProteinFeatures.
     * 
     * @param protein_length
     */
    public void setProtein_length(int protein_length) {
        this.protein_length = protein_length;
    }

    /**
     * Sets the protein_schematic value for this ProteinFeatures.
     * 
     * @param protein_schematic   * HTTP link which can be used to generate the SMART
     *                                     protein schematic in PNG format.
     */
    public void setProtein_schematic(org.apache.axis.types.URI protein_schematic) {
        this.protein_schematic = protein_schematic;
    }

}
