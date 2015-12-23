import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Main {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("akka-remote-responder")
    implicit val dispatcher = system.dispatcher

    system.actorOf(Props[Responder], "responder")
    val selection = system.actorSelection("akka.tcp://akka-remote-responder@localhost:2552/user/responder")

    implicit val timeout = Timeout(5.seconds)

    (selection ? Identify(0)).mapTo[ActorIdentity].map {
      case ActorIdentity(_, Some(ref)) =>
        println("Found remote actor!")
        (ref ? "Hello").mapTo[String] foreach println

      case ActorIdentity(_, None) => println("Didn't find remote actor")
    }
  }
}

class Responder extends Actor {
  override def receive: Receive = {
    case s: String => sender ! s"$s :)"
  }
}
