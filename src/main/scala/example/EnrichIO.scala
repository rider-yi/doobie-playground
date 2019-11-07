package example

import cats.effect.IO
import com.twitter.util.{Future, Promise}

object EnrichIO {
  implicit class RichIO[A](io: IO[A]) {
    def runToFuture: Future[A] = {
      val promise = new Promise[A]

      io.unsafeRunAsync {
        case Right(a) => promise.setValue(a)
        case Left(e)  => promise.setException(e)
      }

      promise
    }
  }
}
