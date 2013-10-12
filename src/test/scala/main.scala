import com.github.jedesah.Tournament._
import org.scalatest._
import com.github.nscala_time.time.Imports._

trait defaults {
  /** Courts are open from 9:00 am. to 10:30 pm. */
  val defaultHoursOfOperation = (new LocalTime(9, 0), new LocalTime(22, 30))
  /** Courts are open normally for all 2 days */
  val defaultAvailability = Map(
    1 -> defaultHoursOfOperation,
    2 -> defaultHoursOfOperation
  )
  /** All courts have the same Availability */
  val defaultAvailabilities = Map(
    "Court1" -> defaultAvailability,
    "Court2" -> defaultAvailability
  )
  
  val defaultParticipants = Set("Gen", "Bob", "Guillaume Hebert", "Yohann Labonte")

  /** By default,
    there must be a minimum of 3 hours between each match
    each match is expected to last 1 hour
  */
  val defaultRules = Rules(Duration.standardHours(3), Duration.standardHours(1))
}

class GenerateTournamentFeatureSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen with defaults {
  info("It is common in tournaments of competitive nature that there is a miminum time betweeen matches involving the same participant in order for the participant to rest")
  info("Il est commun dans un tournoi de nature competitive d'avoir un minimum de temps entre les parties qui implique un meme joueur afin de permettre a ce dernier de se reposer.")
  feature("Minimum rest time / Temps minimal de repos") {
    scenario("The number of participants, minimum rest time and MatchLocationAvailabilities admit a solution / Le nombre de partipants, la quantite minimal de repos et les disponibilites de terrains admetent une solution") {
      Given("the above scenario / Le scenario mentionne ci-haut")
      When("the tournament is generated / le tournoi est genere")
      Then("the tournament respects the minimum amount of rest of all participants / le tournoi respecte la quantite minimal de repos devant etre accorde a chacun des participants")
      pending
    }
    scenario("The Number of participants, minimum rest time and MatchLocationAvailability do not admit a solution / Le Nombre de participatns, temps de repos minimum et disponibilites de terrains n'admetent pas une solution") {
      Given("the above scenario / le scenario mentionne ci-haut")
      When("the tournament is generated / le tournoi est genere")
      Then("an IllegalArgumentException is thrown / une IllegalArgumentException est lance")
      pending
    }
  }
  
  feature("Balanced draw / Tirage balance") {
    scenario("Number of participants is a power of two / Le nombre de participants est une puissance de deux") {
      Given("A number of participants that is a power of two / un nombre de participants qui est une puissance de deux")
      When("the tournament is generated / le tournoi est genere")
      Then("each participant should be required to win the same amount of matches in order to win the tournament / chaque participant devrait avoir a gagner le meme nombre de match afin de gagner le tournoi")
      pending
    }
    scenario("number of participants is not a power of two / nombre de participants n'est pas une puissance de deux") {
      Given("a number of participants that is not a power of two / un nombre de participants qui n'est pas une puissance de deux")
      When("the tournament is generated / le tournoi est genere")
      Then("each participant should be required to win no more than one less match than any other participant in order to win the tournament / chaque participant devrait avoir a gagner pas plus d'une partie de moins que n'importe quel autre participant afin de remporter le tournoi")
      pending
    }
    scenario("one participant / un seul participant") {
      Given("one participant / un seul participant")
      When("the tournament is generated / le tournoi est genere")
      Then("an IllegalArgumentException is thrown / un IllegalArgumentException est lance")
      pending
    }
  }
  
