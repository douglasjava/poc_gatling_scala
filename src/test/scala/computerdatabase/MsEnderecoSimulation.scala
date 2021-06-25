package computerdatabase

import io.gatling.core.Predef.{constantUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class MsEnderecoSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080/api/v1")
    .acceptHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Endereco")
      .exec(
        http("Request MS ENDERECO")
          .get("/cep/32141012")
          .check(status.is(200))
      )

  setUp(scn.inject(
      nothingFor(4 seconds), //pausa de 4 segundos
      atOnceUsers(10), // usuários simultaneos constantes
      rampUsers(10) during(5 seconds), //injetar 10 usuários durante 5 segundos
      constantUsersPerSec(20) during(15 seconds),  //injetar 10 usuários a uma taxa constante por segundo, durante 15 segundos, em intervalos regulares
      constantUsersPerSec(20) during(15 seconds) randomized, // o mesmo de cima só que aleatórios
      rampUsersPerSec(10) to 20 during (10 minutes),//Injeta os usuários da taxa inicial à taxa alvo, definida em usuários por segundo, durante um determinado período. Os usuários serão injetados em intervalos regulares.
      rampUsersPerSec(10) to 20 during (10 minutes) randomized,// o mesmo de cima só que aleatórios
      heavisideUsers(1000) during(20 seconds) //Injeta um determinado número de usuários seguindo uma aproximação suave da função de etapa do heaviside esticada para uma determinada duração.
    ).protocols(httpProtocol)
  )
}
