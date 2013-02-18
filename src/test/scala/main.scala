import com.github.jedesah._
import org.scalatest._
import com.github.nscala_time.time.Imports._

class GenerateTournamentSpec extends FlatSpec with ShouldMatchers {
  
  /** 3 courts, numbered from 1 to 3 */
  val defaultCourts = Map(1 -> PlayArea("Court 1"), 2 -> PlayArea("Court 2"), 3 -> PlayArea("Court 3"))
  /** Courts are open from 9:00 am. to 11:00 pm. */
  val defaultHoursOfOperation = (new LocalTime(9, 0), new LocalTime(23, 0))
  /** Courts are open normally for all 3 days */
  val defaultCourtAvailabilities = Map(1 -> defaultHoursOfOperation, 2 -> defaultHoursOfOperation, 3 -> defaultHoursOfOperation)
  /** All courts are available with the same schedule */
  val defaultPlayAreaAvailabilities = PlayAreaAvailability(defaultCourts.values.toSet, defaultCourtAvailabilities)
  
  /** By default, there must be a minimum of 3 hours between each match */
  val defaultRules = Rules(Duration.standardHours(3))

  "Generate Tournament" should "produce a Tournament with all players" in {
    val participants = Set("Gen", "Bob", "Guillaume Hebert", "Yohann Labonte")
    val tournamentInstance = Tournament.generate(Set(defaultPlayAreaAvailabilities), defaultRules, participants)
    tournamentInstance.draw.contenders should equal (participants)
  }
  
  it should "produce a Tournament with no two matches involving the same participant that have less than the specified minimum amount of time between matches. Of course, this should include any future match a player migth play" in {
    assert(false)
  }
  
  it should "throw an InvalidRequirementsError if a tournament cannot be produced as specified. i.e. if the amount of players is large, availabities are slim and/or the specified minimum amount of time between matches is too large" in {
    assert(false)
  }
  
  it should "produce a tournament with a balanced draw. i.e no participant should be able to play more than one less match than any other participant to win the draw. If the number of participants in the draw is a power of 2, all participants, the draw should make it so all players must win the same amount of matches in order to win the tournament" in {
    assert(false)
  }
  
  it should "produce a tournament that respects the availabilities of the facilities. i.e. no match should be scheduled outside of the specified playing hours of any PlayArea" in {
    assert(false)
  }
  
  it should "produce a tournament that only uses the specified playAreas" in {
    assert(false)
  }
  
  it should "produce a tournament with an underterminated winner unless the specified Set of participants was of size 1" in {
    assert(false)
  }
  
  it should "produce a tournament with a randomized draw" in {
    assert(false)
  }
}

class MatchSpec extends FlatSpec with ShouldMatchers {
  
  "determinedSubMatches" should "return the Set of all subMatches for whom the participants involved are known" in {
    assert(false)
  }
  
  "leafSubMatches" should "return the matches from the fisrt round" in {
    assert(false)
  }
  
  "round(Int)" should "return the round corresponding the specified Int. Rounds are numbered 1 to n where 1 is the first round and n is the round containing this match" in {
    assert(false)
  }
  
  "contenders" should "return the Set of all participants that have not yet lost any mathces" in {
    assert(false)
  }
  
  "update" should "correclty update itself. i.e. the update method shoud return a new Tournament state that correclty reflects the fact that the specified player won" in {
    val matchToUpdate = SimpleMatch(Participant("Paul"), Participant("Andrea"))
    val startMatch = CompositeMatch(matchToUpdate, SimpleMatch(Participant("Jon"), Participant("George")))
    val expectedMatch = CompositeMatch(new SimpleMatch(Participant("Paul"), Participant("Andrea"), Participant("Paul")), SimpleMatch(Participant("Jon"), Participant("George")))
    startMatch.update(Participant("Paul")) should equal (expectedMatch)
  }
  
  it should "throw an IllegalArgumentException if the String is not among the remaining containders" in {
    val match_ = SimpleMatch(Participant("Mary"), Participant("Judy"))
    evaluating { match_.update(Participant("George")) } should produce [IllegalArgumentException]
  }
}
