package parsers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.{Profile, ProfileId, Rating, SoundOffId}
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object ProcessRating {
  private val formatter = DateTimeFormat.forPattern("MMMddyy")

  def parseLineToRatingAndProfile(tuple: (String, SoundOffId)): (Rating, Profile) = {
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
    val profileId = ProfileId(-1)
    val ratingObj = Rating(tuple._2, profileId, rating, date)
    println(ratingObj)
    (ratingObj, Profile(profileId, name, None))
  }

  private def formatDay(day: String): String = {
    val dayOfMonth = day.dropRight(2)
    dayOfMonth.length match {
      case 1 => "0" + dayOfMonth
      case _ => dayOfMonth
    }
  }
}
