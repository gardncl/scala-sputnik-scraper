package parsers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.SoundOffId
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._

object ProcessSoundOff {
  private val browser =
    JsoupBrowser()
  private val soundoffUrl =
    "https://www.sputnikmusic.com/soundoff.php?albumid="

  def apply: Flow[SoundOffId, Vector[(String, SoundOffId)], NotUsed] =
    Flow[SoundOffId].map(getLines)

  /*
   * Insert the band into the database and then pass all the
   * unprocessed lines with
   */
  def getLines(soundOffId: SoundOffId): Vector[(String, SoundOffId)] = {
    val doc = browser.get(soundoffUrl + soundOffId.value)
    val lines = doc >> elementList("table")
      .map(_ >> allText("tbody"))
    val tuples = lines
      .filter(validLine)
      .map(_ -> soundOffId)
    lines.length match {
      case 0 => tuples.toVector
      case _ => tuples.drop(2).toVector
    }
  }

  private def validLine(line: String): Boolean = {
    getRating(line) match {
      case Some(_) => true
      case None => false
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
