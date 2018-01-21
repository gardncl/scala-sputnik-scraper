import akka.actor.{ActorSystem, Props}
import io.bfil.scalescrape.actor.ScrapingActor

object Main extends App {

  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[ExampleScraper], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}


class ExampleScraper extends ScrapingActor {

  private val baseUrl = "https://www.sputnikmusic.com/"

  override def receive: Receive = {
    case _       => grabMainTitle(baseUrl)
  }

  private def grabMainTitle(url: String) =
    scrape {
          get(url) { response =>
            complete(doIt(response))
          }
    }

  private def doIt(response: Any): Unit = {
    println(response)
  }
}




