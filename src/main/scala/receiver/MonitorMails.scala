package receiver
import akka.actor.{ActorSystem, Props}

import com.typesafe.config.ConfigFactory
import java.io.File


object MonitorMails extends App {

  //get configration paramenters from mailConfig.conf file
  val parsedConfig = ConfigFactory.parseFile(new File("mailConfig.conf"))
  var confFile = ConfigFactory.load(parsedConfig)

  val monitorTimeInMinutes = confFile.getString("sourceMailConf.monitorTimeInMinutes")
  var appConfigMap:Map[String,String] = Map("monitorTimeInMinutes"->monitorTimeInMinutes.toString)
  val readFolder = confFile.getString("sourceMailConf.readFolder")
  appConfigMap ++= Map("readFolder"->readFolder)
  val emailPassword = confFile.getString("sourceMailConf.emailPassword")
  appConfigMap ++= Map("emailPassword"->emailPassword)
  val emailAddress = confFile.getString("sourceMailConf.emailAddress")
  appConfigMap ++= Map("emailAddress"->emailAddress)

  val destlocation =  confFile.getString("destinationFTPConf.destlocation")
  appConfigMap ++= Map("ftpDestlocation"->destlocation)
  val hostIP =  confFile.getString("destinationFTPConf.hostIP")
  appConfigMap ++= Map("ftpHostIP"->hostIP)
  val userName =  confFile.getString("destinationFTPConf.userName")
  appConfigMap ++= Map("ftpUserName"->userName)
  val password =  confFile.getString("destinationFTPConf.password")
  appConfigMap ++= Map("ftpPassword"->password)
  println(appConfigMap)

  //Create Actor
  println("Creating Actor")
  var actorSystem = ActorSystem("ActorSystem")
  var actor = actorSystem.actorOf(Props(new SchedularActor(appConfigMap)),"SchedularActor")
}