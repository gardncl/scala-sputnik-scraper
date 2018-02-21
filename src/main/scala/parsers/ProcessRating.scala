package parsers

import io.ParseException
import models.{Profile, ProfileId, Rating, SoundOffId}
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

import scala.util.matching.Regex

object ProcessRating {
  private val formatter = DateTimeFormat.forPattern("MMMddyy")

  def parseLineToRatingAndProfile(tuple: (String, SoundOffId)): (Rating, Profile) = {
    val line = tuple._1
    val split: Array[String] = line.split(" +")
    val rating = split(0).toDouble
    val name = getName(line)
    var date: Option[LocalDate] = Option.empty
    val index = split.indexOf("|")
    val ratingThere = index + 1

//    println(line)

    if (split.length >= 7 && ratingThere > 0) {
      val rawDate = split(ratingThere) + formatDay(split(ratingThere + 1)) + split(ratingThere + 2)
      try {
        date = Some(formatter.parseLocalDate(rawDate))
      } catch {
        case _: Throwable => new RuntimeException("Could not parse: " + line)
      }
    }
    //GET ID FOR NAME
    val profileId = ProfileId(-1)
    val ratingObj = Rating(tuple._2, profileId, rating, date)
    (ratingObj, Profile(profileId, name, None))
  }

    //TODO: Change to use single regex.
   private def getName(line: String): String = {
    var nameOption: Option[String] = None
    val regex1 = new Regex("(?<=awful|very poor|poor|average|good|great|excellent|superb|classic).*")
    val regex2 = new Regex("(?<=awful|very poor|poor|average|good|great|excellent|superb|classic).*(?=\\|)")
    val regex3 = new Regex(".*(?=CONTRIBUTOR|STAFF|EMERITUS)")

    if (!line.contains('|') && !(line.contains("CONTRIBUTOR")
      || line.contains("STAFF")
      || line.contains("EMERITUS"))) {
      nameOption = regex1 findFirstIn line
    } else if (line.contains("CONTRIBUTOR")
      || line.contains("STAFF")
      || line.contains("EMERITUS")) {
      val intermediateName = regex1 findFirstIn line
      nameOption = regex3 findFirstIn intermediateName.getOrElse(
        throw ParseException(s"Failed to parse:\n$line")
      )
    } else {
      nameOption = regex2 findFirstIn line
    }

    nameOption match {
      case Some(value) => value.trim
      case None => throw ParseException(s"Failed to parse:\n$line")
    }
  }

  private def formatDay(day: String): String = {
    val dayOfMonth = day.dropRight(2)
    dayOfMonth.length match {
      case 1 => "0" + dayOfMonth
      case _ => dayOfMonth
    }
  }
}
