import com.github.jedesah._
import org.scalatest._
import com.github.nscala_time.time.Imports._

trait  defaults {
  /** 3 courts, numbered from 1 to 3 */
  val defaultCourts = Map(1 -> MatchLocation("Court 1"), 2 -> MatchLocation("Court 2"), 3 -> MatchLocation("Court 3"))
  /** Courts are open from 9:00 am. to 11:00 pm. */
  val defaultHoursOfOperation = (new LocalTime(9, 0), new LocalTime(23, 0))
  /** Courts are open normally for all 3 days */
  val defaultCourtAvailabilities = Map(1 -> defaultHoursOfOperation, 2 -> defaultHoursOfOperation, 3 -> defaultHoursOfOperation)
  /** All courts are available with the same schedule */
  val defaultAvailabilities = MatchLocationAvailability(defaultCourts.values.toSet, defaultCourtAvailabilities)

  /** By default, there must be a minimum of 3 hours between each match */
  val defaultRules = Rules(Duration.standardHours(3))
}

class GenerateTournamentFeatureSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen {
  info("It is common in tournaments of competitive nature that there is a miminum time betweeen matches involving the same participant in order for the participant to rest")
  info("Il est commun dans un tournoi de nature competitive d'avoir un minimum de temps entre les parties qui implique un meme joueur afin de permettre a ce dernier de se reposer.")
  feature("Minimum rest time / Temps minimal de repos") {
    scenario("The number of participants, minimum rest time and MatchLocationAvailabilities admit a solution / Le nombre de partipants, la quantite minimal de repos et les disponibilites de terrains admetent une solution") {
      Given("the above scenario / Le scenario mentionne ci-haut")
      When("the tournament is generated / le tournoi est genere")
      Then("the tournament respects the minimum amount of rest of all participants / l tournoi respecte la quantite minimal de repos devant etre accorde a chacun des participants")
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
      Given("A number of participants that is a power of deux / un nombre de participants qui est une puissance de deux")
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
    When("the tournament is generated / le tournoi est genere")
    Then("MatchLocation availabilities are respected / les disponibilites sont respecte")
    pending
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
      val participants = Set("Gen", "Bob", "Guillaume Hebert", "Yohann Labonte")
      val tournamentInstance = Tournament.generate(Set(defaultAvailabilities), defaultRules, participants)
      tournamentInstance.draw.contenders should equal (participants)
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
	val matchToUpdate = SimpleMatch(Participant("Paul"), Participant("Andrea"))
	val startMatch = CompositeMatch(matchToUpdate, SimpleMatch(Participant("Jon"), Participant("George")))
	val expectedMatch = CompositeMatch(new SimpleMatch(Participant("Paul"), Participant("Andrea"), Participant("Paul")), SimpleMatch(Participant("Jon"), Participant("George")))
	startMatch.update(Participant("Paul")) should equal (expectedMatch)
      }
      it("should throw an IllegalArgumentException if the String is not among the remaining containders / devrait lance une exception de type IllegalArgumentException si le participant n'est pas parmis les participants qui reste") {
	val match_ = SimpleMatch(Participant("Mary"), Participant("Judy"))
	evaluating { match_.update(Participant("George")) } should produce [IllegalArgumentException]
      }
    }
  }
}
