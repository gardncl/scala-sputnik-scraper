package parsers

import akka.stream.scaladsl.Flow
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{allText, elementList}
import net.ruippeixotog.scalascraper.dsl.DSL._

object ProcessSoundOff {
  private val browser =
    JsoupBrowser()
  private val soundoffUrl =
    "https://www.sputnikmusic.com/soundoff.php?albumid="

  def apply(albumId: Int): Iterator[String] = {
    val doc = browser.get(soundoffUrl + albumId)
    val lines = doc >> elementList("table")
      .map(_ >> allText("tbody"))
    lines
      .filter(validLine)
      .toIterator
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
