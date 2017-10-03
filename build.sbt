name := "SparkProcera"
version := "1.1"
scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.11" % "1.6.3"

libraryDependencies ++= Seq(
  "org.apache.spark"  % "spark-core_2.11"              % "2.1.0" % "provided",
  "org.apache.spark"  % "spark-mllib_2.11"             % "2.1.0" % "provided"
  )
  
assemblyMergeStrategy in assembly := {  
	case PathList("META-INF", xs @ _*) => MergeStrategy.discard
	case x => MergeStrategy.first
}