package com.truecaller

import java.util.Properties

import _root_.kafka.message.MessageAndMetadata
import _root_.kafka.serializer.{StringDecoder, DefaultDecoder}
import com.linkedin.camus.schemaregistry.{CachedSchemaRegistry, SchemaRegistry}
import com.truecaller.logging.kafka.events.app_event
import io.netty.handler.codec.bytes.ByteArrayDecoder
import org.apache.avro.Schema
import org.apache.avro.io.DecoderFactory
import org.apache.avro.specific.SpecificDatumReader
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.slf4j.LoggerFactory


object NewUsersDemo {

  def getSchema(props: Properties, topicName: String) {

    val registryClass = Class.forName(props.getProperty("kafka.message.coder.schema.registry.class"))
    val registry = registryClass.newInstance().asInstanceOf[SchemaRegistry[Schema]]
    registry.init(props)

    val cachedRegistry = new CachedSchemaRegistry[Schema](registry, props)

    val latestSchema = cachedRegistry.getLatestSchemaByTopic(topicName).getSchema()
  }

  def main(args: Array[String]) {

    if (args.length < 2) {
      System.err.println("Usage: NewUsersDemo <zookeeper> <topic> ")
      System.exit(1)
    }

    val logger = LoggerFactory.getLogger("haha")

    // parsing parameters
    val zookeeper = args(0)
    val topic = args(1)

    // initialization and configuration
    val sparkConf = new SparkConf().setAppName("NewUsersDemo")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val numInputMessages = ssc.sparkContext.accumulator(0L, "Kafka messages consumed")

    // connect to Kafka to fetch the log events
    val topicMap = Map(topic -> 1)
//    val lines = KafkaUtils.createStrcreateStream(ssc, zookeeper, "spark-streaming", topicMap).map(_._2)
//
    val brokers="kafka1.truecaller.net:9092,kafka2.truecaller.net:9092,kafka3.truecaller.net:9092"
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
//
//    val decodeEditEvent: (MessageAndMetadata[Array[Byte], Array[Byte]]) => app_event = (m: MessageAndMetadata[Array[Byte], Array[Byte]]) => {
//      val value = m.message()
//
//      val props = new Properties()
//      props.setProperty("kafka.message.coder.schema.registry.class", "com.linkedin.camus.schemaregistry.AvroRestSchemaRegistry")
//      props.setProperty("etl.schema.registry.url", "http://hdp3.truecaller.net:2876/schema-repo")
//
//      val schema = getSchema(props, "event_logs_v4")
//      val reader = new SpecificDatumReader[app_event](app_event.SCHEMA$)
//      val decoder = DecoderFactory.get().binaryDecoder(value, 5, value.size - 5, null)
//
//      val appEvent = reader.read(null, decoder)
//      appEvent
//    }

    val lines = KafkaUtils.createStream[String, Array[Byte], StringDecoder, ByteArrayDecoder](
      ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK).map(_._2)

    lines.foreachRDD { r =>

      r.foreachPartition( rddPart => {
        val avroLogDecoder = new AvroLogDecoder()
        avroLogDecoder.init(topic)
        val list = rddPart.toList
        if (list.size == 0) {
          logger.error("pusto")
        } else {
          list.map( event => {
            logger.error("processing event...")
            val appEvent = avroLogDecoder.decode(event)
            logger.error("appEvent: " + appEvent)
          })
          logger.error("cos jest: " + list.size)
        }

      })
    }

    // start streaming process
    ssc.start()
    ssc.awaitTermination()
  }
}
