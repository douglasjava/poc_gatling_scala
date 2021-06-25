package computerdatabase

import io.gatling.core.Predef.{constantUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class MsClientSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8060/api/v1")
    .acceptHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Name")
      .exec(
        http("Request MS CLIENTE")
          .get("/cliente/1")
          .check(status.is(200))
      )

  setUp(scn.inject(
        constantUsersPerSec(100).during(15.minutes)
    ).protocols(httpProtocol)
  )
}
