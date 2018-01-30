import parsers.RatingScraper

object Runner extends App {

  val filtered = RatingScraper.parseWholePage("https://www.sputnikmusic.com/soundoff.php?albumid=159")
  filtered.foreach {
    println(_)
  }

}
