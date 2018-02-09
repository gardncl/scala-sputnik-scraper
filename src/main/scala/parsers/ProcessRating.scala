package parsers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.{AlbumId, Rating}
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object ProcessRating {
  private val formatter = DateTimeFormat.forPattern("MMMddyy")

  def apply: Flow[(String, AlbumId), Rating, NotUsed] = {
    Flow[(String, AlbumId)].map(parseLineToRating)
  }

  def parseLineToRating(tuple: (String, AlbumId)): Rating = {
    val line = tuple._1
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
    //GET ID FOR NAME
    Rating(AlbumId(1), 1, rating, date)
  }

  private def formatDay(day: String): String = {
    val dayOfMonth = day.dropRight(2)
    dayOfMonth.length match {
      case 1 => "0" + dayOfMonth
      case _ => dayOfMonth
    }
  }
}
