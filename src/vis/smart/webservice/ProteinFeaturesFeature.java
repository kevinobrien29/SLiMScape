/**
 * ProteinFeaturesFeature.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;

public class ProteinFeaturesFeature  implements java.io.Serializable {
    private java.lang.String name;

    private vis.smart.webservice.ProteinFeaturesFeatureType type;

    private float e_value;

    private int start;

    private int end;

    /* SMART can predict possible catalytic
     *                                                 inactivity, based
     * on the presence of essential amino
     *                                                 acids at specific
     * positions in the alignment. If any
     *                                                 of the required amino
     * acids are missing, the domain
     *                                                 is marked as 'inactive'.
     * Check the annotation page
     *                                                 for details. Only
     * returned for features with
     *                                                 type 'SMART'. */
    private vis.smart.webservice.ProteinFeaturesFeatureCatalytic_activity catalytic_activity;

    /* HTTP link to the SMART domain
     *                                                 annotation page. Only
     * returned for features with
     *                                                 type 'SMART'. */
    private org.apache.axis.types.URI SMARTannotation;

    private java.lang.Object __equalsCalc = null;

    private boolean __hashCodeCalc = false;


    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProteinFeaturesFeature.class, true);


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">>proteinFeatures>feature"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">>>proteinFeatures>feature>type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("e_value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "e_value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("start");
        elemField.setXmlName(new javax.xml.namespace.QName("", "start"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalytic_activity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "catalytic_activity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">>>proteinFeatures>feature>catalytic_activity"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SMARTannotation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SMARTannotation"));
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


    public ProteinFeaturesFeature() {
    }


    public ProteinFeaturesFeature(
           java.lang.String name,
           vis.smart.webservice.ProteinFeaturesFeatureType type,
           float e_value,
           int start,
           int end,
           vis.smart.webservice.ProteinFeaturesFeatureCatalytic_activity catalytic_activity,
           org.apache.axis.types.URI SMARTannotation) {
           this.name = name;
           this.type = type;
           this.e_value = e_value;
           this.start = start;
           this.end = end;
           this.catalytic_activity = catalytic_activity;
           this.SMARTannotation = SMARTannotation;
    }


    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProteinFeaturesFeature)) return false;
        ProteinFeaturesFeature other = (ProteinFeaturesFeature) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.e_value == other.getE_value() &&
            this.start == other.getStart() &&
            this.end == other.getEnd() &&
            ((this.catalytic_activity==null && other.getCatalytic_activity()==null) || 
             (this.catalytic_activity!=null &&
              this.catalytic_activity.equals(other.getCatalytic_activity()))) &&
            ((this.SMARTannotation==null && other.getSMARTannotation()==null) || 
             (this.SMARTannotation!=null &&
              this.SMARTannotation.equals(other.getSMARTannotation())));
        __equalsCalc = null;
        return _equals;
    }


    /**
     * Gets the catalytic_activity value for this ProteinFeaturesFeature.
     * 
     * @return catalytic_activity   * SMART can predict possible catalytic
     *                                                 inactivity, based
     * on the presence of essential amino
     *                                                 acids at specific
     * positions in the alignment. If any
     *                                                 of the required amino
     * acids are missing, the domain
     *                                                 is marked as 'inactive'.
     * Check the annotation page
     *                                                 for details. Only
     * returned for features with
     *                                                 type 'SMART'.
     */
    public vis.smart.webservice.ProteinFeaturesFeatureCatalytic_activity getCatalytic_activity() {
        return catalytic_activity;
    }


    /**
     * Gets the e_value value for this ProteinFeaturesFeature.
     * 
     * @return e_value
     */
    public float getE_value() {
        return e_value;
    }


    /**
     * Gets the end value for this ProteinFeaturesFeature.
     * 
     * @return end
     */
    public int getEnd() {
        return end;
    }


    /**
     * Gets the name value for this ProteinFeaturesFeature.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Gets the SMARTannotation value for this ProteinFeaturesFeature.
     * 
     * @return SMARTannotation   * HTTP link to the SMART domain
     *                                                 annotation page. Only
     * returned for features with
     *                                                 type 'SMART'.
     */
    public org.apache.axis.types.URI getSMARTannotation() {
        return SMARTannotation;
    }


    /**
     * Gets the start value for this ProteinFeaturesFeature.
     * 
     * @return start
     */
    public int getStart() {
        return start;
    }

    /**
     * Gets the type value for this ProteinFeaturesFeature.
     * 
     * @return type
     */
    public vis.smart.webservice.ProteinFeaturesFeatureType getType() {
        return type;
    }
    @Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += new Float(getE_value()).hashCode();
        _hashCode += getStart();
        _hashCode += getEnd();
        if (getCatalytic_activity() != null) {
            _hashCode += getCatalytic_activity().hashCode();
        }
        if (getSMARTannotation() != null) {
            _hashCode += getSMARTannotation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    /**
     * Sets the catalytic_activity value for this ProteinFeaturesFeature.
     * 
     * @param catalytic_activity   * SMART can predict possible catalytic
     *                                                 inactivity, based
     * on the presence of essential amino
     *                                                 acids at specific
     * positions in the alignment. If any
     *                                                 of the required amino
     * acids are missing, the domain
     *                                                 is marked as 'inactive'.
     * Check the annotation page
     *                                                 for details. Only
     * returned for features with
     *                                                 type 'SMART'.
     */
    public void setCatalytic_activity(vis.smart.webservice.ProteinFeaturesFeatureCatalytic_activity catalytic_activity) {
        this.catalytic_activity = catalytic_activity;
    }
    /**
     * Sets the e_value value for this ProteinFeaturesFeature.
     * 
     * @param e_value
     */
    public void setE_value(float e_value) {
        this.e_value = e_value;
    }

    /**
     * Sets the end value for this ProteinFeaturesFeature.
     * 
     * @param end
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Sets the name value for this ProteinFeaturesFeature.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * Sets the SMARTannotation value for this ProteinFeaturesFeature.
     * 
     * @param SMARTannotation   * HTTP link to the SMART domain
     *                                                 annotation page. Only
     * returned for features with
     *                                                 type 'SMART'.
     */
    public void setSMARTannotation(org.apache.axis.types.URI SMARTannotation) {
        this.SMARTannotation = SMARTannotation;
    }

    /**
     * Sets the start value for this ProteinFeaturesFeature.
     * 
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Sets the type value for this ProteinFeaturesFeature.
     * 
     * @param type
     */
    public void setType(vis.smart.webservice.ProteinFeaturesFeatureType type) {
        this.type = type;
    }

}
