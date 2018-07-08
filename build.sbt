name := "mails"

version := "1.0"

scalaVersion := "2.12.6"


libraryDependencies += "javax.mail" % "mail" % "1.4"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.13"
libraryDependencies += "commons-net" % "commons-net" % "3.6"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList(ps @ _*)  if ps.last.endsWith(".class") => MergeStrategy.last
  case PathList(ps @ _*)  if ps.last.endsWith(".properties") ||  ps.last.endsWith(".dtd") ||ps.last.endsWith(".xml") ||
    ps.last.endsWith(".xsd") => MergeStrategy.first
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) |
           ("license" :: Nil) | ("license.txt" :: Nil) | ("notice" :: Nil)  | ("notice.txt" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.deduplicate
    }
  case x => old(x)
}  }