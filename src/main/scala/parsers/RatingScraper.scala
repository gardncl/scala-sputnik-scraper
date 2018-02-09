package parsers

import models.{AlbumId, Rating}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{allText, elementList}
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object RatingScraper {
  private val browser = JsoupBrowser()
  private val formatter = DateTimeFormat.forPattern("MMMddyy")

  def parseWholePage(url: String): List[Rating] = {
    val doc = browser.get(url)
    val items = doc >> elementList("table").map(_ >> allText("tbody"))
    items.filter(validLine).map(parseLineToRating)
  }

  private def parseLineToRating(line: String): Rating = {
    val split: Array[String] = line.split(" +")
    val rating = split(0).toDouble
    val name = rating match {
      case 0 => split(1)
      case _ => split(2)
    }
    var date: Option[LocalDate] = Option.empty
    val index = split.indexOf("|")
    val ratingThere = index + 1

    if (split.length >= 7 && ratingThere > 0) {
      val rawDate = split(ratingThere) + formatDay(split(ratingThere + 1)) + split(ratingThere + 2)
      try {
        date = Some(formatter.parseLocalDate(rawDate))
      } catch {
        case _: Throwable => throw new RuntimeException("Could not parse: " + line)
      }
    }
    //GET NAME TO ID
    Rating(AlbumId(1), 1, rating, date)
  }

  private def validLine(line: String): Boolean = {
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