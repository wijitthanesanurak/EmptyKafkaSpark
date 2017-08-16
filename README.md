#Build

sbt clean compile package

#Testing

##Spark

bin/spark-submit --class "org.apache.sparexamples.streaming.ProceraCompute" --master <spark-server:port> <jar> <src-topic> <dst-topic> <duration>

bin/spark-submit --class "org.apache.sparexamples.streaming.ProceraCompute" --master spark://cjkafdc01:7077 spark-procera_2.11-1.1.jar src-topic dst-topic 10

##Kafka

##Producer
bin/kafka-console-producer.sh --brok-list cjkafdc01:9092 --topic test-3

##Consumer
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test55
