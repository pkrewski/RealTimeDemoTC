import AssemblyKeys._ 

name := "RealTimeDemo"
 
version := "0.1"
 
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.3.0",
  "org.apache.spark" % "spark-streaming_2.10" % "1.3.0",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "org.apache.avro" % "avro" % "1.7.7"
//  "com.linkedin.camus" % "camus-api" % "0.1.0-SNAPSHOT",
//  "com.linkedin.camus" % "camus-example" % "0.1.0-SNAPSHOT",
//  "com.linkedin.camus" % "camus-schema-registry-avro" % "0.1.0-SNAPSHOT"
)

assemblySettings

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
