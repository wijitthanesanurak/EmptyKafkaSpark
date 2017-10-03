// scalastyle:off println
package org.apache.spark.examples.streaming

import java.util.HashMap
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream.toPairDStreamFunctions
import scala.Iterator

object ProceraCompute 
{
  /*
	def max(l1: Long,   l2: Long): Long = if (l1 > l2) l1 else l2
	def min(l1: Long,   l2: Long): Long = if (l1 < l2) l1 else l2
	def concat(l1:String, l2: String): String = l1 + "#" + l2
	*/
	def main(args: Array[String]) 
	{  
		if (args.length < 3) {
			System.err.println("\nUsage : DashboardCompute <topic-source> <topic-target> <interval>")
			System.err.println("Sample: DashboardCompute test-3 test55 5\n")
			System.exit(1)
		}

		val Array(topic_s, topic_d, interval) = args

		System.out.println("Topic Source :" + topic_s)
		System.out.println("Topic Dest   :" + topic_d)
		System.out.println("Duration Time:" + interval + " sec.")

		val conf = new SparkConf().setAppName("SparkProcera")

		val ssc = new StreamingContext(conf, Seconds(interval.toInt))

		
		// List of topics you want to listen for from Kafka
		val topics = List(topic_s).toSet

		val kafkaParams = Map[String, String](
			//"metadata.broker.list" -> "104.196.186.168:9092",
		  //"metadata.broker.list" -> "cjkafdc01:9092,cjkafdc02:9092,cjkafdc03:9092",
			//"group.id" -> "spark_test"
		  
		  "metadata.broker.list" -> "hw-ax-01.dc1.true.th:9092,hw-ax-02.dc1.true.th:9092",
      //"group.id" -> "ProceraKafkaEtl",
      "auto.offset.reset" -> "largest",
      "security.protocol" -> "PLAINTEXTSASL"
		)
		
		val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
						ssc, kafkaParams, topics)
		
		messages.foreachRDD(rdd=>rdd.foreach(c=>println(c)))
		// split, get col(0), create touple (col(0), line)
		// สร้างข้อมูลที่มี 2 col โดยเป็น col0 กับ line ทั้งหมด
		//val pair = messages.map(_._2).map(rdd=>(rdd.split(';')(0), rdd))

		// รวม line ที่มี key (col0) ซ้ำๆกัน โดยให้มาต่อเป็น list item เดียว
		//val groups = pair.groupByKey()
		
		// data file
		/*
		try {		 
			// send message to kafka
			val brokers = "cjkafdc01:9092,cjkafdc02:9092,cjkafdc03:9092"
			//val brokers = "localhost:9092"
		 
			groups.foreachRDD( rdd => {
				rdd.foreachPartition( partition => {
					val kafkaTopic = topic_d
					val props = new HashMap[String, Object]()
					props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
					props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        "org.apache.kafka.common.serialization.StringSerializer")
					props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        "org.apache.kafka.common.serialization.StringSerializer")

					val producer = new KafkaProducer[String, String](props)           

					partition.foreach { record => { 
						record._2.foreach( r=> {
							val message = new ProducerRecord[String, String](kafkaTopic, null, r)
							producer.send(message)
						})
					}}
				})
			})
		}
		catch  { 
  		case nfe: NumberFormatException => None 
  		case ae:  java.lang.ArithmeticException => 0
  		case rt:  RuntimeException => 0
  		case oob: java.lang.ArrayIndexOutOfBoundsException => println("Error...")
  		case nfe: java.lang.NumberFormatException => 0
  		case _ => Iterator("An unexpected error has occurred. We are so sorry!")
		}  // end catch
		*/
		// Kick it off
	
		//ssc.checkpoint("/tmp/checkpoint/")
		ssc.start()
		ssc.awaitTermination()
	}
}
