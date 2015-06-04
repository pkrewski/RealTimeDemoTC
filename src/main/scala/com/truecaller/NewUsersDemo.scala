package com.truecaller

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.slf4j.LoggerFactory


object NewUsersDemo {


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
    val lines = KafkaUtils.createStream(ssc, zookeeper, "spark-streaming", topicMap).map(_._2)

    lines.foreachRDD { r =>

      r.foreachPartition( rddPart => {
        val avroLogDecoder = new AvroLogDecoder()
        avroLogDecoder.init(topic)
        if (rddPart.toList.size == 0) {
          logger.error("pusto")
        } else {
          rddPart.map( event => {
            logger.warn("processing event...")
            val appEvent = avroLogDecoder.decode(event.asInstanceOf[Array[Byte]])
            logger.warn("appEvent: " + appEvent)
          })
          logger.error("cos jest: " + rddPart.toList.size)
        }

      })
    }

    // start streaming process
    ssc.start()
    ssc.awaitTermination()
  }
}
