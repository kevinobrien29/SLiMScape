/**
 * Input_protein.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vis.smart.webservice;


/**
 * User can submit either a protein sequence or Uniprot/Ensembl
 *                         identifier. If both a sequence and an ID are
 * supplied, ID will be used. Only
 *                         sequences that are present in the SMART database
 * will be accepted
 *                         (Uniprot100 and Ensembl proteomes).
 */
public class Input_protein  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String protein_sequence;

    private java.lang.String protein_ID;

    private java.lang.Object __equalsCalc = null;

    private boolean __hashCodeCalc = false;


    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Input_protein.class, true);


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://smart.embl.de/webservice/SMART.wsdl", ">input_protein"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protein_sequence");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protein_sequence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protein_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protein_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
    public Input_protein() {
    }

    public Input_protein(
           java.lang.String protein_sequence,
           java.lang.String protein_ID) {
           this.protein_sequence = protein_sequence;
           this.protein_ID = protein_ID;
    }
    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Input_protein)) return false;
        Input_protein other = (Input_protein) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.protein_sequence==null && other.getProtein_sequence()==null) || 
             (this.protein_sequence!=null &&
              this.protein_sequence.equals(other.getProtein_sequence()))) &&
            ((this.protein_ID==null && other.getProtein_ID()==null) || 
             (this.protein_ID!=null &&
              this.protein_ID.equals(other.getProtein_ID())));
        __equalsCalc = null;
        return _equals;
    }

    /**
     * Gets the protein_ID value for this Input_protein.
     * 
     * @return protein_ID
     */
    public java.lang.String getProtein_ID() {
        return protein_ID;
    }

    /**
     * Gets the protein_sequence value for this Input_protein.
     * 
     * @return protein_sequence
     */
    public java.lang.String getProtein_sequence() {
        return protein_sequence;
    }

    @Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getProtein_sequence() != null) {
            _hashCode += getProtein_sequence().hashCode();
        }
        if (getProtein_ID() != null) {
            _hashCode += getProtein_ID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    /**
     * Sets the protein_ID value for this Input_protein.
     * 
     * @param protein_ID
     */
    public void setProtein_ID(java.lang.String protein_ID) {
        this.protein_ID = protein_ID;
    }

    /**
     * Sets the protein_sequence value for this Input_protein.
     * 
     * @param protein_sequence
     */
    public void setProtein_sequence(java.lang.String protein_sequence) {
        this.protein_sequence = protein_sequence;
    }

}
