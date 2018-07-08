package receiver

import engine.Mails
/**
  * Created by priyanka on 7/7/18.
  */

import akka.actor.{Actor, ActorLogging}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class SchedularActor(appConfigMap:Map[String,String]) extends Actor {
  override def preStart(): Unit = {
    println("Actor is created")
    self ! 1
  }
   def receive: Receive = {
     case msg:Int =>
     Mails.mailProcess(appConfigMap)
       val duration = appConfigMap("monitorTimeInMinutes").toInt
       context.system.scheduler.scheduleOnce(1 minutes, self, 1)
     case _ =>
       println("Wrong Message")
   }
}
