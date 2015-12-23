import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.RoundRobinPool

object Main {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("akka-remote-responder")
    val responder = system.actorOf(Props[Responder].withRouter(RoundRobinPool(nrOfInstances = 10)), "responder")
  }
}

class Responder extends Actor{
  override def receive: Receive = {
    case s: String => s"$s :)"
  }
}
