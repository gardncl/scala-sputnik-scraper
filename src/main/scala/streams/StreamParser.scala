package streams

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import io.Rating
import parsers.{ProcessRating, ProcessSoundOff}


object StreamParser {

  def main(implicit sys: ActorSystem,
           mat: Materializer): Unit = {
    val g = RunnableGraph.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        // Source
        val A: Outlet[String] = builder
          .add(Source.fromIterator(
            () => ProcessSoundOff.apply(1640)))
          .out

        // Flow
        val parseToRating: FlowShape[String, Rating] =
          builder
            .add(ProcessRating.apply)

        // Sink
        val printOut: Inlet[Any] =
          builder
            .add(Sink.foreach(println))
            .in

        A ~> parseToRating ~> printOut

        ClosedShape
    })
    g.run()
  }
}
