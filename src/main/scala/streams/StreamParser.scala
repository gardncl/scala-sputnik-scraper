package streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import models.{AlbumId, Rating}
import parsers.{ProcessRating, ProcessSoundOff}

import scala.concurrent.{ExecutionContext, Future}

object StreamParser {

  def main(albumId: AlbumId)(implicit sys: ActorSystem,
                             mat: Materializer): Unit = {
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      // Source(s)
      val createTuples: Outlet[(String, AlbumId)] = builder
        .add(Source.fromIterator(() => ProcessSoundOff.getLines(albumId)))
        .out

      // Flow(s)
      val parseToRating: FlowShape[(String, AlbumId), Rating] =
        builder
          .add(ProcessRating.apply)

      // Sink(s)
      val printOut: Inlet[Any] =
        builder
          .add(Sink.foreach(println))
          .in

      createTuples ~> parseToRating ~> printOut

      ClosedShape
    })
    g.run()
  }

  def apply(albumId: AlbumId)(implicit sys: ActorSystem,
                              mat: Materializer,
                              ec: ExecutionContext): Future[Seq[Rating]] =
    ratingsSource(albumId).runWith(Sink.seq[Rating])

  def ratingsSource(albumId: AlbumId)(
      implicit sys: ActorSystem,
      mat: Materializer,
      ec: ExecutionContext): Source[Rating, NotUsed] =
    createSource(albumId).mapAsync(5) { tuple =>
      Future {
        ProcessRating.parseLineToRating(tuple)
      }
    }

  private def createSource(
      albumId: AlbumId): Source[(String, AlbumId), NotUsed] =
    Source.fromIterator(() => ProcessSoundOff.getLines(albumId))

}
