/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package schema.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class UploadFileMessage extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7560316204949033037L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"UploadFileMessage\",\"namespace\":\"schema.avro\",\"fields\":[{\"name\":\"url\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"timestamp\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
  }

  private static final BinaryMessageEncoder<UploadFileMessage> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<UploadFileMessage> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<UploadFileMessage> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<UploadFileMessage> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<UploadFileMessage> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this UploadFileMessage to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a UploadFileMessage from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a UploadFileMessage instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static UploadFileMessage fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String url;
  private long id;
  private java.time.Instant timestamp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public UploadFileMessage() {}

  /**
   * All-args constructor.
   * @param url The new value for url
   * @param id The new value for id
   * @param timestamp The new value for timestamp
   */
  public UploadFileMessage(java.lang.String url, java.lang.Long id, java.time.Instant timestamp) {
    this.url = url;
    this.id = id;
    this.timestamp = timestamp.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return url;
    case 1: return id;
    case 2: return timestamp;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: url = value$ != null ? value$.toString() : null; break;
    case 1: id = (java.lang.Long)value$; break;
    case 2: timestamp = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'url' field.
   * @return The value of the 'url' field.
   */
  public java.lang.String getUrl() {
    return url;
  }


  /**
   * Sets the value of the 'url' field.
   * @param value the value to set.
   */
  public void setUrl(java.lang.String value) {
    this.url = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public long getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(long value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public java.time.Instant getTimestamp() {
    return timestamp;
  }


  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.time.Instant value) {
    this.timestamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new UploadFileMessage RecordBuilder.
   * @return A new UploadFileMessage RecordBuilder
   */
  public static schema.avro.UploadFileMessage.Builder newBuilder() {
    return new schema.avro.UploadFileMessage.Builder();
  }

  /**
   * Creates a new UploadFileMessage RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new UploadFileMessage RecordBuilder
   */
  public static schema.avro.UploadFileMessage.Builder newBuilder(schema.avro.UploadFileMessage.Builder other) {
    if (other == null) {
      return new schema.avro.UploadFileMessage.Builder();
    } else {
      return new schema.avro.UploadFileMessage.Builder(other);
    }
  }

  /**
   * Creates a new UploadFileMessage RecordBuilder by copying an existing UploadFileMessage instance.
   * @param other The existing instance to copy.
   * @return A new UploadFileMessage RecordBuilder
   */
  public static schema.avro.UploadFileMessage.Builder newBuilder(schema.avro.UploadFileMessage other) {
    if (other == null) {
      return new schema.avro.UploadFileMessage.Builder();
    } else {
      return new schema.avro.UploadFileMessage.Builder(other);
    }
  }

  /**
   * RecordBuilder for UploadFileMessage instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<UploadFileMessage>
    implements org.apache.avro.data.RecordBuilder<UploadFileMessage> {

    private java.lang.String url;
    private long id;
    private java.time.Instant timestamp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(schema.avro.UploadFileMessage.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.url)) {
        this.url = data().deepCopy(fields()[0].schema(), other.url);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[2].schema(), other.timestamp);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing UploadFileMessage instance
     * @param other The existing instance to copy.
     */
    private Builder(schema.avro.UploadFileMessage other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.url)) {
        this.url = data().deepCopy(fields()[0].schema(), other.url);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[2].schema(), other.timestamp);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'url' field.
      * @return The value.
      */
    public java.lang.String getUrl() {
      return url;
    }


    /**
      * Sets the value of the 'url' field.
      * @param value The value of 'url'.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder setUrl(java.lang.String value) {
      validate(fields()[0], value);
      this.url = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'url' field has been set.
      * @return True if the 'url' field has been set, false otherwise.
      */
    public boolean hasUrl() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'url' field.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder clearUrl() {
      url = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public long getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder setId(long value) {
      validate(fields()[1], value);
      this.id = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder clearId() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public java.time.Instant getTimestamp() {
      return timestamp;
    }


    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder setTimestamp(java.time.Instant value) {
      validate(fields()[2], value);
      this.timestamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public schema.avro.UploadFileMessage.Builder clearTimestamp() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UploadFileMessage build() {
      try {
        UploadFileMessage record = new UploadFileMessage();
        record.url = fieldSetFlags()[0] ? this.url : (java.lang.String) defaultValue(fields()[0]);
        record.id = fieldSetFlags()[1] ? this.id : (java.lang.Long) defaultValue(fields()[1]);
        record.timestamp = fieldSetFlags()[2] ? this.timestamp : (java.time.Instant) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<UploadFileMessage>
    WRITER$ = (org.apache.avro.io.DatumWriter<UploadFileMessage>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<UploadFileMessage>
    READER$ = (org.apache.avro.io.DatumReader<UploadFileMessage>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










