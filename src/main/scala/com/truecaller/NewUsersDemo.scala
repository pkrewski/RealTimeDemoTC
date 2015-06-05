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

    if (args.length < 3) {
      System.err.println("Usage: NewUsersDemo <zookeeper> <topic> <batch_interval> ")
      System.exit(1)
    }

    val logger = LoggerFactory.getLogger("haha")

    // parsing parameters
    val zookeeper = args(0)
    val topic = args(1)
    val batchInterval = args(2).toInt

    // initialization and configuration
    val sparkConf = new SparkConf().setAppName("NewUsersDemo")
    val ssc = new StreamingContext(sparkConf, Seconds(batchInterval))
    val numInputMessages = ssc.sparkContext.accumulator(0L, "Kafka messages consumed")

    // connect to Kafka to fetch the log events
    val topicMap = Map(topic -> 1)

    val kafkaParams = Map[String, String]("zookeeper.connect" -> zookeeper,
      "group.id" -> "sparkDemo")

    val lines = KafkaUtils.createStream[Array[Byte], Array[Byte], DefaultDecoder, DefaultDecoder](
      ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK).map(_._2)

    val regIdSearch = lines.map{ avroEvent => {
      val avroLogDecoder = new AvroLogDecoder()
      avroLogDecoder.init(topic)
      avroLogDecoder.decode(avroEvent)
    }}
      .filter(_.getSearch.getSearchString != null)
      .map( event => (event.getUser.getRegisterId, event.getSearch.getSearchString))

    val cnt = regIdSearch.groupByKey().map{ case (regId, itr) => (regId, itr.toList.size)}

    cnt.print()

//    val appEvents = lines.foreachRDD { r =>
//    {
//      r.foreachPartition(rddPart => {
//        val avroLogDecoder = new AvroLogDecoder()
//        avroLogDecoder.init(topic)
//        val list = rddPart.toList
//
//        list.map(event => {
//          avroLogDecoder.decode(event)
//        })
//      })
//    }}

    // start streaming process
    ssc.start()
    ssc.awaitTermination()
  }
}
