package com.truecaller

import java.util.Properties

import _root_.kafka.serializer._
import com.linkedin.camus.schemaregistry.{CachedSchemaRegistry, SchemaRegistry}
import org.apache.avro.Schema
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

    val kafkaParams = Map[String, String]("zookeeper.connect" -> zookeeper,
                                          "group.id" -> "sparkDemo")

    val lines = KafkaUtils.createStream[Array[Byte], Array[Byte], DefaultDecoder, DefaultDecoder] (
      ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK).map(_._2)


    lines.foreachRDD { r => {
      if (r.count() == 0)
        logger.error("pustooo")
      else
      logger.error("cos jest :)")
      r.foreachPartition(rddPart => {
        val avroLogDecoder = new AvroLogDecoder()
        avroLogDecoder.init(topic)
        val list = rddPart.toList
        if (list.size == 0) {
          logger.error("pusto")
        } else {
          list.map(event => {
            logger.error("processing event...")
            val appEvent = avroLogDecoder.decode(event)
            logger.error("appEvent: " + appEvent)
          })
          logger.error("cos jest: " + list.size)
        }

      })
    }
    }

    // start streaming process
    ssc.start()
    ssc.awaitTermination()
  }
}
