package computerdatabase

import io.gatling.core.Predef.{constantUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class MsFilmeSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8090/api/v1")
    .acceptHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Filme")
      .exec(
        http("Request MS FILME")
          .get("/filme?ano=&nome=")
          .check(status.is(200))
      )

  setUp(scn.inject(constantUsersPerSec(100).during(2.minutes))).throttle(
    reachRps(100).in(10.seconds),
    holdFor(1.minute),
    jumpToRps(10),
    holdFor(1.minutes)
  ).protocols(httpProtocol)
}
