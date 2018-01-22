import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object Main extends App {
  val scraper = new ExampleScraper
  val albumId = 200
  val doc = scraper.parse("file:///Users/gardncl/dev/dev-projects/scala/sputnik-scraper/src/main/resources/example.html")

  val items = doc >> elementList("table").map(_ >> allText("tbody"))
//  Rating.parse(items(40))
  val filtered = items.filter(Rating.validLine).map(Rating.parse)
  filtered.foreach(
    println(_)
  )
}

class ExampleScraper {
  val browser = JsoupBrowser()

  def parse(url: String): browser.DocumentType = {
    browser.get(url)
  }
}

case class Rating(rating: Double, username: String, date: Option[LocalDate])

object Rating {
  private val formatter = DateTimeFormat.forPattern("MMMddyy")

  def parse(line: String): Rating = {
    val split: Array[String] = line.split(" +")
    val rating = split(0).toDouble
    val name = split(2)
    var date: Option[LocalDate] = Option.empty
    if (split.length >= 7) {
      val rawDate = split(4) + formatDay(split(5)) + split(6)
      date = Some(formatter.parseLocalDate(rawDate))
    }
    Rating(rating, name, date)
  }

  def validLine(line: String): Boolean = {
    getRating(line) match {
      case Some(_) => true
      case None => false
    }
  }

  private def formatDay(day: String): String = {
    val dayOfMonth = day.dropRight(2)
    dayOfMonth.length match {
      case 1 => "0" + dayOfMonth
      case _ => dayOfMonth
    }
  }

  private def getRating(line: String): Option[Double] = {
    val firstElement = line.split(" +")(0)
    try {
      Some(firstElement.toDouble)
    } catch {
      case _: Throwable => None
    }
  }
}

