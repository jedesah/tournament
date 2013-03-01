package com.github.jedesah

import com.github.nscala_time.time.Imports._

object Tournament {
  


  /** Represents the location of a Match, for a squash tournament, this would be a court.
      For a soccer tournament this would be the playing field. */
  type MatchLocation = String
  type TimeSlot = (LocalTime, LocalTime)
  type Day = Int
  type Availability = Map[Day, TimeSlot]
  type Availabilities = Map[MatchLocation, Availability]
  type Participant = String

  case class Rules(minTimeBeetweenMatch: Duration, expectedMatch:Duration)

  case class Constraints(rules: Rules, availabilities: Availabilities) {
    def isMatchStartTimeValid(startTime: LocalTime, location: MatchLocation, day: Day):Boolean = ???
  }

  case class Tournament(draw: Match, schedule: Map[Match, (MatchLocation, Day, LocalTime)])

  abstract class Match {
    val winner: Option[Participant] = None
    def determinedSubMatches: Set[SimpleMatch]
    def leafSubMatches: Set[SimpleMatch]
    def round(nb: Int): Option[Set[Match]]
    def contenders: Set[Participant]
    def update(winner: Participant): Match
    def findMatchWithParticipant(participant: Participant): Match
    def allMatches: Set[Match]
  }
  case class SimpleMatch(first: Participant, second: Participant) extends Match {
    def this(first: Participant, second: Participant, winner_ : Participant) = {
      this(first, second)
      val winner = Some(winner_)
    }
    def determinedSubMatches: Set[SimpleMatch] = ???
    def leafSubMatches: Set[SimpleMatch] = Set()
    def round(nb: Int): Option[Set[Match]] = ???
    def contenders: Set[Participant] = ???
    def update(winner: Participant): Match = ???
    def findMatchWithParticipant(participant: Participant) = ???
    def allMatches = Set(this)
  }
  /** A CompositeMatch is a Match that opposes the winner of the first match against the winner of the second. */
  case class CompositeMatch(first: Match, second: Match) extends Match {
    def this(first: Match, second: Match, winner_ : Participant) = {
      this(first, second)
      val winner = Some(winner_)
    }
    def determinedSubMatches: Set[SimpleMatch] = ???
    def leafSubMatches: Set[SimpleMatch] = ???
    def round(nb: Int): Option[Set[Match]] = ???
    def contenders: Set[Participant] = ???
    def update(match_ : Match, winner: Participant): Match = ???
    def update(winner: Participant): Match = ???
    def findMatchWithParticipant(participant: Participant) = ???
    def allMatches = ???
  }
  
  /** Generate a tournament that satisfies the specified constraints */
  def generate(constraints: Constraints, participants: Set[Participant]): Tournament = ???
}