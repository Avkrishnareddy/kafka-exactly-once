import AssemblyKeys._

name := "kafka-exactly-once"

scalaVersion := "2.10.4"

val sparkVersion = "1.6.0"

externalResolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
)

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-core" % sparkVersion % "provided").
    exclude("org.apache.spark", "spark-network-common_2.10").
    exclude("org.apache.spark", "spark-network-shuffle_2.10"),
  // avoid an ivy bug
  "org.apache.spark" %% "spark-network-common" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-network-shuffle" % sparkVersion % "provided",
  ("org.apache.spark" %% "spark-streaming" % sparkVersion % "provided").
    exclude("org.apache.spark", "spark-core_2.10"),
  ("org.apache.spark" %% "spark-streaming-kafka" % sparkVersion).
    exclude("org.apache.spark", "spark-core_2.10"),
  ("org.scalikejdbc" %% "scalikejdbc" % "2.2.1").
    exclude("org.slf4j", "slf4j-api"),
  ("org.postgresql" % "postgresql" % "9.3-1101-jdbc4").
    exclude("org.slf4j", "slf4j-api"),
  "com.typesafe" % "config" % "1.2.1"
)

assemblySettings

mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (mergeStrategy in assembly).value(x)
}
