/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.truecaller.logging.kafka.events;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class app_record extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"app_record\",\"namespace\":\"com.truecaller.logging.kafka.events\",\"fields\":[{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"os\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence type;
  @Deprecated public java.lang.CharSequence os;
  @Deprecated public java.lang.CharSequence version;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public app_record() {}

  /**
   * All-args constructor.
   */
  public app_record(java.lang.CharSequence type, java.lang.CharSequence os, java.lang.CharSequence version) {
    this.type = type;
    this.os = os;
    this.version = version;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return type;
    case 1: return os;
    case 2: return version;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: type = (java.lang.CharSequence)value$; break;
    case 1: os = (java.lang.CharSequence)value$; break;
    case 2: version = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }

  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'os' field.
   */
  public java.lang.CharSequence getOs() {
    return os;
  }

  /**
   * Sets the value of the 'os' field.
   * @param value the value to set.
   */
  public void setOs(java.lang.CharSequence value) {
    this.os = value;
  }

  /**
   * Gets the value of the 'version' field.
   */
  public java.lang.CharSequence getVersion() {
    return version;
  }

  /**
   * Sets the value of the 'version' field.
   * @param value the value to set.
   */
  public void setVersion(java.lang.CharSequence value) {
    this.version = value;
  }

  /** Creates a new app_record RecordBuilder */
  public static com.truecaller.logging.kafka.events.app_record.Builder newBuilder() {
    return new com.truecaller.logging.kafka.events.app_record.Builder();
  }
  
  /** Creates a new app_record RecordBuilder by copying an existing Builder */
  public static com.truecaller.logging.kafka.events.app_record.Builder newBuilder(com.truecaller.logging.kafka.events.app_record.Builder other) {
    return new com.truecaller.logging.kafka.events.app_record.Builder(other);
  }
  
  /** Creates a new app_record RecordBuilder by copying an existing app_record instance */
  public static com.truecaller.logging.kafka.events.app_record.Builder newBuilder(com.truecaller.logging.kafka.events.app_record other) {
    return new com.truecaller.logging.kafka.events.app_record.Builder(other);
  }
  
  /**
   * RecordBuilder for app_record instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<app_record>
    implements org.apache.avro.data.RecordBuilder<app_record> {

    private java.lang.CharSequence type;
    private java.lang.CharSequence os;
    private java.lang.CharSequence version;

    /** Creates a new Builder */
    private Builder() {
      super(com.truecaller.logging.kafka.events.app_record.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.truecaller.logging.kafka.events.app_record.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.os)) {
        this.os = data().deepCopy(fields()[1].schema(), other.os);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.version)) {
        this.version = data().deepCopy(fields()[2].schema(), other.version);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing app_record instance */
    private Builder(com.truecaller.logging.kafka.events.app_record other) {
            super(com.truecaller.logging.kafka.events.app_record.SCHEMA$);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.os)) {
        this.os = data().deepCopy(fields()[1].schema(), other.os);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.version)) {
        this.version = data().deepCopy(fields()[2].schema(), other.version);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'type' field */
    public java.lang.CharSequence getType() {
      return type;
    }
    
    /** Sets the value of the 'type' field */
    public com.truecaller.logging.kafka.events.app_record.Builder setType(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.type = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'type' field has been set */
    public boolean hasType() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'type' field */
    public com.truecaller.logging.kafka.events.app_record.Builder clearType() {
      type = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'os' field */
    public java.lang.CharSequence getOs() {
      return os;
    }
    
    /** Sets the value of the 'os' field */
    public com.truecaller.logging.kafka.events.app_record.Builder setOs(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.os = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'os' field has been set */
    public boolean hasOs() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'os' field */
    public com.truecaller.logging.kafka.events.app_record.Builder clearOs() {
      os = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'version' field */
    public java.lang.CharSequence getVersion() {
      return version;
    }
    
    /** Sets the value of the 'version' field */
    public com.truecaller.logging.kafka.events.app_record.Builder setVersion(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.version = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'version' field has been set */
    public boolean hasVersion() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'version' field */
    public com.truecaller.logging.kafka.events.app_record.Builder clearVersion() {
      version = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public app_record build() {
      try {
        app_record record = new app_record();
        record.type = fieldSetFlags()[0] ? this.type : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.os = fieldSetFlags()[1] ? this.os : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.version = fieldSetFlags()[2] ? this.version : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
