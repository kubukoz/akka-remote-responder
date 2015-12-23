import akka.actor._

object Main {
  def main(args: Array[String]) {
    ActorSystem("akka-remote-responder").actorOf(Props[Responder], "responder")
  }
}

class Responder extends Actor{
  override def receive: Receive = {
    case s: String => sender ! s"$s :)"
  }
}