  feature("respect availabilities / respect des disponibilites") {
    Given("a set of availabilities / une collection de disponibilites")
    val availabilities = Map(
      "Court1" -> Map(
	1 -> ((new LocalTime(9,0), new LocalTime(23,0))),
	2 -> ((new LocalTime(12,0), new LocalTime(17,0)))
      ),
      "Court2" -> Map(
	1 -> (new LocalTime(9,0), new LocalTime(23,0)),
	2 -> (new LocalTime(13,0), new LocalTime(18,0))
      ),
      "Court3" -> Map(
	1 -> (new LocalTime(12,0), new LocalTime(17,0)),
	2 -> (new LocalTime(10,30), new LocalTime(16,45))
      )
    )
    When("the tournament is generated / le tournoi est genere")
    val constraints = Constraints(defaultRules, availabilities)
    val tournament = generate(constraints, defaultParticipants)
    Then("MatchLocation availabilities are respected / les disponibilites sont respecte")
    val doRespectAvailabilities = tournament.draw.allMatches.forall { match_ =>
      val (matchLocation, day, time) = tournament.schedule(match_)
      constraints.isMatchStartTimeValid(time, matchLocation, day)
    }
    assert(doRespectAvailabilities)
  }
  
  feature("Randomized draw / Tirage aleatoire") {
    // generez 10 tournois avec beaucoup de joueurs et vous assurer qu'ils ne sont pas tous pareil devrait suffir pour valider ce test.
    // Le test va echouer de temps en temps.
    (pending)
  }
}

class GenerateTournamentSpec extends FunSpec with ShouldMatchers with defaults {
  describe("Generate a tournament / Generer un tournoi") {
    describe("only uses specified MatchLocations / utilise seulement les terrains specifies") {
      (pending)
    }
    describe("involves all specified participants / inclut tous les participants specifies") {
      val tournamentInstance = generate(Constraints(defaultRules, defaultAvailabilities), defaultParticipants)
      tournamentInstance.draw.contenders should equal (defaultParticipants)
    }
  }
}

class MatchSpec extends FunSpec with ShouldMatchers {
  describe("Match") {
    describe("determinedSubMatches") {
      it("should return the Set of all subMatches for whom the participants involved are known / devrait retourner la collection de tous les sous-matchs pour lesquels les participants sont connus") (pending)
    }
    describe("leafSubMatches") {
      it("should return the matches from the first round / retourner les matchs de la premiere ronde") (pending)
    }
    describe("round(Int)") {
      it("return the round corresponding to the specified Int. Rounds are numbered 1 to n where 1 is the first round and n is the round containing this match / retourner la ronde qui correspond au Int recu en parametre. Les rondes sont numerotes de 1 a n ronde, ou 1 est la premiere ronde et n et le match courant") (pending)
    }

    describe("contenders") {
      it("should return the Set of all participants that have not yet lost any mathces / devrait retourner la collection de tous les participants pour lesquels il est encore possible de gagner") (pending)
    }

    describe("update") {
      it("should correclty update itself. i.e. the update method shoud return a new Tournament state that correclty reflects the fact that the specified player won / devrait mettre a jour le tournoi afin de refleter la victoire du joueur specifie") {
	val matchToUpdate = SimpleMatch("Paul", "Andrea")
	val startMatch = CompositeMatch(matchToUpdate, SimpleMatch("Jon", "George"))
	val expectedMatch = CompositeMatch(new SimpleMatch("Paul", "Andrea", "Paul"), SimpleMatch("Jon", "George"))
	startMatch.update("Paul") should equal (expectedMatch)
      }
      it("should throw an IllegalArgumentException if the String is not among the remaining containders / devrait lance une exception de type IllegalArgumentException si le participant n'est pas parmis les participants qui reste") {
	val match_ = SimpleMatch("Mary", "Judy")
	evaluating { match_.update("George") } should produce [IllegalArgumentException]
      }
    }
  }
}

class ConstraintsSpec extends FunSpec with ShouldMatchers {
  describe("Constraints") {
    describe("isMatchStartTimeValid") {
      // TODO: Ecrivez les tests qui permettront de valider si le temps de commencement est valide ou non
      // Indice: Il y a au moins 3 cas a traite
    }
  }
}
