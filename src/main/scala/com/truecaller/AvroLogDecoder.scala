package com.truecaller

import java.util.Properties

import com.linkedin.camus.schemaregistry.{CachedSchemaRegistry, SchemaRegistry}
import com.truecaller.logging.kafka.events.app_event
import org.apache.avro.Schema
import org.apache.avro.io.DecoderFactory
import org.apache.avro.specific.SpecificDatumReader
import org.slf4j.LoggerFactory

@serializable
class AvroLogDecoder {

  val OFFSET = 5
  var latestSchema = null
  val reader = new SpecificDatumReader[app_event](app_event.SCHEMA$)
  val logger = LoggerFactory.getLogger(classOf[AvroLogDecoder])

  def init(topicName: String) {

    val props = new Properties()
    props.setProperty("kafka.message.coder.schema.registry.class", "com.linkedin.camus.schemaregistry.AvroRestSchemaRegistry")
    props.setProperty("etl.schema.registry.url", "http://hdp3.truecaller.net:2876/schema-repo")

    getSchema(props, topicName)

//    val path = Paths.get("src/main/resources/app_events_from_kafka.avro")
//    val avroBytes = Files.readAllBytes(path)
  }

  def getSchema(props: Properties, topicName: String) {

    val registryClass = Class.forName(props.getProperty("kafka.message.coder.schema.registry.class"))
    val registry = registryClass.newInstance().asInstanceOf[SchemaRegistry[Schema]]
    registry.init(props)

    val cachedRegistry = new CachedSchemaRegistry[Schema](registry, props)

    val latestSchema = cachedRegistry.getLatestSchemaByTopic(topicName).getSchema()
  }

  def decode(avroBytes: Array[Byte]): app_event = {
    val decoder = DecoderFactory.get().binaryDecoder(avroBytes, OFFSET, avroBytes.length - OFFSET, null)

    val appEvent = reader.read(null, decoder)
   // logger.info("deserialized: " + appEvent)

    return appEvent
  }



}
