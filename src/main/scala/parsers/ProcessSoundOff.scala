package parsers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.AlbumId
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._

object ProcessSoundOff {
  private val browser =
    JsoupBrowser()
  private val soundoffUrl =
    "https://www.sputnikmusic.com/soundoff.php?albumid="

  def apply: Flow[AlbumId, Iterator[(String, AlbumId)], NotUsed] =
    Flow[AlbumId].map(getLines)

  /*
   * Insert the band into the database and then pass all the
   * unprocessed lines with
   */
  def getLines(albumId: AlbumId): Iterator[(String, AlbumId)] = {
    val doc = browser.get(soundoffUrl + albumId.value)
    val lines = doc >> elementList("table")
      .map(_ >> allText("tbody"))
    lines
      .filter(validLine)
      .map(_ -> albumId)
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
